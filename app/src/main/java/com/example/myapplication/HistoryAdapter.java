package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private List<HistoryItem> historyList;
    private Context context;

    public HistoryAdapter(List<HistoryItem> historyList) {
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        // 앞서 만든 item_history.xml을 연결합니다.
        View view = LayoutInflater.from(context).inflate(R.layout.item_history, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryItem item = historyList.get(position);

        // 데이터를 레이아웃에 세팅
        holder.tvTitle.setText(item.getTitle());
        holder.tvDate.setText(item.getDateRange());

        // 카드 클릭 시 상세 페이지(KnowledgeDetailActivity)로 이동
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, KnowledgeDetailActivity.class);

            // 상세 페이지에서 보여줄 데이터를 인텐트로 넘깁니다.
            intent.putExtra("title", item.getTitle());
            intent.putExtra("content", item.getContent());
            intent.putExtra("image", item.getImageResId());

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    // 뷰홀더 클래스: 아이템 안의 위젯들을 붙잡아두는 역할
    public static class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDate;

        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_history_title);
            tvDate = itemView.findViewById(R.id.tv_history_date);
        }
    }
}