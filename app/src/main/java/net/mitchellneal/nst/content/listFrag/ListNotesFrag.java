package neal.mitch.nsmaster.content.listFrag;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import neal.mitch.nsmaster.R;
import neal.mitch.nsmaster.application.App;
import neal.mitch.nsmaster.application.SizeData;
import neal.mitch.nsmaster.events.Event;

/**
 * Created by Mitchell Neal 2/3/2018
 */

public class ListNotesFrag extends Fragment {

    Context context;
    View v;
    SizeData sizeData;
    private boolean measured = false;
    private EventBus bus;

    private RelativeLayout container;
    private Point parentViewDimens;
    private Point currentBockDimens;
    private Point focusedBlockDimens;

    private String quadName;
    private boolean standAlone = false;
    private boolean inFocus = true;
    private boolean evenSplit = false;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private int backgroundColor = 0;

    private TextView title;
    private ImageView sortButton;
    private ImageView optionsButton;

    public ListNotesFrag() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        v = inflater.inflate(R.layout.frag_list_notes, container, false);
        bus = EventBus.getDefault();

        init();
        return v;
    }

    public void setQuadName(String quadName) {
        this.quadName = quadName;
    }

    private void init() {
        if(quadName == null) {
            standAlone = true;
        }

        initData();
        initRecyclerView();
        initViewElements();
    }

    private void initData() {
        sizeData = App.getsizeData();
    }

    private void initRecyclerView() {

    }

    private void initViewElements() {
        container = v.findViewById(R.id.container);
        container.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if(!measured) {
                            //TODO measure view and add to savedpreferences
                            measured = true;
                            //TODO
                        }
                    }
                });
        title = v.findViewById(R.id.title);
        optionsButton = v.findViewById(R.id.title_options_button);
        sortButton = v.findViewById(R.id.sort_button);

        optionsButton.setOnClickListener(mOnClick);
        sortButton.setOnClickListener(mOnClick);
        title.setOnClickListener(mOnClick);
    }

    private RelativeLayout.OnClickListener mOnClick = new RelativeLayout.OnClickListener() {

        @Override
        public void onClick(View v) {
            if(inFocus) {
                switch(v.getId()) {
                    default:
                        App.toastMe("Click");
                        break;
                }
            }
        }
    };

    @Subscribe
    private void onEvent(Event event) {

    }

    @Override
    public void onPause() {
        if(bus.isRegistered(this)) {
            bus.unregister(this);
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!bus.isRegistered(this)) {
            bus.register(this);
        }
    }
}
