package org.mira.companion;

import android.app.Application;
import android.content.ContextWrapper;

import org.mira.companion.Utils.FontsOverride;
import org.mira.companion.Utils.Prefs;

public class CompanionApp extends Application {


    @Override
    public void onCreate()
    {
        super.onCreate();

        // Initializing the preferences helper
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();



        // Overriding default fonts
        FontsOverride.setDefaultFont(this, "DEFAULT", "SF/SF-Pro-Display-Light.otf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "SF/SF-Pro-Display-Light.otf");
        FontsOverride.setDefaultFont(this, "SERIF", "SF/SF-Pro-Display-Light.otf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "SF/SF-Pro-Display-Light.otf");

    }

}
