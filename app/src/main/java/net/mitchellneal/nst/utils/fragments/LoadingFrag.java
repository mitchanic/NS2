package neal.mitch.nsmaster.utils.fragments;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import neal.mitch.nsmaster.R;
import neal.mitch.nsmaster.application.App;
import neal.mitch.nsmaster.application.SizeData;

/**
 * Created by Mitchell Neal on 2/3/2018.
 *
 *
 * This fragment is a loading screen, used at the start of the app to hide the initialization of
 * data and of the real fragments.
 **/

public class LoadingFrag extends Fragment {

    boolean initialized = false;
    SizeData sizeData;

    public LoadingFrag() {
        sizeData = App.getsizeData();
    }

    public static LoadingFrag newInstance() {
        LoadingFrag ret = new LoadingFrag();
        return ret;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_loading, container, false);
        v.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (getView() != null) {
                    if (!initialized) {
                        Log.d("LOADING", "Measured " + getView().getMeasuredWidth() + " "+getView().getMeasuredHeight());
                       if (sizeData != null) {
                            if (sizeData.usableScreenDimens == null) {
                                Point dimens = new Point(getView().getMeasuredWidth(), getView().getMeasuredHeight());
                                if (!(dimens.x == 0 || dimens.y == 0)) {
                                    sizeData.usableScreenDimens = dimens;
                                    if (sizeData.screenDimens != null) {
                                        Point topBarDim = new Point();
                                        topBarDim.x = sizeData.screenDimens.x;
                                        topBarDim.y = (sizeData.screenDimens.y - sizeData.usableScreenDimens.y);
                                        if (topBarDim.y > 0) {
                                            sizeData.topBarDimens = topBarDim;
                                        }
                                    }
                                    sizeData.initDimensFromScreenDimens();
                                    initialized = true;
                                }
                            }
                        }
                    }
                }
            }
        });
        return v;
    }
}
