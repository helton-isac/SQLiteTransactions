package br.com.hitg.sqlitetransactions.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteInstanceManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sqliteDB.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_DEFAULT = "DEFAULT_TABLE";
    private static final String TABLE_TRANSACTION_A = "TRANSACTION_A_TABLE";
    private static final String TABLE_TRANSACTION_B = "TRANSACTION_B_TABLE";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_VALUE = "value";

    private static SQLiteInstanceManager instance;

    public SQLiteInstanceManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    public static synchronized SQLiteInstanceManager getInstance(Context context) {
//        if (instance == null) {
//            instance = new SQLiteInstanceManager(context.getApplicationContext());
//        }
//        return instance;
//    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_DEFAULT +
                "(" +
                COLUMN_CODE + " TEXT ," +
                COLUMN_VALUE + " TEXT" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TRANSACTION_A +
                "(" +
                COLUMN_CODE + " TEXT ," +
                COLUMN_VALUE + " TEXT" +
                ")"
        );

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TRANSACTION_B +
                "(" +
                COLUMN_CODE + " TEXT ," +
                COLUMN_VALUE + " TEXT" +
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
