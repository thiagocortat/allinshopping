package com.projetandoo.allinshopping.interfaces;

import android.os.Bundle;
import android.support.annotation.LayoutRes;

/**
 * Created by glavko on 29/09/14.
 */
public interface OnLayoutInjectListener {

    @LayoutRes
    public int getLayoutResource();

    public void onBeforeInjectViews(Bundle savedInstanceState);

    public void onAfterInjectViews(Bundle savedInstanceState);
}
