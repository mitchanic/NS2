package neal.mitch.nsmaster.application;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Point;

import neal.mitch.nsmaster.utils.I;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mitchell Neal 2/3/2018
 */

public class Current {

    public Activity activity;
    private boolean initialized = false;
    private boolean tutorialEnabled = false;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    private String category;


    public Current() {}

    public void init() {
        if (activity == null) return;
        if(initialized) {
            return;
        }

        settings = activity.getPreferences(MODE_PRIVATE);
        fetchSavedData();
        initialized = true;
    }

    private void fetchSavedData() {
        tutorialEnabled = settings.getBoolean(I.tutorial, false);       //todo change to true initially
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


}
