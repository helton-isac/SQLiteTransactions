package br.com.hitg.sqlitetransactions.sqlite;

public class SQLiteDatabaseCommand {

    private SQLiteDatabaseConnection databaseConnection;
    private boolean closed = false;
    private String query;

    SQLiteDatabaseCommand(SQLiteDatabaseConnection databaseConnection, String query) {
        this.databaseConnection = databaseConnection;
    }

    public void closeCommand() {
        if (this.closed) {
            return;
        }

        this.query = "";
        this.closed = true;
    }

    public int executeStatement() {
        boolean isInsideTransaction = this.databaseConnection.isInsideTransaction();

        this.databaseConnection.getDatabase().execSQL(this.query);

        return 1;
    }

}
