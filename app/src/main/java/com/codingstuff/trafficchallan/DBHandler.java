package com.codingstuff.trafficchallan;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    public DBHandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table TrafficDetails(Mobile varchar(20),Vechile varchar(20),Location varchar(20),Type varchar(10))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertRecord(String mob, String veh, String selectedCity, String type) {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("insert into TrafficDetails values(?,?,?,?)",new String[]{mob,veh,selectedCity,type});
        db.close();
    }

    public String displayRecord() {
        String Data="";
        SQLiteDatabase db=getReadableDatabase();
        Cursor cr=db.rawQuery("select * from TrafficDetails",null);
        while(cr.moveToNext())
        {
            String m=cr.getString(0);
            String v=cr.getString(1);
            String l=cr.getString(2);
            String t=cr.getString(3);
            Data+="Mobile Number : "+m+"\nVechile Number : "+v+"\nLocation : "+l+"\n"+"Type : "+t;
        }
        return Data;
    }

    public void challanPaid(String vec) {
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("delete from TrafficDetails where Vechile=?",new String[]{vec});
        db.close();
    }
}
