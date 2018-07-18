package com.techieaid.howler;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
 ** Created by Gautam Krishnan {@link https://github.com/GautiKrish}
 */public class AlarmRecyclerViewAdapter extends RecyclerView.Adapter<AlarmRecyclerViewAdapter.ViewHolder> {

     private Context mContext;
     private ArrayList<String> mAlarmsAdapter;

    AlarmRecyclerViewAdapter(Context context, ArrayList<String> alarmsAdapter) {
        this.mContext = context;
        mAlarmsAdapter=alarmsAdapter;
    }
    @NonNull
    @Override
    public AlarmRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.activity_alarm_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(mAlarmsAdapter.get(position));

    }

    @Override
    public int getItemCount() {
        return mAlarmsAdapter.size();
    }

    public void setAlarmTime(String alarmTime) {
        mAlarmsAdapter.add(alarmTime);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTextView;
        private ImageView mImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.alarm_time);
            mImageView = itemView.findViewById(R.id.trash_icon);
        }
    }
}
