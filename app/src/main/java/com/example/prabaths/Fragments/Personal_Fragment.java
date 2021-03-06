package com.example.prabaths.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;

import com.example.prabaths.Adapter.MyPagerAdapter;
import com.example.prabaths.TravelBuddy.MainActivity;
import com.example.prabaths.inner.fragments.personal.Edit_Profile_Fragment;
import com.example.prabaths.inner.fragments.personal.View_Profile_Fragment;
import com.example.prabaths.TravelBuddy.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prabath s on 5/2/2016.
 */
public class Personal_Fragment extends Fragment implements ViewPager.OnPageChangeListener,TabHost.OnTabChangeListener{
    ViewPager viewPager;
    TabHost tabHost;
    private MyPagerAdapter myPagerAdapter;
    private MainActivity mainActivity;
    int i=0;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.tabs_viewpager_layout,container,false);
        i++;
        initViewPager();
        initTabHost();

        return v;
    }



    public class fakeContent implements TabHost.TabContentFactory{

        Context context;
        public fakeContent(Context mcontext) {
            context=mcontext;
        }

        @Override
        public View createTabContent(String tag) {
            View fakeView= new View(context);
            fakeView.setMinimumHeight(0);
            fakeView.setMinimumWidth(0);
            return fakeView;
        }
    }


    private void initTabHost() {

        tabHost= (TabHost) v.findViewById(R.id.tabHost);
        tabHost.setup();
        String[] tabNames={"View","Edit"};
        for(int k=0;k<tabNames.length;k++){
            TabHost.TabSpec tabSpec;
            tabSpec=tabHost.newTabSpec(tabNames[k]);
            tabSpec.setIndicator(tabNames[k]);
            tabSpec.setContent(new fakeContent(getActivity()));
            tabHost.addTab(tabSpec);
        }

        tabHost.setOnTabChangedListener(this);
    }


    private void initViewPager() {

        List<Fragment> list=new ArrayList<Fragment>();
        //list.add(mainActivity.getView_profile_fragment());
       // list.add(mainActivity.getEdit_profile_fragment());
        list.add(new View_Profile_Fragment());
        list.add(new Edit_Profile_Fragment());

        this.myPagerAdapter = new MyPagerAdapter(getChildFragmentManager(),list);
        this.viewPager=(ViewPager) v.findViewById(R.id.view_pager);
        this.viewPager.setAdapter(this.myPagerAdapter);
        this.viewPager.setOnPageChangeListener(this);


    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int selectedItem) {
        tabHost.setCurrentTab(selectedItem);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onTabChanged(String tabId) {

        int selectedPage=tabHost.getCurrentTab();
        viewPager.setCurrentItem(selectedPage);
        HorizontalScrollView horizontalScrollView= (HorizontalScrollView) v.findViewById(R.id.h_scroll_view);
        View tabView=tabHost.getCurrentTabView();
        int scrollPos=tabView.getLeft()-(horizontalScrollView.getWidth()-tabView.getWidth())/2;
        horizontalScrollView.smoothScrollTo(scrollPos, 0);
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}

