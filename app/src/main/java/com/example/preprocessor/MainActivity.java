package com.example.preprocessor;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText subjectEditText, inputEditText;
    private Button sendButton;
    private TextView resultTextView;
    private GenerativeModelFutures model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        subjectEditText = findViewById(R.id.subjectEditText);
        inputEditText = findViewById(R.id.inputEditText);
        sendButton = findViewById(R.id.sendButton);
        resultTextView = findViewById(R.id.resultTextView);

        // Gemini 설정
        GenerativeModel gm = new GenerativeModel(
                "gemini-3-flash-preview", // 사용 중인 모델명 확인
                "AIzaSyDvl63Z6F4LXrsUyWKcjheqF1DlmFWG8A0"
        );
        model = GenerativeModelFutures.from(gm);

        sendButton.setOnClickListener(v -> {
            String subject = subjectEditText.getText().toString().trim();
            String contentInput = inputEditText.getText().toString().trim();

            if (subject.isEmpty() || contentInput.isEmpty()) {
                resultTextView.setText("주제와 내용을 모두 입력해주세요.");
                return;
            }

            requestValidation(subject, contentInput);
        });
    }

    private void requestValidation(String subject, String userText) {
        resultTextView.setText("AI가 주제 '" + subject + "'와의 연관성을 분석 중...");

        String prompt = "당신은 교육 데이터 검역 전문가입니다. 아래 데이터를 분석하세요.\n\n" +
                "1. 목표 주제: " + subject + "\n" +
                "2. 사용자 입력: " + userText + "\n\n" +
                "위 데이터가 '" + subject + "'에 대해 사실인지, 주제와 관련이 있는지 판단하여 다음 양식으로 응답하세요.\n\n" +
                "[검증 리포트]\n" +
                "- 진실성 점수(0~100): \n" +
                "- 주제 적합성(적합/부적합): \n" +
                "- 학습 수준(초/중/고급): \n" +
                "- 정제된 최종 문장: \n" +
                "- 피드백: \n" +
                "- 데이터 승인 여부(YES/NO): ";

        Content content = new Content.Builder().addText(prompt).build();
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();
                runOnUiThread(() -> {
                    resultTextView.setText(resultText);

                    // 데이터 승인(YES)인 경우에만 서버에 저장
                    if (resultText.toUpperCase().contains("YES")) {
                        // 결과 텍스트에서 데이터 추출 및 서버 전송
                        parseAndSendData(subject, userText, resultText);
                    }
                });
            }

            @Override
            public void onFailure(Throwable t) {
                runOnUiThread(() -> resultTextView.setText("통신 오류: " + t.getMessage()));
            }
        }, ContextCompat.getMainExecutor(this));
    }

    // AI 결과에서 필요한 정보를 뽑아 서버로 전송하는 메서드
    private void parseAndSendData(String subject, String original, String aiResponse) {
        // 간단한 파싱 예시 (실제 서비스에서는 정규표현식이 더 정확합니다)
        int score = 0;
        String cleaned = original; // 기본값

        try {
            // "진실성 점수(0~100): 85" 형태에서 숫자 추출
            if (aiResponse.contains("진실성 점수")) {
                String scoreStr = aiResponse.split("진실성 점수")[1].replaceAll("[^0-9]", "");
                if (!scoreStr.isEmpty()) score = Integer.parseInt(scoreStr);
            }

            // "정제된 최종 문장: " 이후의 텍스트 추출
            if (aiResponse.contains("정제된 최종 문장:")) {
                cleaned = aiResponse.split("정제된 최종 문장:")[1].split("\n")[0].trim();
            }
        } catch (Exception e) {
            Log.e("PARSING_ERROR", "데이터 추출 중 오류 발생");
        }

        // 서버 전송 로직 호출
        sendToServer(subject, original, cleaned, score);
    }

    private void sendToServer(String sub, String ori, String clean, int score) {
        LearningRequest request = new LearningRequest(sub, ori, clean, score);
        ApiService apiService = RetrofitClient.getApiService();

        apiService.saveData(request).enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("SERVER_LOG", "MySQL 저장 성공!");
                } else {
                    Log.e("SERVER_LOG", "서버 응답 오류: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("SERVER_LOG", "네트워크 연결 실패: " + t.getMessage());
            }
        });
    }
}