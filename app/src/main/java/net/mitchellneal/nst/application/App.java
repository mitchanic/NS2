package neal.mitch.nsmaster.application;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import neal.mitch.nsmaster.dataStructures.AllData;

/**
 * Created by Mitchell Neal 2/3/2018
 */

public class App extends Application {
    
    private SizeData sizeData;
    private Current current;
    private AllData allData;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        init();//TODO move out into public func to be called independently
    }

    public void init() {
        sizeData = new SizeData();
        current = new Current();
        allData = new AllData();
    }

    public static SizeData getsizeData() {
        if (context != null) {
            App app = (App) context;
            return app.sizeData;
        } else {
            return null;
        }
    }

    public static AllData getallData() {
        if (context != null) {
            App app = (App) context;
            return app.allData;
        } else {
            return null;
        }
    }

    public static Current getStateData() {
        if (context != null) {
            App app = (App) context;
            return app.current;
        } else {
            return null;
        }
    }

    public static Context getContext() {
        return context;
    }

    public static void toastMe(String text) {
        if (context != null) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show();
        }
    }
}
