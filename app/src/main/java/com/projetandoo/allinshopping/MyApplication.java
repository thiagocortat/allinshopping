package com.projetandoo.allinshopping;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by glavko on 16/08/14.
 */
public class MyApplication extends Application {

    private static boolean isDev = false;
    private static Context mContext;


    @SuppressLint("NewApi")
    @Override
    public void onCreate() {
        setAppContext(getApplicationContext());
        super.onCreate();

//        Constants.PACKAGE_NAME = getPackageName();
//        try {
//            Constants.APP_VERSION = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
//        }catch (PackageManager.NameNotFoundException e){}



        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }


    public static Properties loadProperties(String name){
        Resources resources = getAppContext().getResources();
        AssetManager assetManager = resources.getAssets();

        try{
            // Pegamos do arquivo de configuração se a app está rodando em desenvolvimento ou em produção
            InputStream inputStream = assetManager.open(name);//("environment.properties");
            Properties envProps = new Properties();
            envProps.load(inputStream);
            return envProps;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static void setAppContext(Context context) {
        mContext = context;
    }

    public static Context getAppContext() {
        return mContext;
    }
}
