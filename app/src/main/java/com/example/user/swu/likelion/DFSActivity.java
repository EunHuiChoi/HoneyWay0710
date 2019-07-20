package com.example.user.swu.likelion;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class DFSActivity extends AppCompatActivity{

    String TAG = this.getClass().getSimpleName();

    String station1[] = {"1호선", "소요산", "동두천", "보산", "동두천중앙", "지행", "덕정", "덕계",
            "양주", "녹양", "가능", "의정부", "회룡", "망월사", "도봉산", "도봉", "방학", "창동",
            "녹천", "월계", "광운대", "석계", "신이문", "외대앞", "회기",
            //있는 데이터 시작
            "청량리", "제기동", "신설동",
            "동묘앞", "동대문", "종로5가", "종로3가", "종각", "시청", "서울역",
            //있는 데이터 끝
            "남영", "용산",
            "노량진", "대방", "신길", "영등포", "신도림", "구로", "구일", "개봉", "오류동", "온수",
            "역곡", "소사", "부천", "중동", "송내", "부개", "부평", "백운", "동암", "간석", "주안",
            "도화", "제물포", "도원", "동인천", "인천", "가산", "독산", "금천구청", "석수", "관악",
            "안양", "명학", "금정", "군포", "당정", "의왕", "성균관대", "화서", "수원", "세류", "병점",
            "세마", "오산대", "오산", "진위", "송탄", "서정리", "지제", "평택", "성환", "직산",
            "두정", "천안", "광명", "서동탄", "봉명", "쌍용", "아산", "배방", "온양온천", "신창"};
    String station2[] = {"2호선", "낙성대", "사당", "방배", "서초", "교대", "강남", "역삼", "선릉",
            "삼성", "종합운동장", "잠실새내", "잠실", "잠실나루", "강변", "구의", "건대입구", "성수",
            "뚝섬", "한양대", "왕십리", "상왕십리", "신당", "동대문역사문화공원", "을지로4가", "을지로3가",
            "을지로입구", "시청", "충정로", "아현", "이대", "신촌", "홍대입구", "합정", "당산",
            "영등포구청", "문래", "신도림", "대림", "구로디지털단지", "신대방", "신림", "봉천",
            "서울대입구"};
    String station3[] = {"3호선", "오금", "경찰병원", "가락시장", "수서", "일원", "대청", "학여울",
            "대치", "도곡", "매봉", "양재",
            "남부터미널", "교대", "고속터미널", "잠원", "신사", "압구정", "옥수", "금호", "약수",
            "동대입구", "충무로", "을지로3가", "종로3가", "안국", "경복궁", "독립문", "무악재",
            "홍제", "녹번", "불광", "연신내", "구파발", "지축", "삼송", "원흥", "원당", "화정", "대곡",
            "백석", "마두", "정발산", "주엽", "대화"};
    String station4[] = {"4호선", "오이도", "정왕", "신길온천", "안산", "초지", "고잔", "중앙",
            "한대앞", "상록수", "반월", "대야미", "수리산", "산본", "금정", "범계", "평촌", "인덕원",
            "정부종합청사", "과천", "대공원", "경마공원", "선바위", "남태령", "사당", "이수",
            "동작", "이촌", "신용산", "삼각지", "숙대입구", "서울역", "회현", "명동", "충무로",
            "동대문역사문화공원", "동대문", "혜화", "한성대입구", "성신여대입구", "길음", "미아사거리",
            "미아", "수유", "쌍문", "창동", "노원", "상계", "당고개"};
    String station5[] = {"5호선", "방화", "개화산", "김포공항", "송정", "마곡", "발산", "우장산",
            "화곡", "까치산", "신정", "목동", "오목교", "양평", "영등포구청",
            "영등포시장", "신길", "여의도", "여의나루", "마포", "공덕", "애오개", "충정로",
            "서대문", "광화문", "종로3가", "을지로4가", "동대문역사문화공원", "청구", "신금호",
            "행당", "왕십리", "마장", "답십리", "장한평", "군자", "아차산", "광나루", "천호",
            "강동", "둔촌동", "올림픽공원", "방이", "오금", "개롱", "거여", "마천", "길동",
            "굽은다리", "명일", "고덕", "상일동"};
    String station6[] = {"6호선", "봉화산", "화랑대", "태릉입구", "석계", "돌곶이", "상월곡",
            "월곡", "고려대", "안암", "보문", "창신", "동묘앞", "신당", "청구", "약수", "버티고개",
            "한강진", "이태원", "녹사평", "삼각지", "효창공원앞", "공덕", "대흥", "광흥창", "상수",
            "합정", "망원", "마포구청", "월드컵경기장", "디지털미디어시티", "증산", "새절", "응암",
            "역촌", "불광", "독바위", "연신내", "구산"};
    String station7[] = {"7호선", "장암", "도봉산", "수락산", "마들", "노원", "중계", "하계", "공릉",
            "태릉입구", "먹골", "중화", "상봉", "면목", "사가정", "용마산", "중곡", "군자",
            "어린이대공원", "건대입구", "뚝섬유원지", "청담", "강남구청", "학동", "논현", "반포",
            "고속터미널", "내방", "이수", "남성", "숭실대입구", "상도", "장승배기", "신대방삼거리",
            "보라매", "신풍", "대림", "남구로", "가산디지털단지", "철산", "광명사거리", "천왕", "온수",
            "까치울",
            "부천종합운동장", "춘의", "신중동", "부천시청", "상동", "삼산체육관", "굴포천", "부평구청"};
    String station8[] = {"8호선","암사", "천호", "강동구청", "몽촌토성", "잠실", "석촌", "송파",
            "가락시장", "문정", "장지", "복정", "산성", "남한산성입구", "단대오거리", "신흥", "수진", "모란"};
    String stationSS[]={"성수지선", "신설동", "용두", "신답", "용답", "성수"};
    String stationSJ[]={"신정지선", "까치산", "신정네거리", "양천구청", "도림천", "신도림"};

    int st1Length = station1.length - 1;
    int st2Length = station2.length - 1;
    int st3Length = station3.length - 1;
    int st4Length = station4.length - 1;
    int st5Length = station5.length - 1;
    int st6Length = station6.length - 1;
    int st7Length = station7.length - 1;
    int st8Length = station8.length - 1;
    int stSSLength = stationSS.length - 1;
    int stSJLength = stationSJ.length - 1;
    //int stKLength = stationK.length - 1;

    int fullLength = st1Length + st2Length + st3Length + st4Length + st5Length + st6Length + st7Length + st8Length + stSSLength + stSJLength + 1;
    int limit = 0;
    int[] hwanseungs = new int[61];

    //structInfo[] infos = new structInfo[5];

    static int topN = 5;

    int min = Integer.MAX_VALUE;

    Comparator<structInfo> comparator = new totalTimeComparator();
    PriorityQueue<structInfo> queue = new PriorityQueue<structInfo>(topN,comparator);

    /////////////////////////////////////////////////////
    String[] index2Time = {"time0530","time0600","time0630","time0700","time0730","time0800","time0830","time0900","time0930","time1000","time1030","time1100","time1130","time1200","time1230","time1300","time1330","time1400","time1430","time1500","time1530","time1600","time1630","time1700","time1730","time1800","time1830","time1900","time1930","time2000","time2030","time2100","time2130","time2200","time2230","time2300","time2330","time2400","time2430"};

    ///////////////////////////////혼잡도 DB
    // 혼잡도 DB 불러오기
    final String DATABASE_NAME = "congestion.db";
    final String PACKAGE_DIRECTORY = "/data/data/com.example.user.swu.likelion/";
    Context mContext = this;
    myDBHelper myHelper;
    SQLiteDatabase congestionDB;

    // 배열선언
    //String[] arrayStationNo;

    // 배열 사이즈
    int rowSize;

    String hour;
    String minute;
    String day;
    int hourInt;
    int minuteInt;
    String depart;
    String arrive;

    /////////////////////////////혼잡도 평균
    String[] avgCongest = {"매우여유", "여유", "보통", "혼잡", "매우혼잡"};
    Map<String, Integer> CongestMap = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CongestMap.put("매우여유",0);
        CongestMap.put("여유",2);
        CongestMap.put("보통",4);
        CongestMap.put("혼잡",8);
        CongestMap.put("매우혼잡",16);

        Intent intent = getIntent();
        hour = intent.getStringExtra("HOUR");
        minute = intent.getStringExtra("MINUTE");
        day = intent.getStringExtra("DAY");
        depart = intent.getStringExtra("DEPART");
        arrive =intent.getStringExtra("ARRIVE");

        hourInt = Integer.parseInt(hour);
        minuteInt = Integer.parseInt(minute);

        //String realTime = returnTime(hourInt, minuteInt);
        //realTime = "time2030";

        //String fullTime = hour+minute;

        DFSAlgorithm d = new DFSAlgorithm();
        ////////////////////////1호선////////////////////////////
        d.inputData(1,2,3,3); //소요산 동두천
        d.inputData(2,3,3,3); //동두천 보산
        d.inputData(3,4,3,3); //보산 동두천중앙
        d.inputData(4,5,3,3); //동두천중앙 지행
        d.inputData(5,6,3,3); //지행 덕정
        d.inputData(6,7,3,3); //덕정 덕계
        d.inputData(7,8,3,3); //덕계 양주
        d.inputData(8,9,3,3); //양주 녹양
        d.inputData(9,10,3,3); //녹양 가능
        d.inputData(10,11,3,3); //가능 의정부
        d.inputData(11,12,3,3); //의정부 회룡
        d.inputData(12,13,3,3); //회룡 망월사
        d.inputData(13,14,3,3); //망월사 도봉산
        d.inputData(14,15,3,3); //도봉산 도봉
        d.inputData(15,16,2,2); //도봉 방학
        d.inputData(16,17,2,2); //방학 창동
        d.inputData(17,18,2,2); //창동 녹천
        d.inputData(18,19,2,2); //녹천 월계
        d.inputData(19,20,2,2); //월계 광운대
        d.inputData(20,21,2,2); //광운대 석계
        d.inputData(21,22,2,2); //석계 신이문
        d.inputData(22,23,2,2); //신이문 외대앞
        d.inputData(23,24,3,3); //외대앞 회기
        d.inputData(24,25,2,2); //회기 청량리
        d.inputData(25,26,2,2); //청량리 제기동
        d.inputData(26,27,1,1); //제기동 신설동
        d.inputData(27,28,1,1); //신설동 동묘앞
        d.inputData(28,29,1,1); //동묘앞 동대문
        d.inputData(29,30,1,1); //동대문 종로5가
        d.inputData(30,31,2,2); //종로5가 종로3가
        d.inputData(31,32,2,2); //종로3가 종각
        d.inputData(32,33,3,3); //종각 시청
        d.inputData(33,34,2,2); //시청 서울역
        d.inputData(34,35,3,3); //서울역 남영
        d.inputData(35,36,2,2); //남영 용산
        d.inputData(36,37,3,3); //용산 노량진
        d.inputData(37,38,3,3); //노량진 대방
        d.inputData(38,39,2,2); //대방 신길
        d.inputData(39,40,2,2); //신길 영등포
        d.inputData(40,41,2,2); //영등포 신도림
        d.inputData(41,42,2,2); //신도림 구로
        d.inputData(42,43,3,3); //구로 구일
        d.inputData(43,44,2,2); //구일 개봉
        d.inputData(44,45,2,2); //개봉 오류동
        d.inputData(45,46,3,3); //오류동 온수
        ////////////////////////////////////////온수부터 인천까지는 안 이음//////////////////
        d.inputData(42,63,5,5); //구로 가산
        d.inputData(63,64,3,3); //가산 독산
        d.inputData(64,65,3,3); //독산 금천구청
        d.inputData(65,66,3,3); //금천구청 석수
        d.inputData(66,67,3,3); //석수 관악
        d.inputData(67,68,3,3); //관악 안양
        d.inputData(68,69,3,3); //안양 명학
        d.inputData(69,70,4,4); //명학 금정
        d.inputData(70,71,4,4); //금정 군포
        d.inputData(71,72,4,4); //군포 당정
        d.inputData(72,73,4,4); //당정 의왕
        d.inputData(73,74,4,4); //의왕 성균관대
        d.inputData(74,75,4,4); //성균관대 화서
        d.inputData(75,76,4,4); //화서 수원
        d.inputData(76,77,4,4); //수원 세류
        d.inputData(77,78,4,4); //세류 병점
        d.inputData(78,79,4,4); //병점 세마
        d.inputData(79,80,4,4); //세마 오산대
        d.inputData(80,81,4,4); //오산대 오산
        d.inputData(81,82,4,4); //오산 진위
        d.inputData(82,83,4,4); //진위 송탄
        d.inputData(83,84,4,4); //송탄 서정리
        d.inputData(84,85,4,4); //서정리 지제
        d.inputData(85,86,4,4); //지제 평택
        d.inputData(86,87,4,4); //평택 성환
        d.inputData(87,88,4,4); //성환 직산
        d.inputData(88,89,4,4); //직산 두정
        d.inputData(89,90,4,4); //두정 천안
        d.inputData(65,91,5,5); //금천구청 광명
        d.inputData(78,92,4,4); //병점 서동탄
        d.inputData(90,93,4,4); //천안 봉명
        d.inputData(93,94,4,4); //봉명 쌍용
        //////////////////////쌍용에서 신창 까지 생략

        //////////////////////////////2호선//////////////////////////////
        d.inputData(99,100,2,2); //낙성대 사당
        d.inputData(100,101,3,3); //사당 방배
        d.inputData(101,102,2,2); //방배 서초
        d.inputData(102,103,2,2); //서초 교대
        d.inputData(103,104,2,2); //교대 강남
        d.inputData(104,105,1,1); //강남 역삼
        d.inputData(105,106,2,2); //역삼 선릉
        d.inputData(106,107,2,2); //선릉 삼성
        d.inputData(107,108,2,2); //삼성 종합운동장
        d.inputData(108,109,2,2); //종합운동장 잠실새내
        d.inputData(109,110,2,2); //잠실새내 잠실
        d.inputData(110,111,2,2); //잠실 잠실나루
        d.inputData(111,112,3,3); //잠실나루 강변
        d.inputData(112,113,1,1); //강변 구의
        d.inputData(113,114,3,3); //구의 건대입구
        d.inputData(114,115,2,2); //건대입구 성수
        d.inputData(115,116,2,2); //성수 뚝섬
        d.inputData(116,117,3,3); //뚝섬 한양대
        d.inputData(117,118,2,2); //한양대 왕십리
        d.inputData(118,119,1,1); //왕십리 상왕십리
        d.inputData(119,120,2,2); //상왕십리 신당
        d.inputData(120,121,2,2); //신당 동대문역사문화공원
        d.inputData(121,122,2,2); //동대문역사문화공원 을지로4가
        d.inputData(122,123,2,2); //을지로4가 을지로3가
        d.inputData(123,124,2,2); //을지로3가 을지로입구
        d.inputData(124,125,1,1); //을지로입구 시청
        d.inputData(125,126,1,1); //시청 충정로
        d.inputData(126,127,3,3); //충정로 아현
        d.inputData(127,128,2,2); //아현 이대
        d.inputData(128,129,1,1); //이대 신촌
        d.inputData(129,130,2,2); //신촌 홍대입구
        d.inputData(130,131,2,2); //홍대입구 합정
        d.inputData(131,132, 2,2); //합정 당산
        d.inputData(132,133,2,2); //당산 영등포구청
        d.inputData(133,134,2,2); //영등포구청 문래
        d.inputData(134,135,2,2); //문래 신도림
        d.inputData(135,136,3,3); //신도림 대림
        d.inputData(136,137,2,2); //대림 구로디지털단지
        d.inputData(137,138,2,2); //구로디지털단지 신대방
        d.inputData(138,139,2,2); //신대방 신림
        d.inputData(139,140,2,2); //신림 봉천
        d.inputData(140,141,2,2); //봉천 서울대입구
        d.inputData(141, 99,2,2); //서울대입구 낙성대

        //////////////////////////////3호선//////////////////////////////
        d.inputData(142, 143, 2, 2);//오금 경찰병원
        d.inputData(143, 144, 2, 2);//경찰병원 가락시장
        d.inputData(144, 145, 2, 2);//가락시장 수서
        d.inputData(145, 146, 3, 3);//수서 일원
        d.inputData(146, 147, 2, 2);//일원 대청
        d.inputData(147, 148, 2, 2);//대청 학여울
        d.inputData(148, 149, 1, 1);//학여울 대치
        d.inputData(149, 150, 1, 1);//대치 도곡
        d.inputData(150, 151, 2, 2);//도곡 매봉
        d.inputData(151, 152, 2, 2);//매봉 양재
        d.inputData(152, 153, 3, 3);//양재 남부터미널
        d.inputData(153, 154, 2, 2);//남부터미널 교대
        d.inputData(154, 155, 2, 2);//교대 고속터미널
        d.inputData(155, 156, 2, 2);//고속터미널 잠원
        d.inputData(156, 157, 2, 2);//잠원 신사
        d.inputData(157, 158, 3, 3);//신사 압구정
        d.inputData(158, 159, 2, 2);//압구정 옥수
        d.inputData(159, 160, 2, 2);//옥수 금호
        d.inputData(160, 161, 1, 1);//금호 약수
        d.inputData(161, 162, 2, 2);//약수 동대입구
        d.inputData(162, 163, 2, 2);//동대입구 충무로
        d.inputData(163, 164, 1, 1);//충무로 을지로3가
        d.inputData(164, 165, 1, 1);//을지로3가 종로3가
        d.inputData(165, 166, 2, 2);//종로3가 안국
        d.inputData(166, 167, 2, 2);//안국 경복궁
        d.inputData(167, 168, 3, 3);//경복궁 독립문
        d.inputData(168, 169, 2, 2);//독립문 무악재
        d.inputData(169, 170, 2, 2);//무악재 홍제
        d.inputData(170, 171, 2, 2);//홍제 녹번
        d.inputData(171, 172, 2, 2);//녹번 불광
        d.inputData(172, 173, 2, 2);//불광 연신내
        d.inputData(173, 174, 3, 3);//연신내 구파발
        d.inputData(174, 175, 3, 3);//구파발 지축
        d.inputData(175, 176, 4, 4);//지축 삼송
        d.inputData(176, 177, 4, 4);//삼송 원흥
        d.inputData(177, 178, 2, 2);//원흥 원당
        d.inputData(178, 179, 3, 3);//원당 화정
        d.inputData(179, 180, 4, 4);//화정 대곡
        d.inputData(180, 181, 3, 3);//대곡 백석
        d.inputData(181, 182, 3, 3);//백석 마두
        d.inputData(182, 183, 2, 2);//마두 정발산
        d.inputData(183, 184, 3, 3);//정발산 주엽
        d.inputData(184, 185, 2, 2);//주엽 대화

        /////////////////4호선///////////////
        d.inputData(186, 187, 4, 4);//오이도 정왕
        d.inputData(187, 188, 4, 4);//정왕 신길온천
        d.inputData(188, 189, 4, 4);//신길온천 안산
        d.inputData(189, 190, 2, 2);//안산 공단
        d.inputData(190, 191, 3, 3);//공단 고잔
        d.inputData(191, 192, 2, 2);//고잔 중앙
        d.inputData(192, 193, 3, 3);//중앙 한대앞
        d.inputData(193, 194, 3, 3);//한대앞 상록수
        d.inputData(194, 195, 3, 3);//상록수 반월
        d.inputData(195, 196, 4, 4);//반월 대야미
        d.inputData(196, 197, 3, 3);//대야미 수리산
        d.inputData(197, 198, 2, 2);//수리산 산본
        d.inputData(198, 199, 3, 3);//산본 금정
        d.inputData(199, 200, 3, 3);//금정 범계
        d.inputData(200, 201, 2, 2);//범계 평촌
        d.inputData(201, 202, 4, 4);//평촌 인덕원
        d.inputData(202, 203, 3, 3);//인덕원 정부종합청사
        d.inputData(203, 204, 2, 2);//정부종합청사 과천
        d.inputData(204, 205, 2, 2);//과천 대공원
        d.inputData(205, 206, 2, 2);//대공원 경마공원
        d.inputData(206, 207, 3, 3);//경마공원 선바위
        d.inputData(207, 208, 3, 3);//선바위 남태령
        d.inputData(208, 209, 1, 1);//남태령 사당
        d.inputData(209, 210, 2, 2);//사당 이수
        d.inputData(210, 211, 3, 3);//이수 동작
        d.inputData(211, 212, 4, 4);//동작 이촌
        d.inputData(212, 213, 2, 2);//이촌 신용산
        d.inputData(213, 214, 1, 1);//신용산 삼각지
        d.inputData(214, 215, 2, 2);//삼각지 숙대입구
        d.inputData(215, 216, 2, 2);//숙대입구 서울역
        d.inputData(216, 217, 2, 2);//서울역 회현
        d.inputData(217, 218, 2, 2);//회현 명동
        d.inputData(218, 219, 1, 1);//명동 충무로
        d.inputData(219, 220, 2, 2);//충무로 동대문역사문화공원
        d.inputData(220, 221, 2, 2);//동대문역사문화공원 동대문
        d.inputData(221, 222, 2, 2);//동대문 혜화
        d.inputData(222, 223, 2, 2);//혜화 한성대입구
        d.inputData(223, 224, 3, 3);//한성대입구 성신여대입구
        d.inputData(224, 225, 2, 2);//성신여대입구 길음
        d.inputData(225, 226, 2, 2);//길음 미아삼거리
        d.inputData(226, 227, 2, 2);//미아삼거리 미아
        d.inputData(227, 228, 3, 3);//미아 수유
        d.inputData(228, 229, 2, 2);//수유 쌍문
        d.inputData(229, 230, 2, 2);//쌍문 창동
        d.inputData(230, 231, 2, 2);//창동 노원
        d.inputData(231, 232, 2, 2);//노원 상계
        d.inputData(232, 233, 3, 3);//상계 당고개

        //////////////////////////////5호선//////////////////////////////
        d.inputData(234,235,2,2); //방화 개화산
        d.inputData(235,236,2,2); //개화산 김포공항
        d.inputData(236,237,2,2); //김포공항 송정
        d.inputData(237,238,2,2); //송정 마곡
        d.inputData(238,239,2,2); //마곡 발산
        d.inputData(239,240,2,2); //발산 우장산
        d.inputData(240,241,2,2); //우장산 화곡
        d.inputData(241,242,2,2); //화곡 까치산
        d.inputData(242,243,2,2); //까치산 신정
        d.inputData(243,244,2,2); //신정 목동
        d.inputData(244,245,2,2); //목동 오목교
        d.inputData(245,246,2,2); //오목교 양평
        d.inputData(246,247,2,2); //양평 영등포구청
        d.inputData(247,248,2,2); //영등포구청 영등포시장
        d.inputData(248,249,2,2); //영등포시장 신길
        d.inputData(249,250,2,2); //신길 여의도
        d.inputData(250,251,2,2); //여의도 여의나루
        d.inputData(251,252,2,2); //여의나루 마포
        d.inputData(252,253,2,2); //마포 공덕
        d.inputData(253,254,2,2); //공덕 애오개
        d.inputData(254,255,2,2); //애오개 충정로
        d.inputData(255,256,2,2); //충정로 서대문
        d.inputData(256,257,2,2); //서대문 광화문
        d.inputData(257,258,2,2); //광화문 종로3가
        d.inputData(258,259,2,2); //종로3가 을지로4가
        d.inputData(259,260,2,2); //을지로4가 동대문역사문화공원
        d.inputData(260,261,2,2); //동대문역사문화공원 청구
        d.inputData(261,262,2,2); //청구 신금호
        d.inputData(262,263,2,2); //신금호 행당
        d.inputData(263,264,2,2); //행당 왕십리
        d.inputData(264,265,2,2); //왕십리 마장
        d.inputData(265,266,2,2); //마장 답십리
        d.inputData(266,267,2,2); //답십리 장한평
        d.inputData(267,268,2,2); //장한평 군자
        d.inputData(268,269,2,2); //군자 아차산
        d.inputData(269,270,2,2); //아차산 광나루
        d.inputData(270,271,2,2); //광나루 천호
        d.inputData(271,272,2,2); //천호 강동
        d.inputData(272,273,2,2); //강동 둔촌동
        d.inputData(273,274,2,2); //둔촌동 올림픽공원
        d.inputData(274,275,2,2); //올림픽공원 방이
        d.inputData(275,276,2,2); //방이 오금
        d.inputData(276,277,2,2); //오금 개룡
        d.inputData(277,278,2,2); //개룡 거여
        d.inputData(278,279,3,3); //거여 마천
        d.inputData(272,280,2,2); //강동 길동
        d.inputData(280,281,2,2); //길동 굽은다리
        d.inputData(281,282,2,2); //굽은다리 명일
        d.inputData(282,283,2,2); //명일 고덕
        d.inputData(283,284,3,3); //고덕 상일동

        ///////////////////6호선////////////
        d.inputData(285, 286, 2, 2);//봉화산 화랑대
        d.inputData(286, 287, 2, 2);//화랑대 태릉입구
        d.inputData(287, 288, 2, 2);//태릉입구 석계
        d.inputData(288, 289, 2, 2);//석계 돌곶이
        d.inputData(289, 290, 2, 2);//돌곶이 상월곡
        d.inputData(290, 291, 2, 2);//상월곡 월곡
        d.inputData(291, 292, 2, 2);//월곡 고려대
        d.inputData(292, 293, 2, 2);//고려대 안암
        d.inputData(293, 294, 2, 2);//안암 보문
        d.inputData(294, 295, 2, 2);//보문 창신
        d.inputData(295, 296, 2, 2);//창신 동묘앞
        d.inputData(296, 297, 2, 2);//동묘앞 신당
        d.inputData(297, 298, 2, 2);//신당 청구
        d.inputData(298, 299, 2, 2);//청구 약수
        d.inputData(299, 300, 2, 2);//약수 버티고개
        d.inputData(300, 301, 2, 2);//버티고개 한강진
        d.inputData(301, 302, 2, 2);//한강진 이태원
        d.inputData(302, 303, 2, 2);//이태원 녹사평
        d.inputData(303, 304, 2, 2);//녹사평 삼각지
        d.inputData(304, 305, 2, 2);//삼각지 효창공원앞
        d.inputData(305, 306, 2, 2);//효창공원앞 공덕
        d.inputData(306, 307, 2, 2);//공덕 대흥
        d.inputData(307, 308, 2, 2);//대흥 광흥창
        d.inputData(308, 309, 2, 2);//광흥창 상수
        d.inputData(309, 310, 2, 2);//상수 합정
        d.inputData(310, 311, 2, 2);//합정 망원
        d.inputData(311, 312, 2, 2);//망원 마포구청
        d.inputData(312, 313, 2, 2);//마포구청 월드컵경기장
        d.inputData(313, 314, 2, 2);//월드컵경기장 디지털미디어시티
        d.inputData(314, 315, 2, 2);//디지털미디어시티 증산
        d.inputData(315, 316, 2, 2);//증산 새절
        d.inputData(316, 317, 2, 2);//새절 응암
        d.inputData(317, 318, 2, 2);//응암 역촌
        d.inputData(318, 319, 2, 2);//역촌 불광
        d.inputData(319, 320, 2, 2);//불광 독바위
        d.inputData(320, 321, 2, 2);//독바위 연신내
        d.inputData(321, 322, 2, 2);//연신내 구산
        d.inputData(322, 317, 0, 2);//구산 응암

        /////////////////////////////7호선////////////////////////////////
        d.inputData(323,324,2,2); //장암 도봉산
        d.inputData(324,325,2,2); //도봉산 수락산
        d.inputData(325,326,3,3); //수락산 마들
        d.inputData(326,327,2,2); //마들 노원
        d.inputData(327,328,2,2); //노원 중계
        d.inputData(328,329,1,1); //중계 하계
        d.inputData(329,330,2,2); //하계 공릉
        d.inputData(330,331,2,2); //공릉 태릉입구
        d.inputData(331,332,2,2); //태릉입구 먹골
        d.inputData(332,333,1,1); //먹골 중화
        d.inputData(333,334,3,3); //중화 상봉
        d.inputData(334,335,1,1); //상봉 면목
        d.inputData(335,336,2,2); //면목 사가정
        d.inputData(336,337,2,2); //사가정 용마산
        d.inputData(337,338,1,1); //용마산 중곡
        d.inputData(338,339,2,2); //중곡 군자
        d.inputData(339,340,2,2); //군자 어린이대공원
        d.inputData(340,341,2,2); //어린이대공원 건대입구
        d.inputData(341,342,2,2); //건대입구 뚝섬유원지
        d.inputData(342,343,3,3); //뚝섬유원지 청담
        d.inputData(343,344,2,2); //청담 강남구청
        d.inputData(344,345,2,2); //강남구청 학동
        d.inputData(345,346,2,2); //학동 논현
        d.inputData(346,347,1,1); //논현 반포
        d.inputData(347,348,2,2); //반포 고속터미널
        d.inputData(348,349,3,3); //고속터미널 내방
        d.inputData(349,350,2,2); //내방 이수
        d.inputData(350,351,2,2); //이수 남성
        d.inputData(351,352,3,3); //남성 숭실대입구
        d.inputData(352,353,2,2); //숭실대입구 상도
        d.inputData(353,354,2,2); //상도 장승배기
        d.inputData(354,355,2,2); //장승배기 신대방삼거리
        d.inputData(355,356,2,2); //신대방삼거리 보라매
        d.inputData(356,357,2,2); //보라매 신풍
        d.inputData(357,358,2,2); //신풍 대림
        d.inputData(358,359,2,2); //대림 남구로
        d.inputData(359,360,2,2); //남구로 가산디지털단지
        d.inputData(360,361,3,3); //가산디지털단지 철산
        d.inputData(361,362,2,2); //철산 광명사거리
        d.inputData(362,363,2,2); //광명사거리 천왕
        d.inputData(363,364,2,2); //천왕 온수
        d.inputData(364,365,3,3); //온수 까치울
        d.inputData(365,366,2,2); //까치울 부천종합운동장
        d.inputData(366,367,2,2); //부천종합운동장 춘의
        d.inputData(367,368,2,2); //춘의 신중동
        d.inputData(368,369,2,2); //신중동 부천시청
        d.inputData(369,370,2,2); //부천시청 상동
        d.inputData(370,371,2,2); //상동 삼산체육관
        d.inputData(371,372,2,2); //삼산체육관 굴포천
        d.inputData(372,373,1,1); //굴포천 부평구청

        /////////////////////////////8호선////////////////////////////////
        d.inputData(374,375,2,2); //암사 천호
        d.inputData(375,376,2,2); //천호 강동구청
        d.inputData(376,377,2,2); //강동구청 몽촌토성
        d.inputData(377,378,2,2); //몽촌토성 잠실
        d.inputData(378,379,2,2); //잠실 석촌
        d.inputData(379,380,2,2); //석촌 송파
        d.inputData(380,381,2,2); //송파 가락시장
        d.inputData(381,382,1,1); //가락시장 문정
        d.inputData(382,383,2,2); //문정 장지
        d.inputData(383,384,2,2); //장지 복정
        d.inputData(384,385,3,3); //복정 산성
        d.inputData(385,386,3,3); //산성 남한산성입구
        d.inputData(386,387,2,2); //남한산성입구 단대오거리
        d.inputData(387,388,1,1); //단대오거리 신흥
        d.inputData(388,389,2,2); //신흥 수진
        d.inputData(389,390,1,1); //수진 모란

        ///////////성수지선///////////
        d.inputData(391, 392, 3, 3);//신설동 용두
        d.inputData(392, 393, 3, 3);//용두 신답
        d.inputData(393, 394, 3, 3);//신답 용답
        d.inputData(394, 395, 3, 3);//용답 성수

        ///////////신정지선///////////
        d.inputData(396, 397, 2, 2);//까치산 신정네거리
        d.inputData(397, 398, 2, 2);//신정네거리 양천구청
        d.inputData(398, 399, 2, 2);//양천구청 도림천
        d.inputData(399, 400, 2, 2);//도림천 신도림

        /////////////////////////////환승/////////////////////////////////
        d.inputData(14,324,1,1); //1호선 7호선 도봉산
        d.inputData(17,230,1,1); //1호선 4호선 창동
        d.inputData(21,288,1,1); //1호선 6호선 석계
        d.inputData(27,391,1,1); //1호선 성수지선 신설동
        d.inputData(28,296,1,1); //1호선 6호선 동묘앞
        d.inputData(29,221,1,1); //1호선 4호선 동대문
        d.inputData(31,165,1,1); //1호선 3호선 종로3가
        d.inputData(31,258,1,1); //1호선 5호선 종로3가
        d.inputData(33,125,1,1); //1호선 2호선 시청
        d.inputData(34,216,1,1); //1호선 4호선 서울역
        d.inputData(39,249,1,1); //1호선 5호선 신길
        d.inputData(41,135,1,1); //1호선 2호선 신도림
        d.inputData(41,400,1,1); //1호선 신정지선 신도림
        d.inputData(46,364,1,1); //1호선 7호선 온수
        d.inputData(63,360,1,1); //1호선 7호선 가산
        d.inputData(70,199,1,1); //1호선 4호선 금정

        d.inputData(100,209,1,1); //사당(2) 사당(4)
        d.inputData(103, 154, 1,1);//교대(2) 교대(3)
        d.inputData(110,378,1,1); //잠실(2) 잠실(8)
        d.inputData(114,341,1,1); //건대입구(2) 건대입구(7)
        d.inputData(115,395,1,1); //성수(2) 성수(성수)
        d.inputData(118,264,1,1); //왕십리(2) 왕십리(5)
        d.inputData(120,297,1,1); //신당(2) 신당(6)
        d.inputData(121,220,1,1); //동대문역사문화공원(2) 동대문역사문화공원(4)
        d.inputData(121,260,1,1); //동대문역사문화공원(2) 동대문역사문화공원(5)
        d.inputData(122,259,1,1); //을지로4가(2) 을지로4가(5)
        d.inputData(123, 164, 1,1);//을지로3가(2) 을지로3가(3)
        d.inputData(126,255,1,1); //충정로(2) 충정로(5)
        d.inputData(131,310,1,1); //합정(2) 합정(6)
        d.inputData(133,247,1,1); //영등포구청(2) 영등포구청(5)
        d.inputData(135,400,1,1); //신도림(2) 신도림(신정지)
        d.inputData(136,358,1,1); //대림(2) 대림(7)

        d.inputData(142, 276, 1,1); //오금(3) 오금(5)
        d.inputData(144, 381, 1,1); //가락시장(3) 가락시장(8)
        d.inputData(155, 348, 1,1); //고속터미널(3) 고속터미널(7)
        d.inputData(161, 299, 1,1);//약수(3) 약수(6)
        d.inputData(163, 219, 1,1);//충무로(3) 충무로(4)
        d.inputData(165, 258, 1,1);//종로3가(3) 종로3가(5)
        d.inputData(172, 319, 1,1);//불광(3)불광(6)
        d.inputData(173, 321, 1,1);//연신내(3)연신내(6)

        d.inputData(210, 350, 1,1);//이수(4)이수(7)
        d.inputData(214, 304, 1,1);//삼각지(4)삼각지(6)
        d.inputData(220, 260, 1,1);//동대문역사문화공원(4)동대문역사문화공원(5)
        d.inputData(231, 327, 1,1);//노원(4)노원(7)

        d.inputData(242,396,1,1); //까치산(5) 까치산(신정지)
        d.inputData(253,306,1,1); //공덕(5) 공덕(6)
        d.inputData(261,298,1,1); //청구(5) 청구(6)
        d.inputData(268,339,1,1); //군자(5) 군자(7)
        d.inputData(271,375,1,1); //천호(5) 천호(8)

        d.inputData(287, 331, 1,1);//태릉입구(6)태릉입구(7)

        d.DFS(returnStationNum(depart), returnStationNum(arrive)); //경로를 찾을 출발역, 도착역 설정


        //////////////////////////////DB검색
        boolean bResult = isCheckDatabase();    // DB가 있는지?
        Log.d("Working", "DataBase Check=" + bResult);
        if (!bResult) {  // DB가 없으면 복사
            copyDataBase();
        }

        myHelper = new DFSActivity.myDBHelper(this);
        congestionDB = myHelper.getReadableDatabase();
        //cursor = congestionDB.rawQuery("SELECT "+realTime+" FROM satUp where stationName=" + "'시청' AND line=1" , null);
        //rowSize = cursor.getCount();
        //Log.d(TAG,"rowSize => "+rowSize);
       // int i = 0;

//        while(cursor.moveToNext()){
//            // 배열 삽입
//            result = cursor.getString(0);
//            i++;
//        }
        structInfo[] infos = new structInfo[5];

        int j = queue.size();
        Intent intent2 = new Intent(this.getApplicationContext(),FiveRoadActivity.class);
        for(int z=0; z<j; z++){
            int sum = 0;
            structInfo imsi = queue.poll();
            imsi.congestions = new String[imsi.hoseons.length];
            //imsi.print();
            int x = imsi.hoseons.length;
            for(int y=0; y<x;y++) {
                Cursor cursor;
                cursor = congestionDB.rawQuery("SELECT " + returnTime(imsi.departTimes[y][0],imsi.departTimes[y][1]) + " FROM " +day+imsi.directions[y] + " where stationName='" + imsi.stations[y] + "' AND line=" + imsi.hoseons[y], null);
                rowSize = cursor.getCount();
                cursor.moveToNext();
                try {
                    imsi.congestions[y] = cursor.getString(0);
                }catch(Exception e){
                    imsi.congestions[y] = imsi.congestions[y-1];
                }
                sum += CongestMap.get(imsi.congestions[y]);
                cursor.close();
            }
            infos[z] = imsi;
            //intent2.putExtra("INFO"+z,imsi);
            //Log.d(TAG,"길이 => "+Congestions.length);
            //imsi.print();
            //intent2.putExtra("INFO"+z,imsi);
            int avg = sum/imsi.congestions.length;
            if(avg<1){
                Log.d(TAG,"매우여유"+avg);
            }else if(avg<3){
                Log.d(TAG,"여유"+avg);
            }else if(avg<5){
                Log.d(TAG,"보통"+avg);
            }else if(avg<9){
                Log.d(TAG,"혼잡"+avg);
            }else{
                Log.d(TAG,"매우 혼잡"+avg);
            }
        }
        //startActivity(intent);

        //Log.d(TAG,"rowSize => "+rowSize+" & result => "+result)
        congestionDB.close();
        intent2.putExtra("INFOS",infos);

        String formatTime = hour+"시 "+minute+"분";
        intent2.putExtra("FORMATTIME", formatTime);
        intent2.putExtra("DAY", day);
        intent2.putExtra("DEPART", depart);
        intent2.putExtra("ARRIVE", arrive);
        //startActivity(intent);
        startActivity(intent2);
    }

    public String returnTime(int hourInt, int minuteInt){
        String time = "운행 시간 아님";
        for(int i=0;i<index2Time.length;i++){
            int _hour = Integer.parseInt(index2Time[i].substring(4,6));
            //Log.d(TAG,"_hour => "+_hour+"\n hourInt => "+hourInt+"\n minuteInt => "+minuteInt);
            if(_hour == hourInt){
                if(minuteInt<=30){
                    time =  index2Time[i-1];
                }else{
                    time = index2Time[i];
                }
            }
        }
        //Log.d(TAG,"realTIme ==> " +time);
        return time;
    }

    public String returnStation(int s) {
        String station = "";
        if (s >= 1 && s <= st1Length) {
            station = station1[s];
        } else if (s <= st1Length + st2Length) {
            station = station2[s - st1Length];
        } else if (s <= st1Length + st2Length + st3Length) {
            station = station3[s - st1Length - st2Length];
        } else if (s <= st1Length + st2Length + st3Length + st4Length) {
            station = station4[s - st1Length - st2Length - st3Length];
        }else if (s <= st1Length + st2Length + st3Length + st4Length + st5Length) {
            station = station5[s - st1Length - st2Length - st3Length - st4Length];
        }else if (s <= st1Length + st2Length + st3Length + st4Length + st5Length + st6Length) {
            station = station6[s - st1Length - st2Length - st3Length - st4Length - st5Length];
        }else if (s <= st1Length + st2Length + st3Length + st4Length + st5Length + st6Length + st7Length) {
            station = station7[s - st1Length - st2Length - st3Length - st4Length - st5Length - st6Length];
        }else if (s <= st1Length + st2Length + st3Length + st4Length + st5Length + st6Length + st7Length + st8Length) {
            station = station8[s - st1Length - st2Length - st3Length - st4Length - st5Length - st6Length- st7Length];
        }else if (s <= st1Length + st2Length + st3Length + st4Length + st5Length + st6Length + st7Length + st8Length + stSSLength) {
            station = stationSS[s - st1Length - st2Length - st3Length - st4Length - st5Length - st6Length- st7Length - st8Length];
        }else if (s <= st1Length + st2Length + st3Length + st4Length + st5Length + st6Length + st7Length + st8Length + stSSLength + stSJLength) {
            station = stationSJ[s - st1Length - st2Length - st3Length - st4Length - st5Length - st6Length- st7Length - st8Length - stSSLength];
        }
        return station;
    }

    public String returnHoseon(int s) {
        String hoseon = "";
        if (s >= 1 && s <= st1Length) {
            hoseon = "1";
        } else if (s <= st1Length + st2Length) {
            hoseon = "2";
        } else if (s <= st1Length + st2Length + st3Length) {
            hoseon = "3";
        } else if (s <= st1Length + st2Length + st3Length + st4Length) {
            hoseon = "4";
        }else if (s <= st1Length + st2Length + st3Length + st4Length + st5Length) {
            hoseon = "5";
        }else if (s <= st1Length + st2Length + st3Length + st4Length + st5Length + st6Length) {
            hoseon = "6";
        }else if (s <= st1Length + st2Length + st3Length + st4Length + st5Length + st6Length + st7Length) {
            hoseon = "7";
        }else if (s <= st1Length + st2Length + st3Length + st4Length + st5Length + st6Length + st7Length + st8Length) {
            hoseon = "8";
        }else if (s <= st1Length + st2Length + st3Length + st4Length + st5Length + st6Length + st7Length + st8Length + stSSLength) {
            hoseon = "SS";
        }else if (s <= st1Length + st2Length + st3Length + st4Length + st5Length + st6Length + st7Length + st8Length + stSSLength + stSJLength) {
            hoseon = "SJ";
        }
        return hoseon;
    }

    public int returnStationNum(String s){ // 역이름 -> 역번호
        int stationNum = 0;
        for (int i = 1; i<=fullLength ; i++) {
            if (i<=st1Length && s.equals(station1[i])) {
                stationNum = i;
            }else if((st1Length+st2Length >= i && i>st1Length) && s.equals((station2[i-st1Length]))){
                stationNum = i;
            }else if(st1Length+st2Length+st3Length >= i && i>st1Length+st2Length && s.equals((station3[i-st1Length-st2Length]))) {
                stationNum = i;
            }else if(st1Length+st2Length+st3Length+st4Length >= i && i>st1Length+st2Length+st3Length && s.equals((station4[i-st1Length-st2Length-st3Length]))) {
                stationNum = i;
            }else if(st1Length+st2Length+st3Length+st4Length+st5Length >= i && i>st1Length+st2Length+st3Length+st4Length && s.equals((station5[i-st1Length-st2Length-st3Length-st4Length]))) {
                stationNum = i;
            }else if(st1Length+st2Length+st3Length+st4Length+st5Length+st6Length >= i && i>st1Length+st2Length+st3Length+st4Length+st5Length && s.equals((station6[i-st1Length-st2Length-st3Length-st4Length-st5Length]))) {
                stationNum = i;
            }else if(st1Length+st2Length+st3Length+st4Length+st5Length+st6Length+st7Length >= i && i>st1Length+st2Length+st3Length+st4Length+st5Length+st6Length && s.equals((station7[i-st1Length-st2Length-st3Length-st4Length-st5Length-st6Length]))) {
                stationNum = i;
            }else if(st1Length+st2Length+st3Length+st4Length+st5Length+st6Length+st7Length+st8Length >= i && i>st1Length+st2Length+st3Length+st4Length+st5Length+st6Length+st7Length && s.equals((station8[i-st1Length-st2Length-st3Length-st4Length-st5Length-st6Length-st7Length]))) {
                stationNum = i;
            }else if(st1Length+st2Length+st3Length+st4Length+st5Length+st6Length+st7Length+st8Length+stSSLength >= i && i>st1Length+st2Length+st3Length+st4Length+st5Length+st6Length+st7Length+st8Length && s.equals((stationSS[i-st1Length-st2Length-st3Length-st4Length-st5Length-st6Length-st7Length-st8Length]))) {
                stationNum = i;
            }else if(fullLength > i && i>st1Length+st2Length+st3Length+st4Length+st5Length+st6Length+st7Length+st8Length+stSSLength && s.equals((stationSJ[i-st1Length-st2Length-st3Length-st4Length-st5Length-st6Length-st7Length-st8Length-stSSLength]))) {
                stationNum = i;
            }
        }
        //Log.d(TAG,"역번호==> "+stationNum);
        return stationNum;
    }


    public int cntHws(int[] hwanseungs){
        int hwanseung=0;
        for(int i=0; i<hwanseungs.length; i++){
            if(hwanseungs[i]==1){
                hwanseung += 1;
            }
        }
        return hwanseung;
    }

    class DFSAlgorithm {
        private int maps[][] = new int[fullLength][fullLength];      //DFS 인접행렬
        private boolean visit[] = new boolean[fullLength];   //방문했나 안했나 판단할 변수

        Stack<Integer> stack = new Stack();


        public DFSAlgorithm() {
            //클래스 생성자
            //스택을 초기화하고
            //table 및  visit 변수를 할당 한다.

            for (int i = 0; i < fullLength; i++) {
                for (int j = 0; j < fullLength; j++) {
                    maps[i][j] = 0; //maps[0~21][0~21]
                }
            }

            for (int i = 0; i < fullLength; i++) {
                visit[i] = false; // visit[0~21]
            }
        }

        public void inputData(int i, int j, int c1, int c2) {
            //데이터를 집어넣는 함수
            //i,j를 넣으면 인접행렬에 값을 넣어준다.
            //무방향 그래프이므로 대칭해서 넣어준다. -> (탱 수정)방향 그래프를 위해 c1, c2 인자를 추가함
            maps[i][j] = c1;
            maps[j][i] = c2;
        }

        public void DFS(int v, int goal) {
            //DFS 구현 부분
            if(limit > 60){
                limit -= 1;
                return;
            }

            if (!stack.isEmpty()){
                if (returnStation(stack.peek()) == returnStation(v)) {
                    if (cntHws(hwanseungs) == 3) {
                        limit -= 1;
                        return;
                    } else {
                        hwanseungs[limit] = 1;
                    }
                } else {
                    hwanseungs[limit] = 0;
                }
            }

            visit[v] = true; //출발지 방문했다고 설정
            stack.push(v); //스택에 값을 넣어준다.

            if (v == goal) { //목표노드에 왔으면

                Queue<String> stations = new LinkedList<String>();
                Queue<String> hoseons = new LinkedList<String>();
                Queue<String> directions = new LinkedList<String>();
                Queue<Integer> departHours = new LinkedList<Integer>();
                Queue<Integer> departMinutes = new LinkedList<Integer>();
                Queue<String> hwanseungStation = new LinkedList<>();

                int _hourInt = 0;
                int _minuteInt = 0;

                //structInfo 객체 생성
                structInfo info = new structInfo();

                //// 스택값 출력
                int count = stack.size(); //스택의 크기를 받을 변수
                int num = 0;
                for (int i = 0; i < count; i++) {
                    if (num >= 1) {
                        if (returnStation(stack.elementAt(i)).equals(returnStation(stack.elementAt(i - 1)))) {
                            if(stack.size()>2 && num>1){
                                if(returnStation(stack.elementAt(i)).equals(returnStation(stack.elementAt(i - 1))) && returnStation(stack.elementAt(i-1)).equals(returnStation(stack.elementAt(i - 2)))) {
                                    stack.pop(); //DFS에서 빠져나오기
                                    hwanseungs[limit] = 0;
                                    limit -= 1;
                                    return;
                                }
                            }
                            info.totalTime += maps[stack.elementAt(i - 1)][stack.elementAt(i)];
                            info.hwanseungs += 1;
                            hwanseungStation.add(returnStation(stack.elementAt(i)));
                            //info.directions.push("환승");
                            continue;
                        }else{
                            stations.add(returnStation(stack.elementAt(i)));
                            hoseons.add(returnHoseon(stack.elementAt(i)));
                            info.totalStation += 1;
                            if (stack.elementAt(i) > stack.elementAt(i - 1)) {
                                directions.add("down");
                            }else{
                                directions.add("up");
                            }
                            info.totalTime += maps[stack.elementAt(i - 1)][stack.elementAt(i)];
                            _minuteInt += maps[stack.elementAt(i - 1)][stack.elementAt(i)];
                            if(_minuteInt>=60){
                                _minuteInt = 0;
                                _hourInt += 1;
                            }
                            departHours.add(_hourInt);
                            departMinutes.add(_minuteInt);
                        }
                    }else{
                        stations.add(returnStation(stack.elementAt(i)));
                        _hourInt = hourInt;
                        _minuteInt = minuteInt;
                        departHours.add(_hourInt);
                        departMinutes.add(_minuteInt);
                    }
                    num++;
                }

                info.stations = new String[stations.size()];
                int j = stations.size();
                for(int i=0;i<j;i++){
                    info.stations[i] = stations.poll();
                }
                info.hoseons = new String[hoseons.size()];
                j = hoseons.size();
                for(int i=0;i<j;i++){
                    info.hoseons[i] = hoseons.poll();
                }
                info.directions = new String[directions.size()];
                j = directions.size();
                for(int i=0;i<j;i++){
                    info.directions[i] = directions.poll();
                }
                info.departTimes = new int[departHours.size()][2];
                j = departHours.size();
                for(int i=0;i<j;i++){
                    info.hwaseungStations[i] = hwanseungStation.poll();
                }
                info.hwaseungStations = new String[hwanseungStation.size()];
                j = hwanseungStation.size();
                for(int i=0;i<j;i++){
                    info.departTimes[i][0] = departHours.poll();
                    info.departTimes[i][1] = departMinutes.poll();
                }

                //System.out.println("\n ^총 역 개수: " + totalStation+" & 총 소요 시간: "+totalTime+" & 총 환승 횟수: "+hwanseung);
                //System.out.println("\n ^limit : " + limit + " & cntHws : "+cntHws(hwanseungs));
                //// 스택값 출력
                //info.print();

                /*if(infos.length==0){
                    min = info.totalTime;
                    infos[0] = info;
                }else{
                    for(int i=0; i<infos.length; i++){
                        if(info.totalTime < infos[i].totalTime){
                            structInfo newInfo = new structInfo();
                            newInfo = info;
                            structInfo temp = new structInfo();
                            temp = infos[i];
                            for(int j=i; j<infos.length-1;i++){
                                infos[i] = newInfo;

                            }
                        }
                    }
                }*/
                if(info.totalTime <min){
                    if(info.totalTime*2<min){
                        queue.clear();
                    }
                    min = info.totalTime;
                }
                if(!(info.totalTime>min*2)) {
                    if (queue.size() < topN) {
                        queue.add(info);
                        //System.out.println("\n여기"+info.totalTime);
                    } else {
                        queue.add(info);
                        queue.remove();
                        //System.out.println("\n저기"+info.totalTime);
                    }
                }

                stack.pop(); //DFS에서 빠져나올땐  pop을 합니다.
                hwanseungs[limit] = 0;
                limit -= 1;
                return;
            }

            for (int i = 0; i < fullLength; i++) {
                if (maps[v][i] != 0 && !visit[i]) {
                    //노드가 이어져있고 방문을 하지 않았으면
                    limit += 1;
                    DFS(i, goal);
                    visit[i] = false; //DFS에서 빠져나오면 해당노드는 방문하지 않은 것으로 한다.
                }
            }

            stack.pop(); //DFS에서 빠져나올땐 pop을 합니다.
            hwanseungs[limit] = 0;
            limit -= 1;
        }
    }

    // DB관련 코드
    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context){
            super(context, "congestion.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }

    public boolean isCheckDatabase() {
        String filePath = PACKAGE_DIRECTORY + "/databases/" + DATABASE_NAME;
        File file = new File(filePath);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    public void copyDataBase(){
        Log.d("Working", "copyDatabase");
        AssetManager manager = mContext.getAssets();
        String folderPath = PACKAGE_DIRECTORY + "/databases";
        String filePath = PACKAGE_DIRECTORY + "/databases/" + DATABASE_NAME;
        File folder = new File(folderPath);
        File file = new File(filePath);
        FileOutputStream fileOut = null;
        BufferedOutputStream bufferOut = null;
        try {
            InputStream inputStr = manager.open(DATABASE_NAME);
            BufferedInputStream bufferStr = new BufferedInputStream(inputStr);
            if (folder.exists()) {

            }else{
                folder.mkdirs();
            }
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }

            fileOut = new FileOutputStream(file);
            bufferOut = new BufferedOutputStream(fileOut);
            int read = -1;
            byte[] buffer = new byte[1024];
            while ((read = bufferStr.read(buffer, 0, 1024)) != -1) {
                bufferOut.write(buffer, 0, read);
            }

            bufferOut.flush();
            bufferOut.close();
            fileOut.close();
            bufferStr.close();
            inputStr.close();
        } catch (IOException e) {
            Log.e("Error : ", e.getMessage());
        }
    }

}
class structInfo implements Serializable {
    String[] stations;
    String[] hoseons;
    String[] directions;
    String[] congestions;
    String[] hwaseungStations;
    int[][] departTimes;
    int totalTime = 0;
    int totalStation = 0;
    int hwanseungs = 0;

    public void print() {
        System.out.println();
        for(int i=0;i<stations.length;i++){
            System.out.print(stations[i]+" ");
        }
        System.out.println();
        for(int i=0;i<hoseons.length;i++){
            System.out.print(hoseons[i]+" ");
        }
        System.out.println();
        for(int i=0;i<directions.length;i++){
            System.out.print(directions[i]+" ");
        }
        System.out.println();
        for(int i=0;i<congestions.length;i++){
            System.out.print(congestions[i]+" ");
        }
        System.out.println();
        for(int i=0;i<departTimes.length;i++){
            System.out.print(departTimes[i][0]+":"+departTimes[i][1]+" ");
        }
        System.out.println("\n^소요시간: "+totalTime+" & 총 역 개수: "+totalStation+" & 환승 횟수: "+hwanseungs);
    }
}

class totalTimeComparator implements Comparator<structInfo> {
    @Override
    public int compare(structInfo x, structInfo y) {
        if(x.totalTime<y.totalTime){
            return 1;
        }else if(x.totalTime>y.totalTime){
            return -1;
        }else{
            return 0;
        }
    }
}
