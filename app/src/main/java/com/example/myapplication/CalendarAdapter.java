package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CalendarAdapter extends BaseAdapter {
    private Context context;
    private List<CalendarDay> days;

    public CalendarAdapter(Context context, List<CalendarDay> days) {
        this.context = context;
        this.days = days;
    }

    @Override
    public int getCount() { return days.size(); }
    @Override
    public Object getItem(int i) { return days.get(i); }
    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.calendar_item, parent, false);
        }

        TextView tvDate = convertView.findViewById(R.id.tv_date);
        TextView tvInfo = convertView.findViewById(R.id.tv_count_info);
        CalendarDay day = days.get(position);

        tvDate.setText(day.date);

        if (day.date.isEmpty()) {
            tvInfo.setVisibility(View.GONE);
        } else {
            // 등록 수/학습 수 표시 (예: 2/1)
            tvInfo.setText(day.addCount + "/" + day.learnCount);
            tvInfo.setVisibility((day.addCount > 0 || day.learnCount > 0) ? View.VISIBLE : View.INVISIBLE);
        }

        return convertView;
    }
}
