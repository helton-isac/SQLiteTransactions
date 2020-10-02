package br.com.hitg.sqlitetransactions.helper;

import android.content.Context;
import android.widget.Toast;

public class Helper {
    public static void showToastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
