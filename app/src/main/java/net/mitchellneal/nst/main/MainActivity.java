package neal.mitch.nsmaster.main;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import neal.mitch.nsmaster.R;
import neal.mitch.nsmaster.application.App;
import neal.mitch.nsmaster.application.SizeData;
import neal.mitch.nsmaster.content.pager.ContainerViewPager;
import neal.mitch.nsmaster.utils.fragments.BlankFrag;
import neal.mitch.nsmaster.utils.fragments.LoadingFrag;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> menuItemList;
    private DrawerLayout drawer;
    private ListView drawerList;
    private View container;
    private Toolbar toolbar;
    private ImageView toolbarLeftButton;
    private ImageView toolbarRightButton;

    private SizeData sizeData;
    private Point screenDimens;
    private Fragment loadingFrag;
    private Fragment mainFrag;
    private boolean initialized = false;
    private FragmentManager fm;
    private boolean tutorialEnabled = false;

    public MainActivity() {}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();

        initLoadingScreen();
        initViewElementsThenStart();
    }

    /* Fetches data such as the global sizeData variable.
     * Initializes data such as database data and shared preferences.
     * Called in initDataThenStart() in initViewelementsThenStart() */
    public void initData() {
        sizeData = App.getsizeData();
        if(sizeData == null) {
            App.toastMe("sizeData IS NULL");
        }
        sizeData.init();
        if(sizeData.screenDimens == null) {
            if(screenDimens != null) {
                sizeData.setScreenDimens(screenDimens.x, screenDimens.y);
                sizeData.initDimensFromScreenDimens();
            }
        }
    }

    private RelativeLayout.OnClickListener mOnClick = new RelativeLayout.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.toolbar_left_button:
                    toggleOpenDrawer();
                    break;
                case R.id.toolbar_right_button:
                    //TODO settings gear functionality
                    break;

            }
        }
    };

    /* Initializes the starting fragments (controlTabFrag, shelfFrag, and summaryTabFrag) as
     * invisible, which are initially hidden behind the loadingFrag when this is called */
    public void initMainFragInvisible() {
        if (tutorialEnabled) {
            //TODO startTutorial();
        } else {
            mainFrag = new ContainerViewPager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fullscreen_frag, mainFrag);
            ft.commit();
        }
    }



    /** Loading Screen/Initialization
     * ============================================================================================
     *
     **/

    /* Initializes the loading screen, which goes in front of the rest of the app while other data
     * is loaded in the background. */
    public void initLoadingScreen() {
        LoadingFrag lFrag = new LoadingFrag();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.loading_frag, lFrag);
        fragmentTransaction.commit();
    }

    public void initViewElementsThenStart() {
        container = findViewById(R.id.main_container);
        container.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if(!initialized) {
                            //TODO measure view and add to savedpreferences
                            initialized = true;
                            if (screenDimens == null) {
                                screenDimens = new Point();
                                screenDimens.x = container.getMeasuredWidth();
                                screenDimens.y = container.getMeasuredHeight();
                            }
                            initToolbar();
                            initializeDrawerMenu();
                            initFragDataThenStart();
                        }
                    }
                });
    }

    /* Calls fetchInitialData() in the background, then once lightData and sizeData have been
     * fetched, initializes invisible fragments behind the loadingFrag and calls fadeLoadingScreenOut() */
    private void initFragDataThenStart() {
        new Thread() {
            public void run() {
                initData();
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(sizeData != null && toolbar != null) {
                                if(toolbar.getMeasuredHeight() != 0) {
                                    RelativeLayout.LayoutParams toolbarParams =
                                        (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
                                    toolbarParams.height = sizeData.topBarDimens.y;
                                    toolbar.setLayoutParams(toolbarParams);
                                }
                            }
                            initMainFragInvisible();
                            fadeLoadingScreenOut();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /* Fades the loading screen out, either to a tutorial if needed or to the starting frags */
    public void fadeLoadingScreenOut() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                try {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toolbarLeftButton.setVisibility(View.VISIBLE);
                            toolbarRightButton.setVisibility(View.VISIBLE);

                            BlankFrag blankFrag = new BlankFrag();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.setCustomAnimations(R.anim.fade_in,
                                    R.anim.fade_out);
                            ft.replace(R.id.loading_frag, blankFrag);
                            ft.commit();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 1000);
    }

    /* Sets up the toolbar view elements */
    public void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        toolbarLeftButton = (ImageView) findViewById(R.id.toolbar_left_button);
        toolbarRightButton = (ImageView) findViewById(R.id.toolbar_right_button);
        toolbarLeftButton.setVisibility(View.INVISIBLE);
        toolbarRightButton.setVisibility(View.INVISIBLE);
        toolbarLeftButton.setOnClickListener(mOnClick);
        toolbarRightButton.setOnClickListener(mOnClick);
    }


    /** Drawer/Menu Methods
     * ============================================================================================
     * //TODO move these to another class once there are more of them
     **/
    /* Handles clicks on items within the drawer menu.
     *
     * parent:
     * view:
     * position:
     * id:
     *
     * returns: Nothing
     */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (position == 0) {
                /*Uri webpage = Uri.parse("https://luminaid.com/collections/solar-lights");
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
                startActivity(webIntent);*/
            }
        }
    }

    /* Sets up the drawer menu, adds items to the list, and sets the itemOnClickListener for the
     * items in the list.
     *
     * arguments: None
     *
     * returns: Nothing
     */
    public void initializeDrawerMenu() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        drawerList = (ListView) findViewById(R.id.left_drawer);
        menuItemList = new ArrayList<String>();

        menuItemList.add("Item1");
        menuItemList.add("Item2");
        menuItemList.add("Item3");
        menuItemList.add("Item4");

        drawerList.setAdapter(new MenuItemAdapter(this, menuItemList));
        drawerList.setOnItemClickListener(new MainActivity.DrawerItemClickListener());
    }

    private void toggleOpenDrawer() {
        if(drawer == null) return;

        if (drawer.isDrawerOpen(Gravity.LEFT)) {
            drawer.closeDrawer(Gravity.LEFT);
        } else {
            drawer.openDrawer(Gravity.LEFT);
        }
    }


    /** Overrides and Utils
     * ============================================================================================
     *
     **/

    @Override
    /* Handles onKeyDown (back button) calls
     *
     * keyCode: The code for what kind of key event was called
     * event: The key event
     *
     * returns: boolean indicating success (?)
     */
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Log.d("MAIN", "onBackPressed Called");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}

/*
container.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        //Log.d("MAINACTIVITY", "OnGlobalLayoutCalled "+container.getMeasuredWidth() + " " + container.getMeasuredHeight());
                        if (screenDimens == null) {
                            screenDimens = new Point();
                            screenDimens.x = container.getMeasuredWidth();
                            screenDimens.y = container.getMeasuredHeight();
                            if (sizeData != null) {
                                if (sizeData.screenDimens == null) {
                                    sizeData.screenDimens = new Point(screenDimens);
                                    //sizeData.initDimensFromScreenDimens();
                                }
                            }

                            if (settings != null) {
                                SharedPreferences.Editor editor = settings.edit();
                                editor.putString(I.width, String.valueOf(screenDimens.x));
                                editor.putString(I.height, String.valueOf(screenDimens.y));
                                editor.apply();
                            }
                        }
                        if (!initted) {
                            initted = true;
                            //Log.d("MAINACTIVITY", "LoadingScreenInitialized");
                            initializeDrawerMenu();
                            initDataThenStart();
                            //Log.d("MAINACTIVITY", "Starting");
                        }
                    }
                });


 */