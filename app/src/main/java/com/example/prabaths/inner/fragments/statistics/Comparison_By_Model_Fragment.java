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
public class Comparison_By_Model_Fragment extends Fragment {



    private TextView totalCostOfModelTxt;
    private TextView fuelCostOfModelTxt;
    private TextView otherCostOfModelTxt;
    private TextView economyOfModelTxt;
    private TextView totalCostOfModelTxt1;
    private TextView fuelCostOfModelTxt1;
    private TextView otherCostOfModelTxt1;
    private TextView economyOfModelTxt1;
    private TextView comparisonModelTxt;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.table1_layout,container,false);
        totalCostOfModelTxt=(TextView) v.findViewById(R.id.totalCostOfModelTxt);
        fuelCostOfModelTxt=(TextView) v.findViewById(R.id.fuelCostOfModelTxt);
        otherCostOfModelTxt=(TextView) v.findViewById(R.id.otherCostOfModelTxt);
        economyOfModelTxt=(TextView) v.findViewById(R.id.economyOfModelTxt);
        totalCostOfModelTxt1=(TextView) v.findViewById(R.id.totalCostOfModelTxt1);
        fuelCostOfModelTxt1=(TextView) v.findViewById(R.id.fuelCostOfModelTxt1);
        otherCostOfModelTxt1=(TextView) v.findViewById(R.id.otherCostOfModelTxt1);
        economyOfModelTxt1=(TextView) v.findViewById(R.id.economyOfModelTxt1);
        comparisonModelTxt=(TextView) v.findViewById(R.id.comparisonModelTxt);
        comparisonModelTxt.setText(Home_Fragment.model);
        GetFromDB getFromDB=new GetFromDB(getContext());
        getFromDB.getAverageCostOfAModel(Home_Fragment.model, totalCostOfModelTxt1);
        getFromDB.getAverageFuelCostOfAModel(Home_Fragment.model, fuelCostOfModelTxt1);
        getFromDB.getAverageOtherCostOfAModel(Home_Fragment.model, otherCostOfModelTxt1);
        getFromDB.getAverageEconomyOfAModel(Home_Fragment.model, economyOfModelTxt1);

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
                economyOfModelTxt.setText(String.valueOf(economy));}
        }
        if(totalCost!=0){totalCostOfModelTxt.setText(String.valueOf(totalCost));}
        if(fuelCost!=0){fuelCostOfModelTxt.setText(String.valueOf(fuelCost));}
        if(otherCost!=0){otherCostOfModelTxt.setText(String.valueOf(otherCost));}
        if(economy!=0){economyOfModelTxt.setText(String.valueOf(economy));}


        return v;
    }
    public String[] validate(String totalCost,String fuelCost,String otherCost){
        String[] array={totalCost,fuelCost,otherCost};
        for(int i=0;i<3;i++){
            try{
                if(array[i]!=null){
                double a=Double.parseDouble(array[i]);
                if(a==0){array[i]="Not Available";}}
                else{
                    array[i]="Not Available";
                }
            }
            catch (NumberFormatException e){
                array[i]="Not available";
            }
        }
        return array;
    }
}



