package com.example.prabaths.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by prabath s on 3/31/2016.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> listFragment;
    public MyPagerAdapter(FragmentManager fm, List<Fragment> listFragment) {

        super(fm);
        this.listFragment=listFragment;
    }

    @Override
    public Fragment getItem(int i) {
        return listFragment.get(i);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }
}
