package com.projetandoo.allinshopping.alerts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import com.projetandoo.allinshopping.AutenticacaoAtualizacaoSistemaActivity;

public class UpdateApplicationDialog extends AbstractDialog {

    public UpdateApplicationDialog(Context context) {
        super(context);
    }

    @Override
    public void show() {
        AlertDialog alertdialog = getBuilder().create();
        alertdialog.setButton(AlertDialog.BUTTON_POSITIVE, "Sim", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Activity context = (Activity) getContext();
                Intent intent = new Intent(context, AutenticacaoAtualizacaoSistemaActivity.class);
                context.startActivity(intent);
//                context.finish();
            }
        });
        alertdialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Não", new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertdialog.show();
    }

}
