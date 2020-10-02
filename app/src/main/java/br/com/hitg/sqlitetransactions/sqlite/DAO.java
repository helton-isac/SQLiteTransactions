package br.com.hitg.sqlitetransactions.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.util.Date;

public class DAO {
    private static final String TABLE_DEFAULT = "DEFAULT_TABLE";
    private static final String TABLE_TRANSACTION_A = "TRANSACTION_A_TABLE";
    private static final String TABLE_TRANSACTION_B = "TRANSACTION_B_TABLE";
    private static final String COLUMN_CODE = "code";
    private static final String COLUMN_VALUE = "value";

    private static final String TRANSACTION_A = "TRANSACTION_A";

    public static String getValueInConnection(SQLiteDatabaseConnection connection) {
        String result = "";
        result += getValueFromTable(connection, TABLE_DEFAULT) + "\n";
        result += getValueFromTable(connection, TABLE_TRANSACTION_A) + "\n";
        result += getValueFromTable(connection, TABLE_TRANSACTION_B) + "\n";
        return result;
    }

    private static String chooseTableByTransaction(SQLiteDatabaseConnection connection) {
        String table = TABLE_DEFAULT;
        if (connection.isInsideTransaction()) {
            if (TRANSACTION_A.equals(connection.getTransactionName())) {
                table = TABLE_TRANSACTION_A;
            } else {
                table = TABLE_TRANSACTION_B;
            }
        }
        return table;
    }

    private static String getValueFromTable(SQLiteDatabaseConnection connection, String table) {
        String selectQuery =
                "select " + COLUMN_VALUE +
                        " from " + table +
                        " where " + COLUMN_CODE + " = '" + table + "'";

        final SQLiteDatabase database = connection.getDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        String result = "NULL";
        if (cursor.moveToFirst()) {
            result = cursor.getString(0);
        }
        cursor.close();
        return result;
    }

    public static void updateValueOnTransaction(SQLiteDatabaseConnection connection) {

        String table = chooseTableByTransaction(connection);

        String value = String.format("%s: %s", table, getTimeStamp());
        String updateValueStatement =
                "UPDATE " + table +
                        " SET " + COLUMN_VALUE + " = ? " +
                        " WHERE " + COLUMN_CODE + " = ?";

        final SQLiteDatabase database = connection.getDatabase();
        database.execSQL(updateValueStatement, new Object[]{value, table});
    }

    private static String getTimeStamp() {
        return DateFormat.getTimeInstance().format(new Date());
    }

    public static void resetTables() {

        final SQLiteDatabase database =
                SQLiteDatabaseHelper.getInstance().getDefaultConnection().getDatabase();

        database.execSQL("DELETE FROM " + TABLE_DEFAULT);
        database.execSQL("DELETE FROM " + TABLE_TRANSACTION_A);
        database.execSQL("DELETE FROM " + TABLE_TRANSACTION_B);

        database.execSQL("INSERT INTO " + TABLE_DEFAULT + " VALUES (?,?)",
                new Object[]{TABLE_DEFAULT, "EMPTY"});
        database.execSQL("INSERT INTO " + TABLE_TRANSACTION_A + " VALUES (?,?)",
                new Object[]{TABLE_TRANSACTION_A, "EMPTY"});
        database.execSQL("INSERT INTO " + TABLE_TRANSACTION_B + " VALUES (?,?)",
                new Object[]{TABLE_TRANSACTION_B, "EMPTY"});
    }

}
