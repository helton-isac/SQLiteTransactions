package br.com.hitg.sqlitetransactions.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteDatabaseConnection {

    private SQLiteInstanceManager sqlDB;
    private SQLiteDatabase database;
    private boolean isInsideTransaction = false;

    public SQLiteDatabaseConnection(Context context) {
        initializeInstance(context);
    }

    private void initializeInstance(Context context) {
        this.sqlDB = SQLiteInstanceManager.getInstance(context);
        this.database = sqlDB.getWritableDatabase();
        this.database.enableWriteAheadLogging();
    }

    public void beginTransaction() {
        database.beginTransactionNonExclusive();
        this.isInsideTransaction = true;
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
        synchronized (this.database) {
            if (this.database == null || !this.database.isOpen()) {
                this.database = sqlDB.getWritableDatabase();
                this.database.enableWriteAheadLogging();
            }
        }
        return this.database;
    }
}
