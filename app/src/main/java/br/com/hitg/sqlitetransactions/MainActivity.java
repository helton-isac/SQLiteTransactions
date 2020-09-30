package br.com.hitg.sqlitetransactions;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import br.com.hitg.sqlitetransactions.sqlite.SQLiteDatabaseConnection;
import br.com.hitg.sqlitetransactions.sqlite.SQLiteDatabaseHelper;
import br.com.hitg.sqlitetransactions.sqlite.SQLiteInstanceManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SQLiteDatabaseHelper helper = SQLiteDatabaseHelper.getInstance();
        helper.setConnection(new SQLiteDatabaseConnection(this));
    }
}