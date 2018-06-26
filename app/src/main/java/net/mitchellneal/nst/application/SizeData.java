package neal.mitch.nsmaster.application;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Point;

import neal.mitch.nsmaster.utils.I;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Mitchell Neal 2/3/2018
 */

public class SizeData {

    public Point screenDimens;
    public Point usableScreenDimens;
    public Point topBarDimens;
    public Point notesViewDimens;
    public Point popupDimens;

    public Activity activity;
    private boolean initialized = false;

    SharedPreferences settings;
    SharedPreferences.Editor editor;

    public SizeData() {}

    public void init() {
        if (activity == null) return;
        if(initialized) {
            return;
        }

        settings = activity.getPreferences(MODE_PRIVATE);
        fetchSavedDimens();
        initialized = true;
    }

    private void fetchSavedDimens() {
        String wid;
        String ht;

        wid = settings.getString(I.width, null);
        ht = settings.getString(I.height, null);
        if (wid != null && ht != null) {
            setScreenDimens(Integer.parseInt(wid), Integer.parseInt(ht));
        }

        wid = settings.getString(I.topBarWidth, null);
        ht = settings.getString(I.topBarHeight, null);
        if (wid != null && ht != null) {
            setTopBarDimens(Integer.parseInt(wid), Integer.parseInt(ht));
        }
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setScreenDimens(int x, int y) {
        //if (!initialized) init();

        if (settings != null) {
            editor = settings.edit();
            editor.putString(I.width, String.valueOf(x));
            editor.putString(I.height, String.valueOf(y));
            editor.apply();
        }

        if(screenDimens == null) {
            screenDimens = new Point();
        }
        screenDimens.x = x;
        screenDimens.y = y;
    }

    public void setTopBarDimens(int x, int y) {
        if (!initialized)
            init();

        if (settings != null) {
            editor = settings.edit();
            editor.putString(I.topBarWidth, String.valueOf(x));
            editor.putString(I.topBarHeight, String.valueOf(y));
            editor.apply();
        }

        if(topBarDimens == null) {
            topBarDimens = new Point();
        }
        topBarDimens.x = x;
        topBarDimens.y = y;
    }

    public void setNotesFragDimens(int x, int y) {
        if (!initialized)
            init();

        if (settings != null) {
            editor = settings.edit();
            editor.putString(I.notesViewWidth, String.valueOf(x));
            editor.putString(I.notesViewHeight, String.valueOf(y));
            editor.apply();
        }

        if(notesViewDimens == null) {
            notesViewDimens = new Point();
        }
        notesViewDimens.x = x;
        notesViewDimens.y = y;
    }

    public void setUsableScreenDimens(int x, int y) {
        if (!initialized)
            init();


        //TODO
    }

    public void initDimensFromScreenDimens() {
        if (screenDimens == null) return;

        setTopBarDimens(screenDimens.x, (int) Math.round(screenDimens.y * 0.08));//TODO make better
    }
}
