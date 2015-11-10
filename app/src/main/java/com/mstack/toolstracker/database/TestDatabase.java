package com.mstack.toolstracker.database;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * Created by Pitipong on 23/10/2558.
 */

@Database(name = TestDatabase.NAME, version = TestDatabase.VERSION)
public class TestDatabase {

    public static final String NAME = "Test";
    public static final int VERSION = 1;
}
