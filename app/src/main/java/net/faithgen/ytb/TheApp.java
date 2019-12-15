package net.faithgen.ytb;

import android.annotation.SuppressLint;
import android.app.Application;

import net.faithgen.sdk.SDK;
import net.faithgen.sdk.enums.Subscription;
import net.faithgen.sdk.menu.MenuChoice;

import java.io.IOException;

import nouri.in.goodprefslib.GoodPrefs;

public class TheApp extends Application {
    @SuppressLint("ResourceType")
    @Override
    public void onCreate() {
        super.onCreate();
        try {
            GoodPrefs.init(this);
            SDK.initializeSDK(this, this.getAssets().open("config.json"), getResources().getString(R.color.colorPrimaryDark), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
