package br.com.hitg.sqlitetransactions.sqlite;


import android.content.Context;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class SQLiteDatabaseHelper {

    private static SQLiteDatabaseHelper instance;

    private SQLiteDatabaseConnection conn = null;

    private static Map<String, SQLiteDatabaseConnection> transactions;

    public static synchronized SQLiteDatabaseHelper getInstance() {
        if (instance == null) {
            instance = new SQLiteDatabaseHelper();
            transactions = new HashMap<>();
        }
        return instance;
    }

    public SQLiteDatabaseConnection getDefaultConnection() {
        return conn;
    }

    public void setConnection(SQLiteDatabaseConnection conn) {
        this.conn = conn;
    }

    public void beginTransaction(Context context, String transactionName) {
        if (!transactions.containsKey(transactionName)) {
            transactions.put(transactionName,
                    new SQLiteDatabaseConnection(context, transactionName));
        } else {
            showToastMessage(context, "Transaction Already Started!");
        }
    }

    private void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public SQLiteDatabaseConnection getConnectionByTransaction(String transactionName) {
        if (transactions.containsKey(transactionName)) {
            return transactions.get(transactionName);
        }
        throw new RuntimeException("Transaction Not Started");
    }
}