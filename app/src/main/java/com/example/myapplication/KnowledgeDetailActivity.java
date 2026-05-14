package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class KnowledgeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 이전에 만든 activity_knowledge_detail.xml 레이아웃을 연결합니다.
        setContentView(R.layout.activity_knowledge_detail);

        // XML 위젯 연결
        TextView tvSubject = findViewById(R.id.tv_detail_subject);
        TextView tvContent = findViewById(R.id.tv_detail_content);
        ImageView ivImage = findViewById(R.id.iv_detail_image);
        MaterialButton btnEdit = findViewById(R.id.btn_edit_knowledge);

        // HistoryAdapter에서 보낸 데이터 받기
        String subject = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        int imageResId = getIntent().getIntExtra("image", 0);

        // 데이터 화면에 표시
        if (subject != null) tvSubject.setText(subject);
        if (content != null) tvContent.setText(content);
        if (imageResId != 0) ivImage.setImageResource(imageResId);

        // 지식 수정 버튼 클릭 시 (필요에 따라 구현)
        // KnowledgeDetailActivity.java 내부
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(KnowledgeDetailActivity.this, CreateQuizActivity.class);

            // 현재 보고 있는 데이터를 수정 페이지로 전달
            intent.putExtra("mode", "edit"); // 수정 모드임을 알림
            intent.putExtra("title", tvSubject.getText().toString());
            intent.putExtra("content", tvContent.getText().toString());

            startActivity(intent);
        });
    }
}