package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<HistoryItem> historyDataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 임시 데이터 생성 (실제로는 DB에서 가져옴)
        historyDataList = new ArrayList<>();
        historyDataList.add(new HistoryItem("정보처리기사", "2025.07.10 ~ 2026.03.14",
                "애자일 모형 - 민첩하게 고객의 요구사항 변화에 대응한다...", android.R.drawable.ic_menu_gallery));
        historyDataList.add(new HistoryItem("주식", "2025.07.10 ~ 2026.02.13",
                "매수/매도 타이밍 잡기 기초...", android.R.drawable.ic_menu_gallery));

        // 어댑터 설정
        adapter = new HistoryAdapter(historyDataList);
        recyclerView.setAdapter(adapter);

        return view;
    }
}