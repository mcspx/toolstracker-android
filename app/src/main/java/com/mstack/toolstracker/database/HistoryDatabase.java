package com.mstack.toolstracker.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Pitipong on 23/10/2558.
 */

@Database(name = HistoryDatabase.NAME, version = HistoryDatabase.VERSION)
public class HistoryDatabase {

    public static final String NAME = "History";
    public static final int VERSION = 1;
}
