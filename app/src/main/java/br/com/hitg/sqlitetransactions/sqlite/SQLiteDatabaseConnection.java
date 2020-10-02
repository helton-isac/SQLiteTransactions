package br.com.hitg.sqlitetransactions.sqlite;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import br.com.hitg.sqlitetransactions.helper.Helper;

public class SQLiteDatabaseConnection {

    private SQLiteInstanceManager sqlDB;
    private SQLiteDatabase database;
    private boolean isInsideTransaction = false;
    private String transactionName;

    public SQLiteDatabaseConnection(Context context) {
        initializeInstance(context);
    }

    public SQLiteDatabaseConnection(Context context, String transactionName) {
        initializeInstance(context);
        this.transactionName = transactionName;
        this.isInsideTransaction = true;
        try {
            this.database.beginTransactionNonExclusive();
        } catch (Exception e) {
            Helper.showToastMessage(e.getMessage());
        }
    }

    private void initializeInstance(Context context) {
        this.sqlDB = new SQLiteInstanceManager(context);
        this.sqlDB.setWriteAheadLoggingEnabled(true);
        this.database = this.sqlDB.getWritableDatabase();
    }

    public boolean commitTransaction() {
        try {
            if (database != null && database.isOpen() && database.inTransaction()) {
                database.setTransactionSuccessful();
                database.endTransaction();
                return true;
            }
        } finally {
            this.isInsideTransaction = false;
        }
        return false;
    }

    public boolean rollbackTransaction() {
        try {
            if (database != null && database.isOpen() && database.inTransaction()) {
                database.endTransaction();
                return true;
            }
        } finally {
            this.isInsideTransaction = false;
        }
        return false;
    }

    public boolean isInsideTransaction() {
        return this.isInsideTransaction;
    }

    public SQLiteDatabase getDatabase() {
        if (this.database == null || !this.database.isOpen()) {
            this.database = sqlDB.getWritableDatabase();
            this.database.enableWriteAheadLogging();
        }
        return this.database;
    }

    public String getTransactionName() {
        return this.transactionName;
    }

    public void execSQL(String sql, Object[] bindArgs) throws SQLException {
        try {
            if (this.isInsideTransaction() && !this.database.inTransaction()) {
                database.beginTransactionNonExclusive();
            }
            database.beginTransactionNonExclusive();
            database.execSQL(sql, bindArgs);
            database.setTransactionSuccessful();
        } catch (Exception e) {
            Helper.showToastMessage(e.getMessage());
        } finally {
            if (database != null && database.inTransaction()) {
                database.endTransaction();
            }
        }
    }
}
