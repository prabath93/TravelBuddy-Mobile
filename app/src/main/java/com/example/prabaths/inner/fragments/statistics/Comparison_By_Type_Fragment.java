package com.example.prabaths.inner.fragments.statistics;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.prabaths.Data.DAO.ExpenseDAO;
import com.example.prabaths.Fragments.Home_Fragment;
import com.example.prabaths.Server.GetFromDB;
import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.VO.Expense;

import java.util.ArrayList;

/**
 * Created by prabath s on 6/8/2016.
 */
public class Comparison_By_Type_Fragment extends Fragment {

    private TextView totalCostOfTypeTxt;
    private TextView fuelCostOfTypeTxt;
    private TextView otherCostOfTypeTxt;
    private TextView economyOfTypeTxt;
    private TextView totalCostOfTypeTxt1;
    private TextView fuelCostOfTypeTxt1;
    private TextView otherCostOfTypeTxt1;
    private TextView economyOfTypeTxt1;
    private TextView comparisonTypeTxt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.table2_layout,container,false);
        totalCostOfTypeTxt=(TextView) v.findViewById(R.id.totalCostOfTypeTxt);
        fuelCostOfTypeTxt=(TextView) v.findViewById(R.id.fuelCostOfTypeTxt);
        otherCostOfTypeTxt=(TextView) v.findViewById(R.id.otherCostOfTypeTxt);
        economyOfTypeTxt=(TextView) v.findViewById(R.id.economyOfTypeTxt);
        totalCostOfTypeTxt1=(TextView) v.findViewById(R.id.totalCostOfTypeTxt1);
        fuelCostOfTypeTxt1=(TextView) v.findViewById(R.id.fuelCostOfTypeTxt1);
        otherCostOfTypeTxt1=(TextView) v.findViewById(R.id.otherCostOfTypeTxt1);
        economyOfTypeTxt1=(TextView) v.findViewById(R.id.economyOfTypeTxt1);
        comparisonTypeTxt=(TextView) v.findViewById(R.id.comparisonTypeTxt);
        comparisonTypeTxt.setText(Home_Fragment.type_of_vechicle);
        GetFromDB getFromDB=new GetFromDB(getContext());
        getFromDB.getAverageCostOfAType(Home_Fragment.type_of_vechicle,totalCostOfTypeTxt1);
        getFromDB.getAverageFuelCostOfAType(Home_Fragment.type_of_vechicle, fuelCostOfTypeTxt1);
        getFromDB.getAverageOtherCostOfAType(Home_Fragment.type_of_vechicle, otherCostOfTypeTxt1);
        getFromDB.getAverageEconomyOfAType(Home_Fragment.type_of_vechicle,economyOfTypeTxt1);

        ExpenseDAO expenseDAO=new ExpenseDAO(getContext());
        ArrayList<Expense> expenses=expenseDAO.getAllExpensesOfAVehicle(Home_Fragment.regNo);
        double totalCost=0;
        double fuelCost=0;
        double otherCost=0;
        double economy=0;
        int economyCount=0;
        for (Expense expense:expenses) {
            if(expense.getType()==1){
                totalCost=totalCost+expense.getCost();
                fuelCost=fuelCost+expense.getCost();
                economy=economy+expense.getEconomy();
                if(expense.getEconomy()!=0){
                    economyCount=economyCount+1;}

            }
            else{
                totalCost=totalCost+1;
                otherCost=otherCost+expense.getCost();
            }


        }
        if(economyCount!=0){economy=economy/economyCount;
            if(economy!=0){
                economy=Math.round(economy*100.0)/100.0;
                economyOfTypeTxt.setText(String.valueOf(economy));}
        }
        if(totalCost!=0){totalCostOfTypeTxt.setText(String.valueOf(totalCost));}
        if(fuelCost!=0){fuelCostOfTypeTxt.setText(String.valueOf(fuelCost));}
        if(otherCost!=0){otherCostOfTypeTxt.setText(String.valueOf(otherCost));}
        if(economy!=0){economyOfTypeTxt.setText(String.valueOf(economy));}
        return v;
    }
}
