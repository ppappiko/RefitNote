package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        GridView calendarGridView = view.findViewById(R.id.calendarGridView);

        // 테스트용 임시 데이터 (나중에 DB에서 불러와야 함)
        List<CalendarDay> dayList = new ArrayList<>();
        // 예시: 1일부터 30일까지 생성
        for (int i = 1; i <= 30; i++) {
            // 15일에는 지식 2개 등록, 1번 학습했다고 가정
            if (i == 15) dayList.add(new CalendarDay(String.valueOf(i), 2, 1));
            else dayList.add(new CalendarDay(String.valueOf(i), 0, 0));
        }

        CalendarAdapter adapter = new CalendarAdapter(getContext(), dayList);
        calendarGridView.setAdapter(adapter);

        return view;
    }
}