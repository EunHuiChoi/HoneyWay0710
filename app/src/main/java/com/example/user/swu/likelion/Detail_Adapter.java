package com.example.user.swu.likelion;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Detail_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Detail_Info> passing_station;

    public Detail_Adapter(ArrayList<Detail_Info> passing_station){
        this.passing_station = passing_station;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public ImageView viewImage;
        public TextView viewText;

        View view;//클릭때문에 추가

        public MyViewHolder(View view){
            super(view);
            this.view = view; // 추가
            viewImage = (ImageView) view.findViewById(R.id.view_image);
            viewText = (TextView) view.findViewById(R.id.view_text);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_item, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.viewText.setText(passing_station.get(position).station_name);

        if (passing_station.get(position).drawable_Id.equals("매우 혼잡")) {
            myViewHolder.viewImage.setImageResource(R.drawable.red);
        }else if (passing_station.get(position).drawable_Id.equals("혼잡")) {
            myViewHolder.viewImage.setImageResource(R.drawable.lightred);
        }else if (passing_station.get(position).drawable_Id.equals("보통")){
            myViewHolder.viewImage.setImageResource(R.drawable.orange);
        }else if (passing_station.get(position).drawable_Id.equals("여유")){
            myViewHolder.viewImage.setImageResource(R.drawable.yellow);
        }else{
            myViewHolder.viewImage.setImageResource(R.drawable.bage);
        }

        final int Position = position;
    }

    @Override
    public int getItemCount() {
        return passing_station.size();
    }
}