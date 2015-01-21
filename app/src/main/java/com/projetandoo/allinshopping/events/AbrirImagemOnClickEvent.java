package com.projetandoo.allinshopping.events;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;

import java.io.File;

import static android.content.Intent.ACTION_VIEW;

public class AbrirImagemOnClickEvent
        implements OnClickListener {
    @Override
    public void onClick(View view) {
        Activity activity = (Activity) view.getContext();
        Uri uri = Uri.fromFile(new File(view.getTag().toString()));

        Intent intent = new Intent();
        intent.setAction(ACTION_VIEW);
        intent.setDataAndType(uri, "image/jpeg");
        activity.startActivity(intent);
    }
}
