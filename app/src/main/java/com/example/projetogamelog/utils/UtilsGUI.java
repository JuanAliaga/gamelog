package com.example.projetogamelog.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.projetogamelog.R;

public class UtilsGUI {

    public static void alertDialogConfirm(Context context, String msg, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.excluirButton);
        builder.setMessage(msg);
        builder.setPositiveButton(R.string.excluirButton, listener);
        builder.setNegativeButton(R.string.cancel, listener);

        AlertDialog alert = builder.create();
        alert.show();

    }
}
