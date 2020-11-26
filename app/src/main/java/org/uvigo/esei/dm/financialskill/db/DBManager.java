package org.uvigo.esei.dm.financialskill.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "financialdb";
    private static int DATABASE_VERSION = 1;

    public static final String EXPENSE_TABLE_NAME = "expense";
    public static final String EXPENSE_COLUMN_ID = "_id";
    public static final String EXPENSE_COLUMN_CONCEPT = "concept";
    public static final String EXPENSE_COLUMN_QUANTITY = "quantity";
    public static final String EXPENSE_COLUMN_CATEGORY = "category";
    public static final String EXPENSE_COLUMN_DESCRIPTION = "description";

    public DBManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            db.execSQL("CREATE TABLE IF NOT EXISTS " + EXPENSE_TABLE_NAME +"(" +
                    EXPENSE_COLUMN_ID +" INTEGER PRIMARY KEY," +
                    EXPENSE_COLUMN_CONCEPT + " TEXT NOT NULL," +
                    EXPENSE_COLUMN_QUANTITY + " REAL NOT NULL," +
                    EXPENSE_COLUMN_CATEGORY + " TEXT NOT NULL," +
                    EXPENSE_COLUMN_DESCRIPTION + " TEXT NOT NULL" + //NOT NULL, are we sure?
                    ")");
            db.setTransactionSuccessful();
        } catch (SQLException exception){
            Log.e(DBManager.class.getName(), "onCreate", exception);
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
