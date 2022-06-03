package com.example.wasla.Helpers.View;

import android.content.Context;
import android.widget.Toast;

import com.example.wasla.R;

public class NoteMessage {

    /**
     * to show toast no internet
     */
    public static void TOAST_NO_INTERNET(Context context) {
        Toast.makeText(context, context.getString(R.string.check_internet), Toast.LENGTH_SHORT).show();
    }

    /**
     * to show toast
     */
    public static void TOAST_ERROR(Context context, String error) {
        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();

    }
}
