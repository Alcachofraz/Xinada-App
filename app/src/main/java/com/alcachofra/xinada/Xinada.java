package com.alcachofra.xinada;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.DisplayMetrics;

import com.alcachofra.xinada.utils.SimplePrefs;

import java.util.Locale;

public class Xinada extends Application {
    public static final String LANGUAGE = "language";

    private static Xinada instance;

    public static Xinada getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;

        // Initialise Shared Preferences:
        new SimplePrefs.Builder()
                .setPrefsName("xinada")
                .setContext(this)
                .setMode(MODE_PRIVATE)
                .setDefaultUse(false)
                .build();

        // Initialise Music Manager
        new Music.Builder()
                .setMediaPlayer(MediaPlayer.create(this, R.raw.piano))
                .build();

        // Set language:
        updateLanguage(getLanguage());

        super.onCreate();
    }

    public static void updateLanguage(String lang) {
        SimplePrefs.putString(Xinada.LANGUAGE, lang);
        setLanguage(getContext(), lang);
    }

    public static void setLanguage(Context context, String lang) {
        getContext().sendBroadcast(new Intent("Language.changed"));
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration config = resources.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) config.setLocale(locale);
        else config.locale = locale;
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }

    public static String getLanguage() {
        return SimplePrefs.getString(Xinada.LANGUAGE, "en");
    }

    public static String string(int id) {
        return getInstance().getResources().getString(id);
    }
}
