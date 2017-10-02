package com.example.prabaths.inner.fragments.statistics;

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
import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.inner.fragments.home.View_Vehicle_For_Home_Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by prabath s on 6/8/2016.
 */
public class Comparison_Fragment extends Fragment implements ViewPager.OnPageChangeListener,TabHost.OnTabChangeListener{
    ViewPager viewPager;
    TabHost tabHost;
    private MyPagerAdapter myPagerAdapter;
    private MainActivity mainActivity;
    String userName;
    public static String regNo;
    public static String model;
    public static String type_of_vechicle;
    int i=0;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.tabs_viewpager_layout,container,false);
        i++;
        //userName=this.getArguments().getString("u");
        initViewPager();
        initTabHost();
        //Bundle bdl=getArguments();
        regNo="";

        //Toast.makeText(getContext(), userName, Toast.LENGTH_LONG).show();

        return v;
    }


    /**
     * This method creates fake content for tabs
     */
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


    /**
     * This method initiates tab host
     */
    private void initTabHost() {

        tabHost= (TabHost) v.findViewById(R.id.tabHost);
        tabHost.setup();
        String[] tabNames={"By Model","By Type"};
        for(int k=0;k<tabNames.length;k++){
            TabHost.TabSpec tabSpec;
            tabSpec=tabHost.newTabSpec(tabNames[k]);
            tabSpec.setIndicator(tabNames[k]);
            tabSpec.setContent(new fakeContent(getActivity()));
            tabHost.addTab(tabSpec);
        }

        tabHost.setOnTabChangedListener(this);
    }


    /**
     * This method initiates view pager
     */
    private void initViewPager() {

        List<Fragment> list=new ArrayList<Fragment>();
        View_Vehicle_For_Home_Fragment vehicle_details_fragment=new View_Vehicle_For_Home_Fragment();
        //vehicle_details_fragment.createSpinner(userName);
        Bundle bundle=new Bundle();
        bundle.putString("u", userName);
        View_Vehicle_For_Home_Fragment view_vehicle_for_home_fragment=new View_Vehicle_For_Home_Fragment();
        view_vehicle_for_home_fragment.setArguments(bundle);
        list.add(new Comparison_By_Model_Fragment());
        list.add(new Comparison_By_Type_Fragment());


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
