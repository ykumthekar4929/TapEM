package edu.uta.se1.team6.tapem.Helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    /*
    Event DTO
        name;
        address;
        area;
        status;
        createdById;
        createdByName;
        createdOn;
        catererUserID;
        catererFirstName;
        catererLastName;
        catererEmail;
        catererMobile;
        city;
        zipcode;
        lat;
        lon;
        imageURL;
    */


    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
