package pl.edu.agh.gitclient.util;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public final class KeyboardUtils {

    public static void hideSoftKeyboardIfOpened(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

}
