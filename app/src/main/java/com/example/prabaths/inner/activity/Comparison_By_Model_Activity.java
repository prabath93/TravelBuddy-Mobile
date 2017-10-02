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
public class Comparison_By_Model_Activity extends AppCompatActivity {


    private TextView totalCostOfModelTxt;
    private TextView fuelCostOfModelTxt;
    private TextView otherCostOfModelTxt;
    private TextView economyOfModelTxt;
    private TextView totalCostOfModelTxt1;
    private TextView fuelCostOfModelTxt1;
    private TextView otherCostOfModelTxt1;
    private TextView economyOfModelTxt1;
    private TextView comparisonModelTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table1_layout2);
        Button backBtn=(Button) findViewById(R.id.backBtn1);
        backBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        backButtonPressed();
                    }
                }
        );

        totalCostOfModelTxt=(TextView) findViewById(R.id.totalCostOfModelTxt);
        fuelCostOfModelTxt=(TextView) findViewById(R.id.fuelCostOfModelTxt);
        otherCostOfModelTxt=(TextView) findViewById(R.id.otherCostOfModelTxt);
        economyOfModelTxt=(TextView) findViewById(R.id.economyOfModelTxt);
        totalCostOfModelTxt1=(TextView) findViewById(R.id.totalCostOfModelTxt1);
        fuelCostOfModelTxt1=(TextView) findViewById(R.id.fuelCostOfModelTxt1);
        otherCostOfModelTxt1=(TextView) findViewById(R.id.otherCostOfModelTxt1);
        economyOfModelTxt1=(TextView) findViewById(R.id.economyOfModelTxt1);
        comparisonModelTxt=(TextView) findViewById(R.id.comparisonModelTxt);
        comparisonModelTxt.setText(Home_Fragment.model);
        GetFromDB getFromDB=new GetFromDB(this);
        getFromDB.getAverageCostOfAModel(Home_Fragment.model, totalCostOfModelTxt1);
        getFromDB.getAverageFuelCostOfAModel(Home_Fragment.model, fuelCostOfModelTxt1);
        getFromDB.getAverageOtherCostOfAModel(Home_Fragment.model, otherCostOfModelTxt1);
        getFromDB.getAverageEconomyOfAModel(Home_Fragment.model, economyOfModelTxt1);

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
                economyOfModelTxt.setText(String.valueOf(economy));}
        }
        if(totalCost!=0){totalCostOfModelTxt.setText(String.valueOf(totalCost));}
        if(fuelCost!=0){fuelCostOfModelTxt.setText(String.valueOf(fuelCost));}
        if(otherCost!=0){otherCostOfModelTxt.setText(String.valueOf(otherCost));}
        if(economy!=0){economyOfModelTxt.setText(String.valueOf(economy));}
    }

    /**
     * When the back button is pressed this method is called to finish
     * the current activity and roll back to the previous activity
     */

    public void backButtonPressed(){
        finish();
    }
}
