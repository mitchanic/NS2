package neal.mitch.nsmaster.utils.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import neal.mitch.nsmaster.R;

/**
 * Created by Mitchell Neal on 2/3/2018.
 *
 *
 * This is just a blank placeholder fragment.
 **/

public class BlankFrag extends Fragment {

    public BlankFrag() {

    }

    public static BlankFrag newInstance() {
        BlankFrag ret = new BlankFrag();
        return ret;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frag_blank, container, false);
        return v;
    }
}
