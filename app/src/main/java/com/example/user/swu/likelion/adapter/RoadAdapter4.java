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
import com.example.user.swu.likelion.RoadBean4;

import java.util.List;

public class RoadAdapter4 extends BaseAdapter {
    private Context mContext;
    private List<RoadBean4> mList;


    public RoadAdapter4(Context context, List<RoadBean4> list) {
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
        convertView = inflater.inflate(R.layout.view_road4, null);

        //해당 ROW의 데이터를 찾는 작업
        final RoadBean4 roadBean4 = mList.get(position);

        //인플레이팅 된 뷰에서 ID 찾는 작업
        TextView txtStation1 = convertView.findViewById(R.id.txtStation1);
        TextView txtStation2 = convertView.findViewById(R.id.txtStation2);
        TextView txtStation3 = convertView.findViewById(R.id.txtStation3);
        TextView txtStation4 = convertView.findViewById(R.id.txtStation4);
        TextView txtStation5 = convertView.findViewById(R.id.txtStation5);
        ImageView bar01 = convertView.findViewById(R.id.bar01);
        ImageView bar02 = convertView.findViewById(R.id.bar02);
        ImageView bar3 = convertView.findViewById(R.id.bar3);
        ImageView bar4 = convertView.findViewById(R.id.bar4);
        TextView txtTime = convertView.findViewById(R.id.txtTime);

        //데이터 셋팅
        txtStation1.setText( roadBean4.getStation1() );
        txtStation2.setText( roadBean4.getStation2() );
        txtStation3.setText( roadBean4.getStation3() );
        txtStation4.setText( roadBean4.getStation4() );

        if(roadBean4.getTransfer1() == 1){
            bar01.setBackgroundDrawable(new ColorDrawable(0x263c94));
        } else if(roadBean4.getTransfer1() == 2){
            bar01.setBackgroundDrawable(new ColorDrawable(0x45b550));
        } else if(roadBean4.getTransfer1() == 3){
            bar01.setBackgroundDrawable(new ColorDrawable(0xff780a));
        } else if(roadBean4.getTransfer1() == 4){
            bar01.setBackgroundDrawable(new ColorDrawable(0x2ca0de));
        } else if(roadBean4.getTransfer1() == 5){
            bar01.setBackgroundDrawable(new ColorDrawable(0x8735de));
        }else if(roadBean4.getTransfer1() == 6){
            bar01.setBackgroundDrawable(new ColorDrawable(0xb85614));
        }else if(roadBean4.getTransfer1() == 7){
            bar01.setBackgroundDrawable(new ColorDrawable(0x79871e));
        }else if(roadBean4.getTransfer1() == 8){
            bar01.setBackgroundDrawable(new ColorDrawable(0xd60471));
        } else{
            bar01.setBackgroundDrawable(new ColorDrawable(0x474747));
        }

        if(roadBean4.getTransfer1() == 1){
            bar02.setBackgroundDrawable(new ColorDrawable(0x263c94));
        } else if(roadBean4.getTransfer1() == 2){
            bar02.setBackgroundDrawable(new ColorDrawable(0x45b550));
        } else if(roadBean4.getTransfer1() == 3){
            bar02.setBackgroundDrawable(new ColorDrawable(0xff780a));
        } else if(roadBean4.getTransfer1() == 4){
            bar02.setBackgroundDrawable(new ColorDrawable(0x2ca0de));
        } else if(roadBean4.getTransfer1() == 5){
            bar02.setBackgroundDrawable(new ColorDrawable(0x8735de));
        }else if(roadBean4.getTransfer1() == 6){
            bar02.setBackgroundDrawable(new ColorDrawable(0xb85614));
        }else if(roadBean4.getTransfer1() == 7){
            bar02.setBackgroundDrawable(new ColorDrawable(0x79871e));
        }else if(roadBean4.getTransfer1() == 8){
            bar02.setBackgroundDrawable(new ColorDrawable(0xd60471));
        } else{
            bar02.setBackgroundDrawable(new ColorDrawable(0x474747));
        }

        if(roadBean4.getTransfer1() == 1){
            bar3.setBackgroundDrawable(new ColorDrawable(0x263c94));
        } else if(roadBean4.getTransfer1() == 2){
            bar3.setBackgroundDrawable(new ColorDrawable(0x45b550));
        } else if(roadBean4.getTransfer1() == 3){
            bar3.setBackgroundDrawable(new ColorDrawable(0xff780a));
        } else if(roadBean4.getTransfer1() == 4){
            bar3.setBackgroundDrawable(new ColorDrawable(0x2ca0de));
        } else if(roadBean4.getTransfer1() == 5){
            bar3.setBackgroundDrawable(new ColorDrawable(0x8735de));
        }else if(roadBean4.getTransfer1() == 6){
            bar3.setBackgroundDrawable(new ColorDrawable(0xb85614));
        }else if(roadBean4.getTransfer1() == 7){
            bar3.setBackgroundDrawable(new ColorDrawable(0x79871e));
        }else if(roadBean4.getTransfer1() == 8){
            bar3.setBackgroundDrawable(new ColorDrawable(0xd60471));
        } else{
            bar3.setBackgroundDrawable(new ColorDrawable(0x474747));
        }


        if(roadBean4.getTransfer1() == 1){
            bar4.setBackgroundDrawable(new ColorDrawable(0x263c94));
        } else if(roadBean4.getTransfer1() == 2){
            bar4.setBackgroundDrawable(new ColorDrawable(0x45b550));
        } else if(roadBean4.getTransfer1() == 3){
            bar4.setBackgroundDrawable(new ColorDrawable(0xff780a));
        } else if(roadBean4.getTransfer1() == 4){
            bar4.setBackgroundDrawable(new ColorDrawable(0x2ca0de));
        } else if(roadBean4.getTransfer1() == 5){
            bar4.setBackgroundDrawable(new ColorDrawable(0x8735de));
        }else if(roadBean4.getTransfer1() == 6){
            bar4.setBackgroundDrawable(new ColorDrawable(0xb85614));
        }else if(roadBean4.getTransfer1() == 7){
            bar4.setBackgroundDrawable(new ColorDrawable(0x79871e));
        }else if(roadBean4.getTransfer1() == 8){
            bar4.setBackgroundDrawable(new ColorDrawable(0xd60471));
        } else{
            bar4.setBackgroundDrawable(new ColorDrawable(0x474747));
        }

        String tempTime = Integer.toString(roadBean4.getTime());
        txtTime.setText(tempTime + "분");


        return convertView;
    }
}
