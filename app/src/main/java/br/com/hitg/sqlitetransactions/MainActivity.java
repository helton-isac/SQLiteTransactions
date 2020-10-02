package br.com.hitg.sqlitetransactions;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Date;

import br.com.hitg.sqlitetransactions.sqlite.DAO;
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

    private static String TRANSACTION_A = "TRANSACTION_A";

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
        DAO.resetTables();
    }

    private void initializeControls() {
        tvDefaultTransactionValue = findViewById(R.id.tvDefaultTransactionValue);
        tvTranAValue = findViewById(R.id.tvTranAValue);

        tvTranBValue = findViewById(R.id.tvTranBValue);
        tvStatusValue = findViewById(R.id.tvStatusValue);

        btTranAStart = findViewById(R.id.btTranAStart);
        btDefaultTranUpdate = findViewById(R.id.btDefaultTranUpdate);
        btRefreshScreen = findViewById(R.id.btRefreshScreen);

        btTranAUpdate = findViewById(R.id.btTranAUpdate);
        btTranACommit = findViewById(R.id.btTranACommit);
        btTranARollback = findViewById(R.id.btTranARollback);

        btTranBUpdate = findViewById(R.id.btTranBUpdate);
        btTranBStart = findViewById(R.id.btTranBStart);
        btTranBCommit = findViewById(R.id.btTranBCommit);
        btTranBRollback = findViewById(R.id.btTranBRollback);

        btRefreshScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshScreen();
            }
        });

        btDefaultTranUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValueOnDefaultTransaction();
            }
        });

        btTranAStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTransactionA();
            }
        });

        btTranAUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateValueOnTransactionA();
            }
        });


    }

    private void startTransactionA() {
        helper.beginTransaction(this, TRANSACTION_A);
    }

    private void updateValueOnDefaultTransaction() {
        DAO.updateValueOnTransaction(helper.getDefaultConnection());
        refreshScreen();
    }

    private void updateValueOnTransactionA() {
        DAO.updateValueOnTransaction(helper.getConnectionByTransaction(TRANSACTION_A));
        refreshScreen();
    }

    private void refreshScreen() {
        String result = DAO.getValueInConnection(helper.getDefaultConnection());
        tvDefaultTransactionValue.setText(result);
        tvStatusValue.setText(getTimeStamp());
    }

    private String getTimeStamp() {
        return DateFormat.getDateTimeInstance().format(new Date());
    }
}

