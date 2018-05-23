package org.mira.companion;

import android.app.Application;

import org.mira.companion.Utils.FontsOverride;

public class CompanionApp extends Application {


    @Override
    public void onCreate()
    {
        super.onCreate();

        FontsOverride.setDefaultFont(this, "DEFAULT", "SF/SF-Pro-Display-Light.otf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "SF/SF-Pro-Display-Light.otf");
        FontsOverride.setDefaultFont(this, "SERIF", "SF/SF-Pro-Display-Light.otf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "SF/SF-Pro-Display-Light.otf");

    }

}
