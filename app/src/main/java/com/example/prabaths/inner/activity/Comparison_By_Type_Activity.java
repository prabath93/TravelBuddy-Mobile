package com.example.prabaths.inner.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.prabaths.Data.DAO.ExpenseDAO;
import com.example.prabaths.Fragments.Home_Fragment;
import com.example.prabaths.Server.GetFromDB;
import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.VO.Expense;

import java.util.ArrayList;

/**
 * Created by prabath s on 6/9/2016.
 */
public class Comparison_By_Type_Activity extends AppCompatActivity{

    private TextView totalCostOfTypeTxt;
    private TextView fuelCostOfTypeTxt;
    private TextView otherCostOfTypeTxt;
    private TextView economyOfTypeTxt;
    private TextView totalCostOfTypeTxt1;
    private TextView fuelCostOfTypeTxt1;
    private TextView otherCostOfTypeTxt1;
    private TextView economyOfTypeTxt1;
    private TextView comparisonTypeTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table2_layout2);
        totalCostOfTypeTxt=(TextView) findViewById(R.id.totalCostOfTypeTxt);
        fuelCostOfTypeTxt=(TextView) findViewById(R.id.fuelCostOfTypeTxt);
        otherCostOfTypeTxt=(TextView) findViewById(R.id.otherCostOfTypeTxt);
        economyOfTypeTxt=(TextView) findViewById(R.id.economyOfTypeTxt);
        totalCostOfTypeTxt1=(TextView) findViewById(R.id.totalCostOfTypeTxt1);
        fuelCostOfTypeTxt1=(TextView) findViewById(R.id.fuelCostOfTypeTxt1);
        otherCostOfTypeTxt1=(TextView) findViewById(R.id.otherCostOfTypeTxt1);
        economyOfTypeTxt1=(TextView) findViewById(R.id.economyOfTypeTxt1);
        comparisonTypeTxt=(TextView) findViewById(R.id.comparisonTypeTxt);
        comparisonTypeTxt.setText(Home_Fragment.type_of_vechicle);
        GetFromDB getFromDB=new GetFromDB(this);
        getFromDB.getAverageCostOfAType(Home_Fragment.type_of_vechicle,totalCostOfTypeTxt1);
        getFromDB.getAverageFuelCostOfAType(Home_Fragment.type_of_vechicle, fuelCostOfTypeTxt1);
        getFromDB.getAverageOtherCostOfAType(Home_Fragment.type_of_vechicle, otherCostOfTypeTxt1);
        getFromDB.getAverageEconomyOfAType(Home_Fragment.type_of_vechicle,economyOfTypeTxt1);

        ExpenseDAO expenseDAO=new ExpenseDAO(this);
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

        Button backBtn=(Button) findViewById(R.id.backBtn2);
        backBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        backButtonPressed();
                    }
                }
        );
    }

    /**
     * When the back button is pressed this method is called to finish
     * the current activity and roll back to the previous activity
     */
    private void backButtonPressed(){
        finish();
    }
}
