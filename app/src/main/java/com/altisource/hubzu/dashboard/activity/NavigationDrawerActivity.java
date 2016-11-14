package com.altisource.hubzu.dashboard.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.altisource.hubzu.dashboard.fragment.IncidentFragment;

import com.altisource.hubzu.dashboard.R;
import com.altisource.hubzu.dashboard.fragment.MonitorFragment;
import com.altisource.hubzu.dashboard.fragment.SignOutFragment;

public class NavigationDrawerActivity extends AppCompatActivity {

    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;
    private View mNavHeader;
    private TextView mTxtName;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_MONITOR = "monitor";
    private static final String TAG_INCIDENT = "incident";

    public static String CURRENT_TAG = TAG_MONITOR;
    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;
    public static ImageButton navBack,menuRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setTitle("DASHBOARD");

        mHandler = new Handler();
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        mNavHeader = mNavigationView.getHeaderView(0);
        mTxtName = (TextView) mNavHeader.findViewById(R.id.name);
        menuRight = (ImageButton) findViewById(R.id.menuRight);
        navBack = (ImageButton) findViewById(R.id.nav_back);
        navBack.setVisibility(View.INVISIBLE);
        menuRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawer.isDrawerOpen(GravityCompat.END)) {
                    mDrawer.closeDrawer(GravityCompat.END);
                } else {
                    mDrawer.openDrawer(GravityCompat.END);
                }
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Refreshing", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_MONITOR;
            loadHomeFragment();
        }
    }

    private void loadNavHeader() {
        mTxtName.setText(getString(R.string.tv_name));
    }

    private void loadHomeFragment() {

        selectNavMenu();
        setToolbarTitle();

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();
        //Closing drawer on item click
        mDrawer.closeDrawers();
        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                MonitorFragment monitorFragment = new MonitorFragment();
                return monitorFragment;
            case 1:
                IncidentFragment incidentFragment = new IncidentFragment();
                return incidentFragment;
            case 2:
                SignOutFragment signoutFragment = new SignOutFragment();
                return signoutFragment;
            default:
                return new MonitorFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle("Dashboard");
    }

    private void selectNavMenu() {
        mNavigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }


    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_monitor:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_MONITOR;
                        navBack.setVisibility(View.GONE);
                        loadHomeFragment();
                        break;
                    case R.id.nav_incident:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_INCIDENT;
                        loadHomeFragment();
                        break;
                    case R.id.nav_signout:
                        navItemIndex = 2;
                        navBack.setVisibility(View.GONE);
                        Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(i);
                        break;
                    default:
                        navItemIndex = 0;
                }
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

    }

    @Override
    public void onBackPressed() {
        NavigationDrawerActivity.navBack.setVisibility(View.GONE);
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawers();
            return;
        }

        // This code loads monitor fragment when back key is pressed
        // when user is in other fragment than monitor
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than monitor
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_MONITOR;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        // show menu only when home fragment is selected
        if (navItemIndex == 0) {
            getMenuInflater().inflate(R.menu.main, menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void toggleFab() {
        if (navItemIndex == 0)
            mFab.show();
        else
            mFab.hide();
    }
}
