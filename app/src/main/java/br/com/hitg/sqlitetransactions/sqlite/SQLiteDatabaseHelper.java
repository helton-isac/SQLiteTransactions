package br.com.hitg.sqlitetransactions.sqlite;


import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import br.com.hitg.sqlitetransactions.helper.Helper;

public class SQLiteDatabaseHelper {

    private static SQLiteDatabaseHelper instance;
    private static Map<String, SQLiteDatabaseConnection> transactions;
    private SQLiteDatabaseConnection defaultConnection = null;

    public static synchronized SQLiteDatabaseHelper getInstance() {
        if (instance == null) {
            instance = new SQLiteDatabaseHelper();
            transactions = new HashMap<>();
        }
        return instance;
    }

    public SQLiteDatabaseConnection getDefaultConnection() {
        return defaultConnection;
    }

    public void setConnection(SQLiteDatabaseConnection conn) {
        this.defaultConnection = conn;
    }

    public void beginTransaction(Context context, String transactionName) {
        if (!transactions.containsKey(transactionName)) {
            transactions.put(transactionName,
                    new SQLiteDatabaseConnection(context, transactionName));
        } else {
            Helper.showToastMessage(context, "Transaction Already Started!");
        }
    }

    public void commitTransaction(Context context, String transactionName) {
        if (transactions.containsKey(transactionName)) {
            final SQLiteDatabaseConnection connection = transactions.get(transactionName);
            connection.commitTransaction();
            transactions.remove(transactionName);
        } else {
            Helper.showToastMessage(context, "Transaction Not Started!");
        }
    }

    public SQLiteDatabaseConnection getConnectionByTransaction(String transactionName) {
        if (transactions.containsKey(transactionName)) {
            return transactions.get(transactionName);
        }
        return null;
    }

    public void rollbackTransaction(Context context, String transactionName) {
        if (transactions.containsKey(transactionName)) {
            final SQLiteDatabaseConnection connection = transactions.get(transactionName);
            connection.rollbackTransaction();
            transactions.remove(transactionName);
        } else {
            Helper.showToastMessage(context, "Transaction Not Started!");
        }
    }
}
