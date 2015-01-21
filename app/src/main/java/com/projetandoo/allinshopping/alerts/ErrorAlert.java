package com.projetandoo.allinshopping.alerts;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class ErrorAlert extends AbstractDialog {

    public ErrorAlert(Context context) {
        super(context);
    }

    public void show() {
        getBuilder().setNeutralButton("OK", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        }).create().show();
    }

}
