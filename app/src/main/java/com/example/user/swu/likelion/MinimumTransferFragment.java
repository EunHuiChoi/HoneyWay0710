package com.example.user.swu.likelion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.user.swu.likelion.adapter.RoadAdapter;
import com.example.user.swu.likelion.adapter.RoadAdapter2;
import com.example.user.swu.likelion.adapter.RoadAdapter3;
import com.example.user.swu.likelion.adapter.RoadAdapter4;
import com.example.user.swu.likelion.adapter.StationAdapter;

import java.util.ArrayList;
import java.util.List;

public class MinimumTransferFragment extends Fragment  {

    public MinimumTransferFragment(){

    }

    ListView list101, list102, list103, list104,
            list201, list202, list203, list204, list301, list302, list303, list304,
            list401, list402, list403, list404, list501, list502, list503, list504;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_minimum_transfer, container, false);

        list101 = view.findViewById(R.id.list101);
        list102 = view.findViewById(R.id.list102);
        list103= view.findViewById(R.id.list103);
        list104= view.findViewById(R.id.list104);

        list201= view.findViewById(R.id.list201);
        list202= view.findViewById(R.id.list202);
        list203= view.findViewById(R.id.list203);
        list204= view.findViewById(R.id.list204);

        list301 = view.findViewById(R.id.list301);
        list302 = view.findViewById(R.id.list302);
        list303= view.findViewById(R.id.list303);
        list304= view.findViewById(R.id.list304);

        list401= view.findViewById(R.id.list401);
        list402= view.findViewById(R.id.list402);
        list403= view.findViewById(R.id.list403);
        list404= view.findViewById(R.id.list404);

        list501= view.findViewById(R.id.list501);
        list502= view.findViewById(R.id.list502);
        list503= view.findViewById(R.id.list503);
        list504= view.findViewById(R.id.list504);

        RoadBean r101 = new RoadBean();
        r101.setStation1("test");
        r101.setStation2("test");
        r101.setTransfer1(1);
        r101.setTime(10);

        RoadBean2 r102 = new RoadBean2();
        r102.setStation1("test");
        r102.setStation2("test");
        r102.setStation3("test");
        r102.setTransfer1(1);
        r102.setTransfer2(1);
        r102.setTime(10);

        RoadBean3 r103 = new RoadBean3();
        r103.setStation1("test");
        r103.setStation2("test");
        r103.setStation3("test");
        r103.setStation4("test");
        r103.setTransfer1(1);
        r103.setTransfer2(1);
        r103.setTransfer3(1);
        r103.setTime(10);

        RoadBean4 r104 = new RoadBean4();
        r104.setStation1("test");
        r104.setStation2("test");
        r104.setStation3("test");
        r104.setStation4("test");
        r104.setStation5("test");
        r104.setTransfer1(1);
        r104.setTransfer2(1);
        r104.setTransfer3(1);
        r104.setTransfer4(1);
        r104.setTime(10);

        RoadBean r201 = new RoadBean();
        r201.setStation1("test");
        r201.setStation2("test");
        r201.setTransfer1(1);
        r201.setTime(10);

        RoadBean2 r202 = new RoadBean2();
        r202.setStation1("test");
        r202.setStation2("test");
        r202.setStation3("test");
        r202.setTransfer1(1);
        r202.setTransfer2(1);
        r202.setTime(10);

        RoadBean3 r203= new RoadBean3();
        r203.setStation1("test");
        r203.setStation2("test");
        r203.setStation3("test");
        r203.setStation4("test");
        r203.setTransfer1(1);
        r203.setTransfer2(1);
        r203.setTransfer3(1);
        r203.setTime(10);

        RoadBean4 r204 = new RoadBean4();
        r204.setStation1("test");
        r204.setStation2("test");
        r204.setStation3("test");
        r204.setStation4("test");
        r204.setStation5("test");
        r204.setTransfer1(1);
        r204.setTransfer2(1);
        r204.setTransfer3(1);
        r204.setTransfer4(1);
        r204.setTime(10);

        RoadBean r301 = new RoadBean();
        r301.setStation1("test");
        r301.setStation2("test");
        r301.setTransfer1(1);
        r301.setTime(10);

        RoadBean2 r302 = new RoadBean2();
        r302.setStation1("test");
        r302.setStation2("test");
        r302.setStation3("test");
        r302.setTransfer1(1);
        r302.setTransfer2(1);
        r302.setTime(10);

        RoadBean3 r303 = new RoadBean3();
        r303.setStation1("test");
        r303.setStation2("test");
        r303.setStation3("test");
        r303.setStation4("test");
        r303.setTransfer1(1);
        r303.setTransfer2(1);
        r303.setTransfer3(1);
        r303.setTime(10);

        RoadBean4 r304 = new RoadBean4();
        r304.setStation1("test");
        r304.setStation2("test");
        r304.setStation3("test");
        r304.setStation4("test");
        r304.setStation5("test");
        r304.setTransfer1(1);
        r304.setTransfer2(1);
        r304.setTransfer3(1);
        r304.setTransfer4(1);
        r304.setTime(10);

        RoadBean r401 = new RoadBean();
        r401.setStation1("test");
        r401.setStation2("test");
        r401.setTransfer1(1);
        r401.setTime(10);

        RoadBean2 r402 = new RoadBean2();
        r402.setStation1("test");
        r402.setStation2("test");
        r402.setStation3("test");
        r402.setTransfer1(1);
        r402.setTransfer2(1);
        r402.setTime(10);

        RoadBean3 r403 = new RoadBean3();
        r403.setStation1("test");
        r403.setStation2("test");
        r403.setStation3("test");
        r403.setStation4("test");
        r403.setTransfer1(1);
        r403.setTransfer2(1);
        r403.setTransfer3(1);
        r403.setTime(10);

        RoadBean4 r404 = new RoadBean4();
        r404.setStation1("test");
        r404.setStation2("test");
        r404.setStation3("test");
        r404.setStation4("test");
        r404.setStation5("test");
        r404.setTransfer1(1);
        r404.setTransfer2(1);
        r404.setTransfer3(1);
        r404.setTransfer4(1);
        r404.setTime(10);

        RoadBean r501 = new RoadBean();
        r501.setStation1("test");
        r501.setStation2("test");
        r501.setTransfer1(1);
        r501.setTime(10);

        RoadBean2 r502 = new RoadBean2();
        r502.setStation1("test");
        r502.setStation2("test");
        r502.setStation3("test");
        r502.setTransfer1(1);
        r502.setTransfer2(1);
        r502.setTime(10);

        RoadBean3 r503 = new RoadBean3();
        r503.setStation1("test");
        r503.setStation2("test");
        r503.setStation3("test");
        r503.setStation4("test");
        r503.setTransfer1(1);
        r503.setTransfer2(1);
        r503.setTransfer3(1);
        r503.setTime(10);

        RoadBean4 r504 = new RoadBean4();
        r504.setStation1("test");
        r504.setStation2("test");
        r504.setStation3("test");
        r504.setStation4("test");
        r504.setStation5("test");
        r504.setTransfer1(1);
        r504.setTransfer2(1);
        r504.setTransfer3(1);
        r504.setTransfer4(1);
        r504.setTime(10);


        final List<RoadBean> RoadList101 = new ArrayList<>();
        RoadList101.add(r101);

        final List<RoadBean2> RoadList102 = new ArrayList<>();
        RoadList102.add(r102);

        final List<RoadBean3> RoadList103 = new ArrayList<>();
        RoadList103.add(r103);

        final List<RoadBean4> RoadList104 = new ArrayList<>();
        RoadList104.add(r104);

        final List<RoadBean> RoadList201 = new ArrayList<>();
        RoadList201.add(r201);

        final List<RoadBean2> RoadList202 = new ArrayList<>();
        RoadList202.add(r202);

        final List<RoadBean3> RoadList203 = new ArrayList<>();
        RoadList203.add(r203);

        final List<RoadBean4> RoadList204 = new ArrayList<>();
        RoadList204.add(r204);

        final List<RoadBean> RoadList301 = new ArrayList<>();
        RoadList301.add(r301);

        final List<RoadBean2> RoadList302 = new ArrayList<>();
        RoadList302.add(r302);

        final List<RoadBean3> RoadList303 = new ArrayList<>();
        RoadList303.add(r303);

        final List<RoadBean4> RoadList304 = new ArrayList<>();
        RoadList304.add(r304);

        final List<RoadBean> RoadList401 = new ArrayList<>();
        RoadList401.add(r401);

        final List<RoadBean2> RoadList402 = new ArrayList<>();
        RoadList402.add(r402);

        final List<RoadBean3> RoadList403 = new ArrayList<>();
        RoadList403.add(r403);

        final List<RoadBean4> RoadList404 = new ArrayList<>();
        RoadList404.add(r404);

        final List<RoadBean> RoadList501 = new ArrayList<>();
        RoadList501.add(r501);

        final List<RoadBean2> RoadList502 = new ArrayList<>();
        RoadList502.add(r502);

        final List<RoadBean3> RoadList503 = new ArrayList<>();
        RoadList503.add(r503);

        final List<RoadBean4> RoadList504 = new ArrayList<>();
        RoadList504.add(r504);

        RoadAdapter roadAdapter101 = new RoadAdapter(getActivity(),RoadList101);//Adapter를 생성한다.
        list101.setAdapter(roadAdapter101);//Adapter를 ListView에 부착시킨다.

        RoadAdapter roadAdapter201 = new RoadAdapter(getActivity(),RoadList201);//Adapter를 생성한다.
        list201.setAdapter(roadAdapter201);//Adapter를 ListView에 부착시킨다.

        RoadAdapter roadAdapter301 = new RoadAdapter(getActivity(),RoadList301);//Adapter를 생성한다.
        list301.setAdapter(roadAdapter301);//Adapter를 ListView에 부착시킨다.

        RoadAdapter roadAdapter401 = new RoadAdapter(getActivity(),RoadList401);//Adapter를 생성한다.
        list401.setAdapter(roadAdapter401);//Adapter를 ListView에 부착시킨다.

        RoadAdapter roadAdapter501 = new RoadAdapter(getActivity(),RoadList501);//Adapter를 생성한다.
        list501.setAdapter(roadAdapter501);//Adapter를 ListView에 부착시킨다.

        RoadAdapter2 roadAdapter102 = new RoadAdapter2(getContext(),RoadList102);//Adapter를 생성한다.
        list102.setAdapter(roadAdapter102);//Adapter를 ListView에 부착시킨다.

        RoadAdapter2 roadAdapter202 = new RoadAdapter2(getActivity(),RoadList202);//Adapter를 생성한다.
        list202.setAdapter(roadAdapter202);//Adapter를 ListView에 부착시킨다.

        RoadAdapter2 roadAdapter302 = new RoadAdapter2(getActivity(),RoadList302);//Adapter를 생성한다.
        list302.setAdapter(roadAdapter302);//Adapter를 ListView에 부착시킨다.

        RoadAdapter2 roadAdapter402 = new RoadAdapter2(getActivity(),RoadList402);//Adapter를 생성한다.
        list402.setAdapter(roadAdapter402);//Adapter를 ListView에 부착시킨다.

        RoadAdapter2 roadAdapter502 = new RoadAdapter2(getActivity(),RoadList502);//Adapter를 생성한다.
        list502.setAdapter(roadAdapter502);//Adapter를 ListView에 부착시킨다.

        RoadAdapter3 roadAdapter103 = new RoadAdapter3(getActivity(),RoadList103);//Adapter를 생성한다.
        list103.setAdapter(roadAdapter103);//Adapter를 ListView에 부착시킨다.

        RoadAdapter3 roadAdapter203 = new RoadAdapter3(getActivity(),RoadList203);//Adapter를 생성한다.
        list203.setAdapter(roadAdapter203);//Adapter를 ListView에 부착시킨다.

        RoadAdapter3 roadAdapter303 = new RoadAdapter3(getActivity(),RoadList303);//Adapter를 생성한다.
        list303.setAdapter(roadAdapter303);//Adapter를 ListView에 부착시킨다.

        RoadAdapter3 roadAdapter403 = new RoadAdapter3(getActivity(),RoadList403);//Adapter를 생성한다.
        list403.setAdapter(roadAdapter403);//Adapter를 ListView에 부착시킨다.

        RoadAdapter3 roadAdapter503 = new RoadAdapter3(getActivity(),RoadList503);//Adapter를 생성한다.
        list503.setAdapter(roadAdapter503);//Adapter를 ListView에 부착시킨다.

        RoadAdapter4 roadAdapter104 = new RoadAdapter4(getActivity(),RoadList104);//Adapter를 생성한다.
        list104.setAdapter(roadAdapter104);//Adapter를 ListView에 부착시킨다.

        RoadAdapter4 roadAdapter204 = new RoadAdapter4(getActivity(),RoadList204);//Adapter를 생성한다.
        list204.setAdapter(roadAdapter204);//Adapter를 ListView에 부착시킨다.

        RoadAdapter4 roadAdapter304 = new RoadAdapter4(getActivity(),RoadList304);//Adapter를 생성한다.
        list304.setAdapter(roadAdapter304);//Adapter를 ListView에 부착시킨다.

        RoadAdapter4 roadAdapter404 = new RoadAdapter4(getActivity(),RoadList404);//Adapter를 생성한다.
        list404.setAdapter(roadAdapter404);//Adapter를 ListView에 부착시킨다.

        RoadAdapter4 roadAdapter504 = new RoadAdapter4(getActivity(),RoadList504);//Adapter를 생성한다.
        list504.setAdapter(roadAdapter504);//Adapter를 ListView에 부착시킨다.

        return view;
    }

}
