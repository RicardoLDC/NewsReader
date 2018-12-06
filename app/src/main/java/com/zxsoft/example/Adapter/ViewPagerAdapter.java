package com.zxsoft.example.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class  ViewPagerAdapter extends FragmentPagerAdapter {
    private String[] tabTitle={"头条","社会","国内","国际","娱乐","体育","军事","科技","财经","时尚"};
    private List<Fragment> fragmentList;
    public ViewPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }
    @Override
    public Fragment getItem(int position) {

        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}
