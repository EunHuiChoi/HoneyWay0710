package com.example.user.swu.likelion.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.swu.likelion.CongestionActivity;
import com.example.user.swu.likelion.R;
import com.example.user.swu.likelion.Select2Activity;
import com.example.user.swu.likelion.SelectActivity;
import com.example.user.swu.likelion.StationBean;

import java.util.List;

public class Station2Adapter extends BaseAdapter {
    private Context mContext;
    private List<StationBean> mList;

    //public  ArrayList<String> DepartArrive; //세영

    public Station2Adapter(Context context, List<StationBean> list) {
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
        //인플레이팅 하는 작업
        // mDepartArrive.add("동대문역사문화공원");
        LayoutInflater inflater = (LayoutInflater)
                mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.view_station, null);

        //해당 ROW의 데이터를 찾는 작업
        final StationBean stationBean = mList.get(position);

        //인플레이팅 된 뷰에서 ID 찾는 작업
        ImageView imgSeoul = convertView.findViewById(R.id.imgSeoul);
        TextView txtStationName = convertView.findViewById(R.id.txtStationName);
        ImageView imgStationNum = convertView.findViewById(R.id.imgStationNum);
        ImageView imgStationNum2 = convertView.findViewById(R.id.imgStationNum2);
        ImageView imgStationNum3 = convertView.findViewById(R.id.imgStationNum3);
        final LinearLayout stationItem = convertView.findViewById(R.id.stationItem);

        //데이터 셋팅
        imgSeoul.setImageResource( stationBean.getImgSeoul() );
        txtStationName.setText( stationBean.getTxtStationName() );
        imgStationNum.setImageResource( stationBean.getImgStationNum() );
        imgStationNum2.setImageResource( stationBean.getImgStationNum2() );
        imgStationNum3.setImageResource( stationBean.getImgStationNum3() );

        //리스트 클릭했을 때의 이벤트 설정
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Select2Activity.DepartArrive.size()>=1){
                    Select2Activity.DepartArrive.remove(0);
                }
                Select2Activity.DepartArrive.add(0,stationBean.getTxtStationName());

                Select2Activity.text_start.setText(stationBean.getTxtStationName());

                //Toast.makeText(mContext, "클릭", Toast.LENGTH_SHORT).show();
                //stationItem.setBackgroundColor(0xF5DA81);
                //convertView.setBackgroundColor(0x8CC7EC);

                //Intent i = new Intent(mContext, SelectActivity.class);
                //선택된 ROW의 Bean 데이터를 싣는다.
                //i.putExtra("stationBean",stationBean.getTxtStationName() );
                //mContext.startActivity(i); //화면이동

            }
        });
        return convertView;
    } // end getView
}
