package com.example.prabaths.inner.fragments.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.prabaths.Data.DAO.ExpenseDAO;
import com.example.prabaths.Data.SQLiteHelper;
import com.example.prabaths.Fragments.Home_Fragment;
import com.example.prabaths.TravelBuddy.MainActivity;
import com.example.prabaths.TravelBuddy.R;
import com.example.prabaths.VO.Expense;

import java.util.ArrayList;

/**
 * Created by prabath s on 5/2/2016.
 */
public class Log_Fragment extends Fragment {

    private MainActivity mainActivity;
    static Context context;
    private static TableLayout tableLayout;
    private static TableRow tableHeaderRow;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.log, container, false);
        tableLayout=(TableLayout) v.findViewById(R.id.logs_table);
        context=getContext();
        tableHeaderRow=(TableRow) v.findViewById(R.id.logs_table_header);



        return v;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    /**
     * This method is called to update the log when a new fuel expense is added or
     * current vehicle changed
     */
    public static void updateLog(){
        ExpenseDAO expenseDAO=new ExpenseDAO(context);
        ArrayList<Expense> expenses=expenseDAO.getAllExpensesOfAVehicle(Home_Fragment.regNo);
        //Toast.makeText(context,"length of array:"+expenses.size(),Toast.LENGTH_LONG).show();
        for (Expense expense:expenses) {
            TableRow tr = new TableRow(context);
            TextView lDateVal = new TextView(context);
            lDateVal.setText(SQLiteHelper.getInstance(context).dateToString(expense.getDate(),context));
            tr.addView(lDateVal);

            TextView expenseTypeTxt = new TextView(context);
            if(expense.getType()==1){expenseTypeTxt.setText("Fuel");}
            else if(expense.getType()==2){expenseTypeTxt.setText("Service");}
            else{expenseTypeTxt.setText("Repair");}
            tr.addView(expenseTypeTxt);

            TextView amountTxt = new TextView(context);
            amountTxt.setText(String.valueOf(expense.getCost()));
            tr.addView(amountTxt);

            tableLayout.addView(tr);


        }
    }

    /**
     * This method clears all logs
     */
    public static void clearLog(){
        tableLayout.removeAllViews();
        tableLayout.addView(tableHeaderRow);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
