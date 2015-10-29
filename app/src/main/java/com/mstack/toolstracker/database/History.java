package com.mstack.toolstracker.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by layer on 23/10/2558.
 */

@Table(databaseName = HistoryDatabase.NAME)
public class History extends BaseModel {

    @Column
    @PrimaryKey
    public int order;

    @Column
    public String cServiceCode;

    @Column
    public String cRegister_Time;

    @Column
    public String cTAT_All;

    @Column
    public String cCondition3;

}
