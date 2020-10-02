package br.com.hitg.sqlitetransactions.helper;

import android.content.Context;
import android.widget.Toast;

public class Helper {

    // Don`t do that.
    private static Context context;

    public static void setContext(Context context) {
        Helper.context = context;
    }


    public static void showToastMessage(String message) {
        Toast.makeText(Helper.context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
