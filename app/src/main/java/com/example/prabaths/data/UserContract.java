package com.example.prabaths.Data;

import android.provider.BaseColumns;

/**
 * Created by prabath s on 5/2/2016.
 */
public final class UserContract {
    public UserContract() {}

    /**
     * Inner class that defines the content of user table
     */
    public static abstract class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";


        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_USER_NAME = "user_name";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_IMAGE_URI = "image_uri";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_NIC_NO = "nic";
        public static final String COLUMN_NAME_TELEPHONE_NO = "tel_no";
    }
}
