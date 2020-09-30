package br.com.hitg.sqlitetransactions;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class SQLiteTestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Stetho.initializeWithDefaults(this);
    }
}
