package org.uvigo.esei.dm.financialskill.db;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.uvigo.esei.dm.financialskill.core.Expense;

public class ExpenseFacade {private DBManager dbManager;

    public ExpenseFacade(DBManager dbManager){
        this.dbManager = dbManager;
    }

    public static Expense readExpense(Cursor cursor){
        Expense toret = new Expense();
        toret.setId(cursor.getInt(cursor.getColumnIndex(DBManager.EXPENSE_COLUMN_ID)));
        toret.setConcept(cursor.getString(cursor.getColumnIndex(DBManager.EXPENSE_COLUMN_CONCEPT)));
        toret.setQuantity(cursor.getDouble(cursor.getColumnIndex(DBManager.EXPENSE_COLUMN_QUANTITY)));
        toret.setCategory(cursor.getString(cursor.getColumnIndex(DBManager.EXPENSE_COLUMN_CATEGORY)));
        return toret;
    }

    public Cursor getExpense() {
        Cursor toret = null;
        toret = dbManager.getReadableDatabase().rawQuery("SELECT * FROM "+DBManager.EXPENSE_TABLE_NAME, null);
        return toret;
    }

    public double getTotalBalance() {
        double total = -0.69;
        Cursor cursor = dbManager.getReadableDatabase().rawQuery("SELECT SUM(" + DBManager.EXPENSE_COLUMN_QUANTITY + ") as Total FROM " + DBManager.EXPENSE_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));// get final total
        }
        return total;
    }

    public double getBalanceByCategory(String category) {
        double total = -0.69;
        Cursor cursor = dbManager.getReadableDatabase().rawQuery("SELECT SUM("
                + DBManager.EXPENSE_COLUMN_QUANTITY +
                ") as Total FROM " + DBManager.EXPENSE_TABLE_NAME +
                " WHERE " + DBManager.EXPENSE_COLUMN_CATEGORY + "=?", new String[]{category});
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));// get final total
        }
        return total;
    }

    public void addExpense(Expense expense) {
        SQLiteDatabase writableDatabase = dbManager.getWritableDatabase();
        try {
            writableDatabase.beginTransaction();
            writableDatabase.execSQL(
                    "INSERT INTO " + DBManager.EXPENSE_TABLE_NAME + "(" +
                            DBManager.EXPENSE_COLUMN_CONCEPT + "," +
                            DBManager.EXPENSE_COLUMN_QUANTITY + "," +
                            DBManager.EXPENSE_COLUMN_CATEGORY +
                            ") VALUES (?, ?, ?)",
                    new Object[]{expense.getConcept(), expense.getQuantity(), expense.getCategory()});
            writableDatabase.setTransactionSuccessful();
        } catch(SQLException exception){
            Log.e(ExpenseFacade.class.getName(), "addTransaction", exception);
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public void removeExpense(Expense expense) {
        SQLiteDatabase writableDatabase = dbManager.getWritableDatabase();
        try{
            writableDatabase.beginTransaction();
            writableDatabase.execSQL(
                    "DELETE FROM " + DBManager.EXPENSE_TABLE_NAME +
                            " WHERE " + DBManager.EXPENSE_COLUMN_ID +" = ?",
                    new Object[]{expense.getId()});
            writableDatabase.setTransactionSuccessful();
        } catch(SQLException exception) {
            Log.e(ExpenseFacade.class.getName(), "removeTransaction", exception);
        } finally {
            writableDatabase.endTransaction();
        }
    }

    public void updateExpense(Expense expense) {
        SQLiteDatabase writableDatabase = dbManager.getWritableDatabase();
        try{
            writableDatabase.beginTransaction();
            writableDatabase.execSQL(
                    "UPDATE " + DBManager.EXPENSE_TABLE_NAME
                            + " SET "
                            + DBManager.EXPENSE_COLUMN_CONCEPT + "=?, "
                            + DBManager.EXPENSE_COLUMN_QUANTITY + "=?, "
                            + DBManager.EXPENSE_COLUMN_CATEGORY + "=? "
                            + " WHERE " + DBManager.EXPENSE_COLUMN_ID +"=?",
                    new Object[]{expense.getConcept(), expense.getQuantity(), expense.getCategory()});

            writableDatabase.setTransactionSuccessful();
        }catch(SQLException exception){
            Log.e(ExpenseFacade.class.getName(), "updateTransaction", exception);
        }finally {
            writableDatabase.endTransaction();
        }
    }

    public Expense getExpenseById(Integer id) {
        Cursor cursor = dbManager.getReadableDatabase().rawQuery(
                "SELECT * FROM " + DBManager.EXPENSE_TABLE_NAME
                        + " WHERE " + DBManager.EXPENSE_COLUMN_ID + " = ?",
                        new String[]{id + ""});
        cursor.moveToFirst();
        return readExpense(cursor);
    }
}
