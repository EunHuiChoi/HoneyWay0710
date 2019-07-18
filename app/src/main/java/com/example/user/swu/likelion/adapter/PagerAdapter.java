package com.example.user.swu.likelion.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.user.swu.likelion.CongestedFragment;
import com.example.user.swu.likelion.MinimumTransferFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {

    //탭의 갯수를 저장하고 있는 멤버변수
    private int mNumOfTab;

    //생성자
    public PagerAdapter(FragmentManager fm, int numOfTab) {
        super(fm);
        mNumOfTab = numOfTab;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:

            case 1:
                CongestedFragment tab2 = new CongestedFragment();
                return tab2;
            case 2:
                MinimumTransferFragment tab3 = new MinimumTransferFragment();
                return tab3;
        }

        return null;
    }

    @Override
    public int getCount() {
        return mNumOfTab;
    }
}
