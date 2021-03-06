package br.com.hitg.sqlitetransactions;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Date;

import br.com.hitg.sqlitetransactions.helper.Helper;
import br.com.hitg.sqlitetransactions.sqlite.DAO;
import br.com.hitg.sqlitetransactions.sqlite.SQLiteDatabaseConnection;
import br.com.hitg.sqlitetransactions.sqlite.SQLiteDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    private static String TRANSACTION_A = "TRANSACTION_A";
    private static String TRANSACTION_B = "TRANSACTION_B";
    private SQLiteDatabaseHelper helper;
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
        Helper.setContext(this);

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
                DAO.updateValueOnTransaction(helper.getDefaultConnection());
                refreshScreen();
            }
        });

        btTranAStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.beginTransaction(MainActivity.this, TRANSACTION_A);
                refreshScreen();
            }
        });

        btTranAUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DAO.updateValueOnTransaction(helper.getConnectionByTransaction(TRANSACTION_A));
                refreshScreen();
            }
        });

        btTranACommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabaseHelper.getInstance().commitTransaction(MainActivity.this, TRANSACTION_A);
                refreshScreen();
            }
        });

        btTranARollback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabaseHelper.getInstance().rollbackTransaction(MainActivity.this, TRANSACTION_A);
                refreshScreen();
            }
        });

        btTranBStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.beginTransaction(MainActivity.this, TRANSACTION_B);
                refreshScreen();
            }
        });

        btTranBUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DAO.updateValueOnTransaction(helper.getConnectionByTransaction(TRANSACTION_B));
                refreshScreen();
            }
        });

        btTranBCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabaseHelper.getInstance().commitTransaction(MainActivity.this, TRANSACTION_B);
                refreshScreen();
            }
        });

        btTranBRollback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabaseHelper.getInstance().rollbackTransaction(MainActivity.this, TRANSACTION_B);
                refreshScreen();
            }
        });


    }


    private void refreshScreen() {
        String result = DAO.getValueInConnection(helper.getDefaultConnection());
        tvDefaultTransactionValue.setText(result);
        tvStatusValue.setText(getTimeStamp());

        tvDefaultTransactionValue.setText(result);
        tvStatusValue.setText(getTimeStamp());

        SQLiteDatabaseConnection transactionA = helper.getConnectionByTransaction(TRANSACTION_A);
        SQLiteDatabaseConnection transactionB = helper.getConnectionByTransaction(TRANSACTION_B);

        if (transactionA == null) {
            btTranAStart.setEnabled(true);
            btTranAUpdate.setEnabled(false);
            btTranACommit.setEnabled(false);
            btTranARollback.setEnabled(false);
            tvTranAValue.setText("Transaction not started");
        } else {
            String tvTranAValueResult = DAO.getValueInConnection(transactionA);
            tvTranAValue.setText(tvTranAValueResult);
            btTranAStart.setEnabled(false);
            btTranAUpdate.setEnabled(true);
            btTranACommit.setEnabled(true);
            btTranARollback.setEnabled(true);
        }

        if (transactionB == null) {
            btTranBStart.setEnabled(true);
            btTranBUpdate.setEnabled(false);
            btTranBCommit.setEnabled(false);
            btTranBRollback.setEnabled(false);
            tvTranBValue.setText("Transaction not started");
        } else {
            String tvTranBValueResult = DAO.getValueInConnection(transactionB);
            tvTranBValue.setText(tvTranBValueResult);
            btTranBStart.setEnabled(false);
            btTranBUpdate.setEnabled(true);
            btTranBCommit.setEnabled(true);
            btTranBRollback.setEnabled(true);
        }
    }

    private String getTimeStamp() {
        return DateFormat.getDateTimeInstance().format(new Date());
    }
}

