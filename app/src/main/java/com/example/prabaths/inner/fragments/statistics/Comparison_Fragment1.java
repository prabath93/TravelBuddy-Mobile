package com.example.prabaths.inner.fragments.statistics;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.inner.activity.Comparison_By_Model_Activity;
import com.example.prabaths.inner.activity.Comparison_By_Type_Activity;

/**
 * Created by prabath s on 6/8/2016.
 */
public class Comparison_Fragment1 extends Fragment {

    Button comparisonByModelBtn;
    Button comparisonByTypeBtn;
    FragmentManager fragmentManager;
    View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.comparison_fragment1,container,false);
        comparisonByModelBtn=(Button) v.findViewById(R.id.comparisonByModelBtn1);
        comparisonByTypeBtn=(Button) v.findViewById(R.id.comparisonByTypeBtn1);
        fragmentManager=this.getFragmentManager();
        View va=((ViewGroup)v.getParent());

        comparisonByModelBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        Intent intent = new Intent(getActivity(), Comparison_By_Model_Activity.class);
                        startActivity(intent);

                        //openModelFragment();
                    }
                }
        );


        comparisonByTypeBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), Comparison_By_Type_Activity.class);
                        startActivity(intent);
                    }
                }
        );
        return v;
    }



}
