package neal.mitch.nsmaster.content;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import neal.mitch.nsmaster.R;
import neal.mitch.nsmaster.application.App;
import neal.mitch.nsmaster.application.SizeData;
import neal.mitch.nsmaster.utils.I;
import neal.mitch.nsmaster.utils.fragments.BlankFrag;

/**
 * Created by Mitchell Neal 2/3/2018
 */

public class FirstFrag extends Fragment {

    Context context;
    View v;
    SizeData sizeData;
    private FragmentManager fm;
    private Fragment notesFrag;

    private FloatingActionButton fab;
    private String notesFragType;
    private RecyclerView catTabsRView;
    private RecyclerView.Adapter catAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        v = inflater.inflate(R.layout.frag_first, container, false);

        fm = getActivity().getSupportFragmentManager();

        initData();
        initViewElements();
        return v;
    }

    public void initData() {
        sizeData = App.getsizeData();
    }

    public void initViewElements() {
        fab = v.findViewById(R.id.fab);
        catTabsRView = v.findViewById(R.id.categories_tabs_rview);

        fab.setOnClickListener(mOnClick);

        initRView();
        initStartingFrag();
    }

    private void initRView() {

    }

    private void initStartingFrag() {
        if(notesFragType == null) {
            notesFragType = I.gridFrag;
        }
        updateNotesFrag();
    }

    private void updateNotesFrag() {
        if(notesFragType == null) return;

        FragmentTransaction ft;
        switch(notesFragType) {
            case I.gridFrag:
                if(!(notesFrag instanceof  GridNotesFrag)) {
                    notesFrag = new GridNotesFrag();
                    ft = fm.beginTransaction();
                    ft.replace(R.id.notes_frag, notesFrag);
                    ft.commit();
                }
                break;
            //TODO
            default:
                notesFrag = new BlankFrag();
                ft = fm.beginTransaction();
                ft.replace(R.id.notes_frag, notesFrag);
                ft.commit();
                break;
        }
    }

    private RelativeLayout.OnClickListener mOnClick = new RelativeLayout.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.fab:

                    break;
                default:

                    break;
            }
        }
    };
}
