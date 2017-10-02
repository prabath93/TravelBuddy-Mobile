package com.example.prabaths.Data;

import android.provider.BaseColumns;

/**
 * Created by prabath s on 5/2/2016.
 */
public final class VehicleContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public VehicleContract() {}

    /**
     * Inner class thad defines the content of vehicle table
     */
    public static abstract class VehicleEntry implements BaseColumns {
        public static final String TABLE_NAME = "vehicle";


        public static final String COLUMN_NAME_REGISTRATION_NO = "reg_no";
        public static final String COLUMN_NAME_MODEL = "model";
        public static final String COLUMN_NAME_BRAND = "brand";
        public static final String COLUMN_NAME_IMAGE_URI = "image_uri";
        public static final String COLUMN_NAME_LAST_MILEAGE = "last_mileage";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_VEHICLE_ID = "id";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_TYPE="type";

    }
}
