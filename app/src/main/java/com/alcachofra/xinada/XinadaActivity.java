package com.alcachofra.xinada;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;

import java.util.Locale;

/**
 * Template activity for Xinada
 */
public class XinadaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Xinada.setLanguage(getBaseContext(), Xinada.getLanguage());

        // Initialise activity in fullscreen:
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    protected void template(@LayoutRes int layout) {
        setContentView(layout);

        AppCompatImageButton info = findViewById(R.id.info);
        AppCompatImageButton piano = findViewById(R.id.piano);
        AppCompatImageView cross = findViewById(R.id.cross);

        // Manage music:
        if (cross != null) {
            if (Music.isPlaying()) cross.setVisibility(View.INVISIBLE);
            else cross.setVisibility(View.VISIBLE);

            // Info
            info.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                startActivity(intent);
            });

            // Music:
            piano.setOnClickListener(v -> {
                if (Music.toggle()) cross.setVisibility(View.INVISIBLE);
                else cross.setVisibility(View.VISIBLE);
            });
        }
    }

    @Override
    protected void onResume() {
        Xinada.setLanguage(getBaseContext(), Xinada.getLanguage());
        System.out.println("----");
        System.out.println(this.getResources().getConfiguration().locale);
        System.out.println(getBaseContext().getResources().getConfiguration().locale);
        System.out.println(getApplicationContext().getResources().getConfiguration().locale);
        System.out.println(Xinada.getContext().getResources().getConfiguration().locale);
        System.out.println("----");
        super.onResume();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(updateBaseContextLocale(base));
    }

    private Context updateBaseContextLocale(Context context) {
        String language = Xinada.getLanguage(); // Helper method to get saved language from SharedPreferences
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            return updateResourcesLocale(context, locale);
        }

        return updateResourcesLocaleLegacy(context, locale);
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    private Context updateResourcesLocale(Context context, Locale locale) {
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    @Override
    public void applyOverrideConfiguration(Configuration overrideConfiguration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
            overrideConfiguration.setLocale(new Locale(Xinada.getLanguage()));
        }
        super.applyOverrideConfiguration(overrideConfiguration);
    }

    private Context updateResourcesLocaleLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }
}
