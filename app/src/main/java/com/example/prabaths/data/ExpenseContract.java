package com.example.prabaths.Data;

import android.provider.BaseColumns;

/**
 * Created by prabath s on 5/2/2016.
 */
public final class ExpenseContract {
    public ExpenseContract() {}



    /**
     * Inner class that defines the content of expenses table
     */
    public static abstract class ExpenseEntry implements BaseColumns {
        public static final String TABLE_NAME = "expenses";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_COST = "total_cost";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_VOLUME = "volume";
        public static final String COLUMN_NAME_ODOMETER_READING = "odometer_reading";
        public static final String COLUMN_NAME_PARTIAL_TANK = "partial_tank";
        public static final String COLUMN_NAME_UNIT_PRICE = "unit_price";
        public static final String COLUMN_NAME_LOCATION = "location";
        public static final String COLUMN_NAME_NOTES = "notes";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_REG_NO = "car_reg_no";
        public static final String COLUMN_NAME_ECONOMY="economy";

    }
}
