package com.example.pr_idi.mydatabaseexample;

/**
 * MySQLiteHelper
 * Created by pr_idi on 10/11/16.
 */
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_FILMS = "films";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_COUNTRY = "country";
    public static final String COLUMN_YEAR_RELEASE = "year_release";
    public static final String COLUMN_DIRECTOR = "director";
    public static final String COLUMN_PROTAGONIST = "protagonist";
    public static final String COLUMN_CRITICS_RATE = "critics_rate";

    private static final String DATABASE_NAME = "films.db";
    private static final int DATABASE_VERSION = 8;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table " + TABLE_FILMS + "( "
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_COUNTRY + " text not null, "
            + COLUMN_YEAR_RELEASE + " integer not null, "
            + COLUMN_DIRECTOR + " text not null, "
            + COLUMN_PROTAGONIST + " text not null, "
            + COLUMN_CRITICS_RATE + " integer"
            + ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
        database.execSQL("INSERT INTO "+TABLE_FILMS+" " +
                "("+COLUMN_TITLE+", "+COLUMN_COUNTRY+"," +
                ""+COLUMN_YEAR_RELEASE+","+COLUMN_DIRECTOR+","
                + COLUMN_PROTAGONIST+","+COLUMN_CRITICS_RATE+") " +
                "values('El ataque de los tomates asesinos'," +
                "'USA'," +
                "1986," +
                "'John De Bello'," +
                "'George Clooney'," +
                "9);");
        database.execSQL("INSERT INTO "+TABLE_FILMS+" " +
                "("+COLUMN_TITLE+", "+COLUMN_COUNTRY+"," +
                ""+COLUMN_YEAR_RELEASE+","+COLUMN_DIRECTOR+","
                + COLUMN_PROTAGONIST+","+COLUMN_CRITICS_RATE+") " +

                "values('Matrix'," +
                "'USA'," +
                "1999," +
                "'The Wachowski Brothers'," +
                "'Keanu Reeves'," +
                "7);");
        database.execSQL("INSERT INTO "+TABLE_FILMS+" " +
                "("+COLUMN_TITLE+", "+COLUMN_COUNTRY+"," +
                ""+COLUMN_YEAR_RELEASE+","+COLUMN_DIRECTOR+","
                + COLUMN_PROTAGONIST+","+COLUMN_CRITICS_RATE+") " +

                "values('Cadena perpetua'," +
                "'USA'," +
                "1994," +
                "'Frank Darabont'," +
                "'Tim Robbins'," +
                "8);");
        database.execSQL("INSERT INTO "+TABLE_FILMS+" " +
                "("+COLUMN_TITLE+", "+COLUMN_COUNTRY+"," +
                ""+COLUMN_YEAR_RELEASE+","+COLUMN_DIRECTOR+","
                + COLUMN_PROTAGONIST+","+COLUMN_CRITICS_RATE+") " +

                "values('Eternal sunshine of the spotless mind'," +
                "'USA'," +
                "2003," +
                "'Michel Gondry'," +
                "'Jim Carrey'," +
                "8);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FILMS);
        onCreate(db);
    }



}