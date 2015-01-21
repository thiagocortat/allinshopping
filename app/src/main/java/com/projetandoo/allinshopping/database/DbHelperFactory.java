package com.projetandoo.allinshopping.database;

import android.content.Context;

public final class DbHelperFactory
{

    public DbHelperFactory(){}

	public static DbHelper getDbHelper(Context context)
    {
        DbHelper helper = new DbHelper(context);
        return helper;
    }
}
