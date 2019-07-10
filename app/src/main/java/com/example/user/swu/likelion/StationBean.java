package com.example.user.swu.likelion;


import java.io.Serializable;

public class StationBean implements Serializable {

    //서울전철 아이콘
    private int imgSeoul;
    //지하철 이름
    private String txtStationName;
    //호선 표시 이미지
    private int imgStationNum;

    private int imgStationNum2;

    private int imgStationNum3;

    public int getImgSeoul() {
        return imgSeoul;
    }

    public void setImgSeoul(int imgSeoul) {
        this.imgSeoul = imgSeoul;
    }

    public String getTxtStationName() {
        return txtStationName;
    }

    public void setTxtStationName(String txtStationName) {
        this.txtStationName = txtStationName;
    }

    public int getImgStationNum() {
        return imgStationNum;
    }

    public void setImgStationNum(int imgStationNum) {
        this.imgStationNum = imgStationNum;
    }

    public int getImgStationNum2() {
        return imgStationNum2;
    }

    public void setImgStationNum2(int imgStationNum2) {
        this.imgStationNum2 = imgStationNum2;
    }

    public int getImgStationNum3() {
        return imgStationNum3;
    }

    public void setImgStationNum3(int imgStationNum3) {
        this.imgStationNum3 = imgStationNum3;
    }
}
