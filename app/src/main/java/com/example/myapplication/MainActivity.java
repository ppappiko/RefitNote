package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabAdd = findViewById(R.id.fab_add_global);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // 지식 추가 버튼 클릭 리스너
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateQuizActivity.class);
            startActivity(intent);
        });

        // 앱 실행 시 첫 화면(Home) 설정
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selected = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                selected = new HomeFragment();
                fabAdd.show(); // 홈에서는 버튼 보이기
            } else if (id == R.id.nav_history) {
                selected = new HistoryFragment();
                fabAdd.show(); // 기록에서는 버튼 보이기
            } else if (id == R.id.nav_solve) {
                selected = new QuizFragment();
                fabAdd.show(); // 문제 풀기에서는 버튼 보이기
            } else if (id == R.id.nav_profile) {
                selected = new ProfileFragment();
                fabAdd.hide(); // 내 정보 페이지에서는 버튼 숨기기!
            }

            if (selected != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selected)
                        .commit();
            }
            return true;
        });

        ExtendedFloatingActionButton fabAdd = findViewById(R.id.fab_add_global);

        fabAdd.setOnClickListener(v -> {
            // 지식 추가 페이지(Activity)로 이동
            Intent intent = new Intent(MainActivity.this, CreateQuizActivity.class);
            startActivity(intent);

            // 페이지가 올라오는 부드러운 애니메이션 (선택)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
    }
}