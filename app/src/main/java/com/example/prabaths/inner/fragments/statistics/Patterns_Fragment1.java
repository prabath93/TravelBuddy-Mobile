package com.example.prabaths.inner.fragments.statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.inner.activity.Economy_Graph_Activity;
import com.example.prabaths.inner.activity.Fuel_Expense_Graph_Activity;
import com.example.prabaths.inner.activity.Other_Expense_Graph_Activity;

/**
 * Created by prabath s on 6/10/2016.
 */
public class Patterns_Fragment1 extends Fragment {

    Button fuelBtn;
    Button otherBtn;
    Button economyBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.pattern_fragment1,container,false);
        fuelBtn=(Button) v.findViewById(R.id.fuelGraphBtn);
        otherBtn=(Button) v.findViewById(R.id.otherGraphBtn);
        economyBtn=(Button) v.findViewById(R.id.economyBtn);


        fuelBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v1) {
                        Intent intent=new Intent(getActivity(),Fuel_Expense_Graph_Activity.class);
                        startActivity(intent);

                        //openModelFragment();
                    }
                }
        );


        otherBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), Other_Expense_Graph_Activity.class);
                        startActivity(intent);
                    }
                }
        );

        economyBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), Economy_Graph_Activity.class);
                        startActivity(intent);
                    }
                }
        );

        return v;
    }
}
