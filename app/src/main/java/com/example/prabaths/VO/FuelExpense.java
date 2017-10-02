package com.example.prabaths.VO;

import java.util.Date;

/**
 * Created by prabath s on 5/10/2016.
 */
public class FuelExpense extends Expense {

    public FuelExpense(String carRegNo, double cost, Date date, int id, String location, String notes, double odometerReading, int partialTank, int type, double unitPrice, String userName, double volume,double economy) {
        super(carRegNo, cost, date, id, location, notes, odometerReading, partialTank, type, unitPrice, userName, volume);
        super.setEconomy(economy);
    }

}
