package com.mstack.toolstracker.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by pitipong on 2/11/2558.
 */
@Table(databaseName = TestDatabase.NAME)
public class Test extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public String lable;

    @Column
    public String value;
}
