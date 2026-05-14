package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.google.mlkit.vision.text.korean.KoreanTextRecognizerOptions;

import java.io.IOException;

public class CreateQuizActivity extends AppCompatActivity {

    private ActivityResultLauncher<Intent> galleryLauncher;
    private TextRecognizer recognizer;

    private EditText etSubject;
    private EditText etContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz);

        etSubject = findViewById(R.id.et_subject);
        etContent = findViewById(R.id.et_content);

        // 한국어 인식기 초기화
        recognizer = TextRecognition.getClient(new KoreanTextRecognizerOptions.Builder().build());

        // 갤러리 결과 처리
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        processImage(imageUri); // 이미지 분석 시작
                    }
                }
        );

        findViewById(R.id.btn_pick_image).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            galleryLauncher.launch(intent);
        });
    }

    private void processImage(Uri uri) {

        // 1. ImageView를 찾아오기 (전역변수로 선언했다면 findViewById 생략 가능)
        ImageView ivPreview = findViewById(R.id.iv_selected_preview);

        // 2. 선택한 사진을 이미지뷰에 세팅
        ivPreview.setImageURI(uri);

        // 3. 혹시 XML에서 visibility="gone"으로 설정했다면 다시 보이게 하기
        ivPreview.setVisibility(View.VISIBLE);

        try {
            InputImage image = InputImage.fromFilePath(this, uri);
            recognizer.process(image)
                    .addOnSuccessListener(visionText -> {
                        // 추출된 텍스트를 '상세 내용' 입력창에 자동으로 채워줌!
                        String resultText = visionText.getText();
                        etContent.append("\n[추출된 내용]\n" + resultText);
                        Toast.makeText(this, "텍스트 추출 완료!", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "인식 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 추출된 텍스트를 보여주고 선택하게 하는 팝업
    private void showOcrResultDialog(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("추출된 텍스트 확인");
        builder.setMessage(text); // 읽어온 글자를 여기서 바로 확인!

        builder.setPositiveButton("내용에 추가", (dialog, which) -> {
            etContent.append("\n" + text); // 확인을 누르면 상세 내용 칸에 추가
            Toast.makeText(this, "내용이 추가되었습니다.", Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton("취소", null);
        builder.show();
    }
}