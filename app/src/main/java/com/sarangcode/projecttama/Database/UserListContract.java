package com.sarangcode.projecttama.Database;

import android.provider.BaseColumns;

public class UserListContract {
    public static final class UserListEntry implements BaseColumns
    {
        public static final String TABLE_NAME="User_info";
        public static final String COLUMN_USER_NAME="User name";
        public static final String COLUMN_PASSWORD="Password";
        public static final String COLUMN_DESTINATION="Destination";


    }
}
