package webops.shaastra.iitm.shaastra2018.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import webops.shaastra.iitm.shaastra2018.R;
import webops.shaastra.iitm.shaastra2018.fragments.Eventsfragment;
import webops.shaastra.iitm.shaastra2018.fragments.Homefragment;
import webops.shaastra.iitm.shaastra2018.fragments.Hospitalityfragment;
import webops.shaastra.iitm.shaastra2018.fragments.Logoutfragment;
import webops.shaastra.iitm.shaastra2018.fragments.Mapfragment;
import webops.shaastra.iitm.shaastra2018.fragments.Showsfragment;
import webops.shaastra.iitm.shaastra2018.fragments.Sponsorsfragment;
import webops.shaastra.iitm.shaastra2018.fragments.Spotlightfragment;
import webops.shaastra.iitm.shaastra2018.fragments.Summitfragment;
import webops.shaastra.iitm.shaastra2018.fragments.UserProfilefragment;
import webops.shaastra.iitm.shaastra2018.fragments.Workshopsfragment;

public class NavigationActivity extends AppCompatActivity  implements Homefragment.OnFragmentInteractionListener{

    private  NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    //qr code scanner object
    public static IntentIntegrator qrScan;
    PopupWindow popUpWindow;
    CardView containerLayout;

    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static  String TAG_HOME ;
    private static  String TAG_Userprofile ;
    private static  String TAG_Events ;
    private static  String TAG_Workshop;
    private static  String TAG_Summit ;
    private static  String TAG_Spotlight;
    private static  String TAG_Shows ;
    private static  String TAG_Hospi ;
    private static  String TAG_Map ;
    private static  String TAG_Spons;
    private static  String TAG_Qrscan ;
    private static  String TAG_logout ;

    public static String CURRENT_TAG = TAG_HOME;

    // toolbar titles respected to selected nav menu item
    private static String[] fragmenttitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TAG_HOME = getResources().getString(R.string.nav_home);;
        TAG_Userprofile = getResources().getString(R.string.nav_userProfile);
        TAG_Events =  getResources().getString(R.string.nav_events);
        TAG_Workshop = getResources().getString(R.string.nav_workshops);
        TAG_Summit = getResources().getString(R.string.nav_summit);
        TAG_Spotlight =  getResources().getString(R.string.nav_spotlight);
        TAG_Shows = getResources().getString(R.string.nav_show);
        TAG_Hospi =getResources().getString(R.string.nav_hospi);
        TAG_Map = getResources().getString(R.string.nav_map);
        TAG_Spons= getResources().getString(R.string.nav_spons) ;
        TAG_Qrscan = getResources().getString(R.string.nav_qr) ;
        TAG_logout = getResources().getString(R.string.nav_logout);

        mHandler = new Handler();
        fragmenttitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // initializing navigation menu
        navigationView = (NavigationView)findViewById(R.id.nav_view);
        setUpNavigationView();


        containerLayout = (CardView) findViewById(R.id.cv_popup);
        popUpWindow = new PopupWindow(NavigationActivity.this);
        popUpWindow.setContentView(containerLayout);

        //intializing scan object
        qrScan = new IntentIntegrator(this);

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }


    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        final String activity_name;
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                //converting the data to json
                //setting values to activity_variable
                activity_name = String.valueOf(result.getContents());
                initiatePopupWindow(navigationView,activity_name);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initiatePopupWindow(final View v, final String qr_response) {

        try {
            //We need to get the instance of the LayoutInflater, use the context of this activity
            popUpWindow.setTouchable(true);
            popUpWindow.setFocusable(true);

            LayoutInflater inflater = (LayoutInflater)NavigationActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Inflate the view from a predefined XML layout
            View layout = inflater.inflate(R.layout.popup,
                    (ViewGroup) findViewById(R.id.cv_popup));

            final Button bt_dismiss_pop,bt_go;
            TextView tv_qr_response;

            tv_qr_response = (TextView) layout.findViewById(R.id.tv_qr_response);
            bt_dismiss_pop = (Button)layout.findViewById(R.id.bt_pop_dismiss);
            bt_go = (Button)layout.findViewById(R.id.bt_go);

            tv_qr_response.setText(qr_response);

            popUpWindow = new PopupWindow(layout,CardView.LayoutParams.WRAP_CONTENT,CardView.LayoutParams.WRAP_CONTENT,true);
            // display the popup in the center
            new Handler().postDelayed(new Runnable(){

                public void run() {
                    popUpWindow.showAtLocation( v, Gravity.CENTER, 0, 0);
                }

            }, 200L);

            bt_dismiss_pop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popUpWindow.dismiss();


                }
            });
            bt_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent navigate = new Intent(MainActivity.this,Main2Activity.class);
                    //navigate.putExtra("Activityname",qr_response);
                    //startActivity(navigate);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {

        // set toolbar title
        setToolbarTitle();
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        if(navItemIndex!=10){
            Runnable mPendingRunnable = new Runnable() {
                @Override
                public void run() {
                    // update the main content by replacing fragments
                    Fragment fragment = getHomeFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
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
        }
        //Closing drawer on item click
        drawer.closeDrawers();
    }

    private Fragment getHomeFragment() {

        switch (navItemIndex) {
            case 0:
                Homefragment homeFragment = new Homefragment();
                return homeFragment;
            case 1:
                UserProfilefragment userProfilefragment = new UserProfilefragment();
                return userProfilefragment;
            case 2:
                Eventsfragment eventsfragment = new Eventsfragment();
                return eventsfragment;
            case 3:
                Workshopsfragment workshopsfragment = new Workshopsfragment();
                return workshopsfragment;
            case 4:
                Summitfragment summitfragment = new Summitfragment();
                return summitfragment;
            case 5:
                Spotlightfragment spotlightfragment = new Spotlightfragment();
                return spotlightfragment;
            case 6:
                Showsfragment showsfragment = new Showsfragment();
                return showsfragment;
            case 7:
                Hospitalityfragment hospitalityfragment = new Hospitalityfragment();
                return hospitalityfragment;
            case 8:
                Mapfragment mapfragment = new Mapfragment();
                Bundle bundle = new Bundle();
                bundle.putString("loc","KV Grounds RegistrationDesk");
                bundle.putDouble("lat",12.992964);
                bundle.putDouble("lng",80.232450);
                mapfragment.setArguments(bundle);
                return mapfragment;
            case 9:
                Sponsorsfragment sponsorsfragment = new Sponsorsfragment();
                return sponsorsfragment;
            case 10:
                    return new Homefragment();
            case 11:
                Logoutfragment logoutfragment = new Logoutfragment();
                return logoutfragment;
            default:
                return new Homefragment();
        }
    }

    private void setToolbarTitle() {
        if(navItemIndex==10){
            getSupportActionBar().setTitle(fragmenttitles[0]);
        }else{
            getSupportActionBar().setTitle(fragmenttitles[navItemIndex]);
        }
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    int id = menuItem.getItemId();

                    if(id == R.id.nav_home) {

                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;

                    }else if (id == R.id.nav_userprofile) {

                        navItemIndex = 1;
                        CURRENT_TAG = TAG_Userprofile;

                    }else if (id == R.id.nav_events) {

                        navItemIndex = 2;
                        CURRENT_TAG = TAG_Events;

                    }else if (id == R.id.nav_workshops) {

                        navItemIndex = 3;
                        CURRENT_TAG = TAG_Workshop;

                    }else if (id == R.id.nav_summit) {

                        navItemIndex = 4;
                        CURRENT_TAG = TAG_Summit;

                    }else if (id == R.id.nav_spotlight) {

                        navItemIndex = 5;
                        CURRENT_TAG = TAG_Spotlight;

                    }else if (id == R.id.nav_shows) {

                        navItemIndex = 6;
                        CURRENT_TAG = TAG_Shows;

                    }else if (id == R.id.nav_hospitality) {

                        navItemIndex = 7;
                        CURRENT_TAG = TAG_Hospi;

                    }else if (id == R.id.nav_map) {

                        navItemIndex = 8;
                        CURRENT_TAG = TAG_Map;

                    }else if (id == R.id.nav_sponsors) {

                        navItemIndex = 9;
                        CURRENT_TAG = TAG_Spons;

                    }else if (id == R.id.nav_qrscanner) {

                        navItemIndex = 10;
                        CURRENT_TAG = TAG_Qrscan;
                        qrScan.initiateScan();

                        //MainActivity.qrScan.initiateScan();

                    }else if (id == R.id.nav_logout) {

                        navItemIndex = 11;
                        CURRENT_TAG = TAG_logout;

                    }
                    else {
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                    }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);
                    loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

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

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                navigationView.getMenu().getItem(navItemIndex).setChecked(true);
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 0)
            fab.show();
        else
            fab.hide();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}
