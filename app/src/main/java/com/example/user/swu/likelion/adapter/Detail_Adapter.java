package com.example.user.swu.likelion.adapter;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.swu.likelion.Detail_Info;
import com.example.user.swu.likelion.R;

import java.util.ArrayList;

public class Detail_Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Detail_Info> passing_station;

    public Detail_Adapter(ArrayList<Detail_Info> passing_station){
        this.passing_station = passing_station;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //public ImageView viewImage;
        public View viewImage;
        public View viewImage2;
        public TextView viewText;

        View view;//클릭때문에 추가

        public MyViewHolder(View view){
            super(view);
            this.view = view; // 추가
            //viewImage = (ImageView) view.findViewById(R.id.view_image);
            viewImage = (View) view.findViewById(R.id.view_image);
            viewImage2 = (View) view.findViewById(R.id.view_image2);
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


        //myViewHolder.viewImage.setBackgroundColor(Color.parseColor("#00aaff"));
        if (passing_station.get(position).hoseons.equals("1")) {
            myViewHolder.viewImage.setBackgroundResource(R.color.line1);
        }else if (passing_station.get(position).hoseons.equals("2")) {
            myViewHolder.viewImage.setBackgroundResource(R.color.line2);
        }else if (passing_station.get(position).hoseons.equals("3")) {
            myViewHolder.viewImage.setBackgroundResource(R.color.line3);
        }else if (passing_station.get(position).hoseons.equals("4")) {
            myViewHolder.viewImage.setBackgroundResource(R.color.line4);
        }else if (passing_station.get(position).hoseons.equals("5")) {
            myViewHolder.viewImage.setBackgroundResource(R.color.line5);
        }else if (passing_station.get(position).hoseons.equals("6")) {
            myViewHolder.viewImage.setBackgroundResource(R.color.line6);
        }else if (passing_station.get(position).hoseons.equals("7")) {
            myViewHolder.viewImage.setBackgroundResource(R.color.line7);
        }else if (passing_station.get(position).hoseons.equals("8")) {
            myViewHolder.viewImage.setBackgroundResource(R.color.line8);
        }else if (passing_station.get(position).hoseons.equals("9")) {
            myViewHolder.viewImage.setBackgroundResource(R.color.line9);
        }






        myViewHolder.viewImage2.setBackgroundColor(Color.parseColor("#00aaff"));

        if (passing_station.get(position).drawable_Id.equals("매우 혼잡")) {
            myViewHolder.viewImage2.setBackgroundResource(R.color.color_bbackbback);
        }else if (passing_station.get(position).drawable_Id.equals("혼잡")) {
            myViewHolder.viewImage2.setBackgroundResource(R.color.color_honjob);
        }else if (passing_station.get(position).drawable_Id.equals("보통")){
            myViewHolder.viewImage2.setBackgroundResource(R.color.color_botong);
        }else if (passing_station.get(position).drawable_Id.equals("여유")){
            myViewHolder.viewImage2.setBackgroundResource(R.color.color_yuyou);
        }else{
            myViewHolder.viewImage2.setBackgroundResource(R.color.color_nulnul);
        }
        /*
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
*/
        final int Position = position;
    }

    @Override
    public int getItemCount() {
        return passing_station.size();
    }
}