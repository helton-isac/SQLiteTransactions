package br.com.hitg.sqlitetransactions.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteInstanceManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sqliteDB.db";
    private static final int DATABASE_VERSION = 1;
    // Table Names
    private static final String TABLE_TEST = "test";
    private static final String KEY_TEST_ID = "id";
    private static final String KEY_TEST_KEY = "column_key";
    private static final String KEY_TEST_VALUE = "value";
    private static SQLiteInstanceManager instance;

    public SQLiteInstanceManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized SQLiteInstanceManager getInstance(Context context) {
        if (instance == null) {
            instance = new SQLiteInstanceManager(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TEST_TABLE = "CREATE TABLE " + TABLE_TEST +
                "(" +
                KEY_TEST_ID + " INTEGER, " +
                KEY_TEST_KEY + " TEXT ," +
                KEY_TEST_VALUE + " TEXT" +
                ")";

        sqLiteDatabase.execSQL(CREATE_TEST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
