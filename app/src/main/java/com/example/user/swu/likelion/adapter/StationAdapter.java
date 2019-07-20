package com.example.user.swu.likelion.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.swu.likelion.R;
import com.example.user.swu.likelion.SelectActivity;
import com.example.user.swu.likelion.StationBean;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class StationAdapter extends BaseAdapter{

    private Context mContext;
    private List<StationBean> mList;

    //public  ArrayList<String> DepartArrive; //세영

    public StationAdapter(Context context, List<StationBean> list) {
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
        convertView = inflater.inflate(R.layout.view_station, null);

        //해당 ROW의 데이터를 찾는 작업
        final StationBean stationBean = mList.get(position);

        //인플레이팅 된 뷰에서 ID 찾는 작업
        ImageView imgSeoul = convertView.findViewById(R.id.imgSeoul);
        TextView txtStationName = convertView.findViewById(R.id.txtStationName);
        ImageView imgStationNum = convertView.findViewById(R.id.imgStationNum);
        ImageView imgStationNum2 = convertView.findViewById(R.id.imgStationNum2);
        ImageView imgStationNum3 = convertView.findViewById(R.id.imgStationNum3);
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
                if (SelectActivity.DepartArrive.get(0).equals(stationBean.getTxtStationName())){
                    Toast.makeText(mContext,"출발역과 동일합니다.", Toast.LENGTH_SHORT).show();
                }else {
                    if (SelectActivity.DepartArrive.size() >= 2) {
                        SelectActivity.DepartArrive.remove(1);
                    }
                    SelectActivity.DepartArrive.add(1, stationBean.getTxtStationName());

                    SelectActivity.txtvArrive.setText(stationBean.getTxtStationName());
                }

            }
        });
        return convertView;
    } // end getView
}
