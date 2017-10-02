package com.example.prabaths.inner.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

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
public class Fuel_Expense_Graph_Activity extends AppCompatActivity {

    LineGraphSeries<DataPoint> series;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuel_expense_graph2);

        GraphView graphView=(GraphView) findViewById(R.id.fuelExpenseGraph);
        series=new LineGraphSeries<>();
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(3);
        ExpenseDAO expenseDAO=new ExpenseDAO(this);
        ArrayList<Expense> expenses=expenseDAO.getFuelExpensesOfAVehicle(Home_Fragment.regNo);

        for(int i=0;i<expenses.size();i++){

            if(expenses.get(i).getType()==1) {
                series.appendData(new DataPoint(expenses.get(i).getDate(), expenses.get(i).getCost()), true, expenses.size());
            }
        }
        graphView.addSeries(series);

        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
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

        Button backBtn=(Button) findViewById(R.id.backBtn3);
        backBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        backButtonClicked();
                    }
                }
        );

    }




    /**
     * When the back button is pressed this method is called to finish
     * the current activity and roll back to the previous activity
     */
    private void backButtonClicked(){
        finish();
    }
}
