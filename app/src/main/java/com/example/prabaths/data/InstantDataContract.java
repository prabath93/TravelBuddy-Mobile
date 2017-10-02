package com.example.prabaths.Data;

import android.provider.BaseColumns;

/**
 * Created by prabath s on 5/2/2016.
 */
public final class InstantDataContract {
    public InstantDataContract() {}

    /**
     * Inner class that defines the content of instant data table
     */
    public static abstract class InstantDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "instantData";


        public static final String COLUMN_NAME_UNIT_PRICE = "unit_price";
        public static final String COLUMN_NAME_USER_NAME = "user_name";
        public static final String COLUMN_NAME_REGNO = "reg_no";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_FINAL_ODOMETER_READING="odometer_reading";

    }
}
