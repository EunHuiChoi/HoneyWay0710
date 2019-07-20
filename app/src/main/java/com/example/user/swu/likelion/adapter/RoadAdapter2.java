package com.example.user.swu.likelion.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.swu.likelion.R;
import com.example.user.swu.likelion.RoadBean;
import com.example.user.swu.likelion.RoadBean2;

import java.util.List;

public class RoadAdapter2 extends BaseAdapter {
    private Context mContext;
    private List<RoadBean2> mList;


    public RoadAdapter2(Context context, List<RoadBean2> list) {
        mContext = context;
        mList = list;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView != null)
            return convertView;


        //인플레이팅 하는 작업
        // mDepartArrive.add("동대문역사문화공원");
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.view_road2, null);

        //해당 ROW의 데이터를 찾는 작업
        final RoadBean2 roadBean2 = mList.get(position);

        //인플레이팅 된 뷰에서 ID 찾는 작업
        TextView txtStation1 = convertView.findViewById(R.id.txtStation1);
        TextView txtStation2 = convertView.findViewById(R.id.txtStation2);
        TextView txtStation3 = convertView.findViewById(R.id.txtStation3);
        ImageView bar01 = convertView.findViewById(R.id.bar01);
        ImageView bar02 = convertView.findViewById(R.id.bar02);
        TextView txtTime = convertView.findViewById(R.id.txtTime);

        //데이터 셋팅
        txtStation1.setText( roadBean2.getStation1() );
        txtStation2.setText( roadBean2.getStation2() );
        txtStation3.setText( roadBean2.getStation3() );

        if(roadBean2.getTransfer1() == 1){
            bar01.setBackgroundDrawable(new ColorDrawable(0x263c94));
        } else if(roadBean2.getTransfer1() == 2){
            bar01.setBackgroundDrawable(new ColorDrawable(0x45b550));
        } else if(roadBean2.getTransfer1() == 3){
            bar01.setBackgroundDrawable(new ColorDrawable(0xff780a));
        } else if(roadBean2.getTransfer1() == 4){
            bar01.setBackgroundDrawable(new ColorDrawable(0x2ca0de));
        } else if(roadBean2.getTransfer1() == 5){
            bar01.setBackgroundDrawable(new ColorDrawable(0x8735de));
        }else if(roadBean2.getTransfer1() == 6){
            bar01.setBackgroundDrawable(new ColorDrawable(0xb85614));
        }else if(roadBean2.getTransfer1() == 7){
            bar01.setBackgroundDrawable(new ColorDrawable(0x79871e));
        }else if(roadBean2.getTransfer1() == 8){
            bar01.setBackgroundDrawable(new ColorDrawable(0xd60471));
        } else{
            bar01.setBackgroundDrawable(new ColorDrawable(0x474747));
        }

        if(roadBean2.getTransfer1() == 1){
            bar02.setBackgroundDrawable(new ColorDrawable(0x263c94));
        } else if(roadBean2.getTransfer1() == 2){
            bar02.setBackgroundDrawable(new ColorDrawable(0x45b550));
        } else if(roadBean2.getTransfer1() == 3){
            bar02.setBackgroundDrawable(new ColorDrawable(0xff780a));
        } else if(roadBean2.getTransfer1() == 4){
            bar02.setBackgroundDrawable(new ColorDrawable(0x2ca0de));
        } else if(roadBean2.getTransfer1() == 5){
            bar02.setBackgroundDrawable(new ColorDrawable(0x8735de));
        }else if(roadBean2.getTransfer1() == 6){
            bar02.setBackgroundDrawable(new ColorDrawable(0xb85614));
        }else if(roadBean2.getTransfer1() == 7){
            bar02.setBackgroundDrawable(new ColorDrawable(0x79871e));
        }else if(roadBean2.getTransfer1() == 8){
            bar02.setBackgroundDrawable(new ColorDrawable(0xd60471));
        } else{
            bar02.setBackgroundDrawable(new ColorDrawable(0x474747));
        }

        String tempTime = Integer.toString(roadBean2.getTime());
        txtTime.setText(tempTime);


        return convertView;
    }
}
