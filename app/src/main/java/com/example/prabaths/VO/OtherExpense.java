package com.example.prabaths.VO;

import com.example.prabaths.Data.DAO.ExpenseDAO;

import java.util.Date;

/**
 * Created by prabath s on 5/25/2016.
 */
public class OtherExpense extends Expense {

    public OtherExpense(String carRegNo, double cost, Date date, int id, String location, String notes, double odometerReading, int partialTank, int type, double unitPrice, String userID, double volume) {
        super(carRegNo, cost, date, id, location, notes, odometerReading, partialTank, type, unitPrice, userID, volume);
    }
}
