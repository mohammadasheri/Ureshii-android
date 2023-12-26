package ai.hara.ureshii.util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public final class KeyboardUtils {

    private KeyboardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static void showSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }

    public static void hideSoftInput(final Activity activity) {
        View view = activity.getCurrentFocus();
        if (view == null) view = new View(activity);
        InputMethodManager imm =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void forceHideSoftInput(final Activity activity, final View view) {
        InputMethodManager a = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        a.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void forceOpenSoftInput(final Activity activity, final View view) {
        InputMethodManager a = (InputMethodManager)activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        a.showSoftInput(view, InputMethodManager.SHOW_FORCED);
    }
}