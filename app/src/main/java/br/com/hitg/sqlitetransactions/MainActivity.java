package br.com.hitg.sqlitetransactions;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Date;

import br.com.hitg.sqlitetransactions.sqlite.SQLiteDatabaseConnection;
import br.com.hitg.sqlitetransactions.sqlite.SQLiteDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabaseHelper helper;
    private TextView tvDefaultTransactionValue;
    private Button btDefaultTranUpdate;
    private Button btRefreshScreen;
    private TextView tvTranAValue;
    private Button btTranAUpdate;
    private Button btTranAStart;
    private Button btTranACommit;
    private Button btTranARollback;
    private TextView tvTranBValue;
    private Button btTranBUpdate;
    private Button btTranBStart;
    private Button btTranBCommit;
    private Button btTranBRollback;
    private TextView tvStatusValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = SQLiteDatabaseHelper.getInstance();
        helper.setConnection(new SQLiteDatabaseConnection(this));

        resetTestTable();
        initializeControls();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshScreen();
    }

    private void resetTestTable() {

        String deleteTestTableStatement = "DELETE FROM test";
        String insertInitialValueStatement = "INSERT INTO test VALUES ('data','EMPTY')";

        final SQLiteDatabase database = helper.getConnection().getDatabase();
        database.execSQL(deleteTestTableStatement);
        database.execSQL(insertInitialValueStatement);
    }

    private void initializeControls() {
        tvDefaultTransactionValue = findViewById(R.id.tvDefaultTransactionValue);
        tvTranAValue = findViewById(R.id.tvTranAValue);

        tvTranBValue = findViewById(R.id.tvTranBValue);
        tvStatusValue = findViewById(R.id.tvStatusValue);

        btDefaultTranUpdate = findViewById(R.id.btDefaultTranUpdate);
        btDefaultTranUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValueOnDefaultTransaction();
            }
        });

        btRefreshScreen = findViewById(R.id.btRefreshScreen);
        btRefreshScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshScreen();
            }
        });

        btTranAStart = findViewById(R.id.btTranAStart);
        btTranAStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTransactionA();
            }
        });

        btTranAUpdate = findViewById(R.id.btTranAUpdate);
        btTranACommit = findViewById(R.id.btTranACommit);
        btTranARollback = findViewById(R.id.btTranARollback);

        btTranBUpdate = findViewById(R.id.btTranBUpdate);
        btTranBStart = findViewById(R.id.btTranBStart);
        btTranBCommit = findViewById(R.id.btTranBCommit);
        btTranBRollback = findViewById(R.id.btTranBRollback);
    }

    private void startTransactionA() {
        helper.beginTransactionA();
    }

    private void updateValueOnDefaultTransaction() {

        String transactionName = "DEFAULT";
        String value = String.format("%s: %s", transactionName, getTimeStamp());
        String updateValueStatement = "UPDATE test SET value = ? WHERE column_key = 'data'";

        final SQLiteDatabase database = helper.getConnection().getDatabase();
        database.execSQL(updateValueStatement, new Object[]{value});
        refreshScreen();
    }

    private void refreshScreen() {
        String selectQuery = "select value from test where column_key = 'data'";

        final SQLiteDatabase database = helper.getConnection().getDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        String result = "NULL";

        if (cursor.moveToFirst()) {
            result = cursor.getString(0);
        }
        cursor.close();

        tvDefaultTransactionValue.setText(result);
        tvStatusValue.setText(getTimeStamp());
    }

    private String getTimeStamp() {
        return DateFormat.getDateTimeInstance().format(new Date());
    }
}

