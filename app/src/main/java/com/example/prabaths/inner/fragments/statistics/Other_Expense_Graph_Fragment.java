package com.example.prabaths.inner.fragments.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.prabaths.Data.DAO.ExpenseDAO;
import com.example.prabaths.Fragments.Home_Fragment;
import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.VO.Expense;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

/**
 * Created by prabath s on 6/10/2016.
 */
public class Other_Expense_Graph_Fragment extends Fragment {

    LineGraphSeries<DataPoint> series;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.other_expense_graph, container, false);

        double x;
        double y;
        x=5.0;
        GraphView graphView=(GraphView) v.findViewById(R.id.otherExpenseGraph);
        series=new LineGraphSeries<>();
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(3);
        ExpenseDAO expenseDAO=new ExpenseDAO(getContext());
        ArrayList<Expense> expenses=expenseDAO.getOtherExpensesOfAVehicle(Home_Fragment.regNo);


        for(int i=0;i<expenses.size();i++){
            x=expenses.get(i).getCost();
            y=2*x;
            if(expenses.get(i).getType()!=1) {
                series.appendData(new DataPoint(expenses.get(i).getDate(), expenses.get(i).getCost()), true, expenses.size());
            }
        }
        graphView.addSeries(series);

        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graphView.getGridLabelRenderer().setNumHorizontalLabels(3);
        graphView.getGridLabelRenderer().setVerticalAxisTitle("Cost");
        graphView.getGridLabelRenderer().setHorizontalAxisTitle("Date");
        // only 4 because of the space

        //graphView.getGridLabelRenderer().
// set manual x bounds to have nice steps

        graphView.getViewport().setXAxisBoundsManual(true);
        if(expenses.size()>0){
            graphView.getViewport().setMaxX(expenses.get(expenses.size()-1).getDate().getTime()+24*60*60*1000);
            graphView.getViewport().setMinX(expenses.get(0).getDate().getTime()-24*60*60*1000);
        }

        graphView.getViewport().setScalable(true);
        graphView.getViewport().setScrollable(true);


        return v;
    }
}
