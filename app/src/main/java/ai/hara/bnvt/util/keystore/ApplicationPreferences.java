package ai.hara.bnvt.util.keystore;

import android.content.Context;

public class ApplicationPreferences {
    public static final String PREFERENCES_FILE = "settings";


    private static ApplicationPreferences instance;


    private ApplicationPreferences(Context context) {
    }

    public static ApplicationPreferences getInstance(Context context) {
        if (instance == null) {
            instance = new ApplicationPreferences(context);
        }

        return instance;
    }
}
