package br.com.hitg.sqlitetransactions.sqlite;


public class SQLiteDatabaseHelper {

    private static SQLiteDatabaseHelper instance;

    private SQLiteDatabaseConnection conn = null;

    public static synchronized SQLiteDatabaseHelper getInstance() {
        if (instance == null) {
            instance = new SQLiteDatabaseHelper();
        }
        return instance;
    }

    public void setConnection(SQLiteDatabaseConnection conn) {
        this.conn = conn;
    }
}
