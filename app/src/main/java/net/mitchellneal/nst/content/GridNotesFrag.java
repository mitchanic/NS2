package neal.mitch.nsmaster.content;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.RelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import neal.mitch.nsmaster.R;
import neal.mitch.nsmaster.application.App;
import neal.mitch.nsmaster.application.Current;
import neal.mitch.nsmaster.application.SizeData;
import neal.mitch.nsmaster.content.listFrag.ListNotesFrag;
import neal.mitch.nsmaster.events.Event;
import neal.mitch.nsmaster.utils.I;
import neal.mitch.nsmaster.utils.MathUtils;

/**
 * Created by Mitchell Neal 2/3/2018
 */

public class GridNotesFrag extends Fragment {

    Context context;
    View v;
    SizeData sizeData;
    Current current;
    private FragmentManager fm;
    private Fragment gridFrag;
    private boolean initialized = false;
    private EventBus bus;

    private RelativeLayout container;
    private View tlView;
    private View trView;
    private View blView;
    private View brView;
    private View tlClickView;
    private View trClickView;
    private View blClickView;
    private View brClickView;
    private RelativeLayout.LayoutParams tlParams;
    private FloatingActionButton dividerFab;
    private Point viewDimens;
    private Point blockDimens;
    private Point evenDimens;
    private int smallFragDimen;
    private int barThickness;
    private String quad;

    private ListNotesFrag tlFrag;
    private ListNotesFrag trFrag;
    private ListNotesFrag blFrag;
    private ListNotesFrag brFrag;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        v = inflater.inflate(R.layout.frag_grid_notes, container, false);

        bus = EventBus.getDefault();
        fm = getActivity().getSupportFragmentManager();

        initData();
        initViewElements();
        return v;
    }

    public void initData() {
        sizeData = App.getsizeData();
        current = App.getStateData();
        quad = I.evenSplit;
        current.setQuadrant(I.evenSplit);
    }

    public void initViewElements() {
        container = v.findViewById(R.id.container);
        tlView = v.findViewById(R.id.tl_frag);
        trView = v.findViewById(R.id.tr_frag);
        blView = v.findViewById(R.id.bl_frag);
        brView = v.findViewById(R.id.br_frag);

        tlClickView = v.findViewById(R.id.tl_block);
        tlClickView.setOnClickListener(mOnClick);
        trClickView = v.findViewById(R.id.tr_block);
        trClickView.setOnClickListener(mOnClick);
        blClickView = v.findViewById(R.id.bl_block);
        blClickView.setOnClickListener(mOnClick);
        brClickView = v.findViewById(R.id.br_block);
        brClickView.setOnClickListener(mOnClick);

        container.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if(!initialized) {
                            //TODO measure view and add to savedpreferences
                            //TODO change FAB location based on view dimens
                            initialized = true;
                            viewDimens = new Point();
                            viewDimens.x = container.getMeasuredWidth();
                            viewDimens.y = container.getMeasuredHeight();
                            initBlockDimens();
                            if(sizeData != null) {
                                sizeData.setNotesFragDimens(viewDimens.x, viewDimens.y);
                            }
                            updateGridViews();
                            initGridFrags();
                        }
                    }
                });
        dividerFab = v.findViewById(R.id.divider_button);
        if(dividerFab != null) {
            dividerFab.setOnClickListener(mOnClick);
        }
    }

    private RelativeLayout.OnClickListener mOnClick = new RelativeLayout.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.divider_button:
                    if(quad != I.evenSplit) {
                        quad = I.evenSplit;
                        updateGridViews();
                    }
                    break;
                case R.id.tl_block:
                    if(quad != I.topLeft) {
                        quad = I.topLeft;
                        updateGridViews();
                    }
                    break;
                case R.id.tr_block:
                    if(quad != I.topRight) {
                        quad = I.topRight;
                        updateGridViews();
                    }
                    break;
                case R.id.bl_block:
                    if(quad != I.bottomLeft) {
                        quad = I.bottomLeft;
                        updateGridViews();
                    }
                    break;
                case R.id.br_block:
                    if(quad != I.bottomRight) {
                        quad = I.bottomRight;
                        updateGridViews();
                    }
                    break;
                default:

                    break;
            }
        }
    };


    private void initGridFrags() {
        FragmentTransaction ft;

        tlFrag = new ListNotesFrag();
        tlFrag.setQuadName(I.topLeft);
        ft = fm.beginTransaction();
        ft.replace(R.id.tl_frag, tlFrag);
        ft.commit();

        trFrag = new ListNotesFrag();
        tlFrag.setQuadName(I.topRight);
        ft = fm.beginTransaction();
        ft.replace(R.id.tr_frag, trFrag);
        ft.commit();

        blFrag = new ListNotesFrag();
        tlFrag.setQuadName(I.bottomLeft);
        ft = fm.beginTransaction();
        ft.replace(R.id.bl_frag, blFrag);
        ft.commit();

        brFrag = new ListNotesFrag();
        tlFrag.setQuadName(I.bottomRight);
        ft = fm.beginTransaction();
        ft.replace(R.id.br_frag, brFrag);
        ft.commit();
    }

    private void initBlockDimens() {
        //TODO save dimens in shared preferences
        blockDimens = new Point();
        barThickness = (Math.round(MathUtils.dpToPix(2, context)));
        int x = (int) Math.round((viewDimens.x * 0.5) - barThickness);
        int y = (int) Math.round((viewDimens.y * 0.5) - barThickness);
        evenDimens = new Point(x, y);
        smallFragDimen = (int) Math.round((viewDimens.x * 0.15) - barThickness);
    }

    /* Updates the grid views by changing the width and height of the tlView's LayoutParams,
     * based on the current blockDimens */
    public void updateGridViews() {
        updateTLDimens();
        if(viewDimens != null) {
            tlParams = (RelativeLayout.LayoutParams) tlClickView.getLayoutParams();
        }
        tlParams.width = blockDimens.x;
        tlParams.height = blockDimens.y;
        tlClickView.setLayoutParams(tlParams);
        updateFabArrow();
        hideUnhideFrags();
    }

    private void hideUnhideFrags() {
        try {
            if (quad.equals(I.topLeft) || quad.equals(I.evenSplit)) {
                tlView.setVisibility(View.VISIBLE);
            } else {
                tlView.setVisibility(View.INVISIBLE);
            }

            if (quad.equals(I.topRight) || quad.equals(I.evenSplit)) {
                trView.setVisibility(View.VISIBLE);
            } else {
                trView.setVisibility(View.INVISIBLE);
            }

            if (quad.equals(I.bottomLeft) || quad.equals(I.evenSplit)) {
                blView.setVisibility(View.VISIBLE);
            } else {
                blView.setVisibility(View.INVISIBLE);
            }

            if (quad.equals(I.bottomRight) || quad.equals(I.evenSplit)) {
                brView.setVisibility(View.VISIBLE);
            } else {
                brView.setVisibility(View.INVISIBLE);
            }
            bus.post(new Event());
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    /* Updates the dimensions of the tlView based on the current quad in focus */
    private void updateTLDimens() {
        int x = evenDimens.x;
        int y = evenDimens.y;

        switch(quad) {
            case I.evenSplit:
                break;
            case I.topLeft:
                x = viewDimens.x - smallFragDimen - barThickness;
                y = viewDimens.y - smallFragDimen - barThickness;
                break;
            case I.topRight:
                x = smallFragDimen;
                y = viewDimens.y - smallFragDimen - barThickness;
                break;
            case I.bottomLeft:
                x = viewDimens.x - smallFragDimen - barThickness;
                y = smallFragDimen;
                break;
            case I.bottomRight:
                x = smallFragDimen;
                y = smallFragDimen;
                break;
        }
        blockDimens.x = x;
        blockDimens.y = y;
    }

    /* Updates the drawable in the Fab based on which quad is currently in focus */
    private void updateFabArrow() {
        if(quad == null) return;

        dividerFab.setVisibility(View.VISIBLE);
        switch(quad) {
            case I.evenSplit:
                dividerFab.setVisibility(View.INVISIBLE);
                break;
            case I.topLeft:
                dividerFab.setImageResource(R.drawable.ic_arrow_u_l);
                break;
            case I.topRight:
                dividerFab.setImageResource(R.drawable.ic_arrow_u_r);
                break;
            case I.bottomLeft:
                dividerFab.setImageResource(R.drawable.ic_arrow_d_l);
                break;
            case I.bottomRight:
                dividerFab.setImageResource(R.drawable.ic_arrow_d_r);
                break;
        }
    }

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
