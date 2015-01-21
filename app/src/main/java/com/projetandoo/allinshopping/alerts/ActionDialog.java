package com.projetandoo.allinshopping.alerts;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import com.projetandoo.allinshopping.MainActivity;

public class ActionDialog extends AbstractDialog {

    public ActionDialog(Context context) {
        super(context);
    }

    public void show() {
        getBuilder().setNeutralButton("OK", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Activity current = (Activity) getContext();
                Intent intent = new Intent(current, MainActivity.class);
                current.startActivity(intent);
                current.finish();

            }
        }).create().show();
    }


}
