package neal.mitch.nsmaster.content.pager;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import neal.mitch.nsmaster.R;
import neal.mitch.nsmaster.content.FirstFrag;
import neal.mitch.nsmaster.utils.fragments.BlankFrag;

/**
 * Created by Mitchell Neal on 2/3/2018.
 *
 *
 * This is the fragment that contains the ViewPager that holds the Summary fragments (group and
 * light summary).
 * Note: The class that determines what fragments fill this fragment's ViewPager is located at
 * /pagerAdapters/SummaryContainerPagerAdapter
 **/

public class ContainerViewPager extends Fragment {
    View v = null;
    EventBus bus = EventBus.getDefault();
    Context mContext;

    boolean vis = false;
    private int currentPageNumber = 0;
    String currentPageTitle = null;
    ViewPager pager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity();
        v = inflater.inflate(R.layout.frag_container_summary, container, false);
        initializeViewElements(v);

        return v;
    }

    /* Initializes ViewPager and Floating Action Button */
    private void initializeViewElements(View v) {
        initViewPager();
    }

    /* Sets up viewpager and sets current (starting) page */
    private void initViewPager(){
        TabLayout tabs = (TabLayout) v.findViewById(R.id.tabs);
        ViewPager pager = (ViewPager) v.findViewById(R.id.pager);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPageNumber = position;
                switch (position) {
                    case 0:
                        //TODO
                        break;
                    case 1:
                        //TODO
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                currentPageNumber = position;
                switch (position) {
                    case 0:
                        //TODO
                        break;
                    case 1:
                        //TODO
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_DRAGGING) {
                    currentPageTitle = null;
                }
            }
        });
        PagerAdapterSummaryContainer adapter = new PagerAdapterSummaryContainer(getChildFragmentManager());
        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);
        pager.setCurrentItem(0);
    }

    public class PagerAdapterSummaryContainer extends FragmentPagerAdapter {

        public PagerAdapterSummaryContainer(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0:
                    return new FirstFrag();
            }
            return new BlankFrag();
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return "Title1";
                case 1:
                    return "Title 2";
                case 2:
                    return "Title 3";
            }
            return "";
        }
    }



    /**
     * Utilities and Overrides
     * =============================================================================================
     */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
