package webops.shaastra.iitm.shaastra2018.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import webops.shaastra.iitm.shaastra2018.R;
import webops.shaastra.iitm.shaastra2018.fragments.EventAboutFragment;
import webops.shaastra.iitm.shaastra2018.fragments.Homefragment;
import webops.shaastra.iitm.shaastra2018.fragments.Mapfragment;
import webops.shaastra.iitm.shaastra2018.objects.EventObject;

public class EventInfoActivity extends AppCompatActivity implements EventAboutFragment.OnFragmentInteractionListener,View.OnClickListener {

    private EventObject eventObject;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private EventPagerAdapter pagerAdapter;
    private JSONObject about,reg,contact,prize,tdp,venue;
    private JSONArray otherinfo;
    private String eventDate,startReg,endReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        eventObject = (EventObject) args.getParcelable("eventinfo");
        String vertical_name = args.getString("vertical");
        TextView tv_event_name = (TextView)findViewById(R.id.tv_eventName);
        TextView tv_event_date = (TextView)findViewById(R.id.tv_date);
        TextView tv_event_venue = (TextView)findViewById(R.id.tv_venue);

        viewPager = (ViewPager)findViewById(R.id.container);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        setTitle(vertical_name);

        tv_event_name.setText(eventObject.name);
        tv_event_venue.setText(eventObject.venue);

        DateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        Date date1 = null;
        Date date2 = null;
        Date date3 = null;
        try {
            date1 = inputFormat.parse(eventObject.eventDate);
            eventDate = outputFormat.format(date1);
            tv_event_date.setText(eventDate);

            date2= inputFormat.parse(eventObject.startReg);
            startReg = outputFormat.format(date2);

            date3 = inputFormat.parse(eventObject.endReg);
            endReg = outputFormat.format(date3);

        } catch (ParseException e) {
            e.printStackTrace();
            eventDate = "Error Occured";
            startReg = "Error Occured";
            endReg = "Error Occured";
        }

//        formatter = new SimpleDateFormat("EEEE MMMM dd, yyyy");
//        s = formatter.format(eventObject.eventDate);


        about = new JSONObject();
        reg = new JSONObject();
        contact = new JSONObject();
        prize = new JSONObject();
        venue = new JSONObject();
        tdp = new JSONObject();

        try {
            about.put("info",eventObject.info);
            about.put("rounds",eventObject.rounds);

            reg.put("reg",eventObject.registration);
            reg.put("startReg",startReg);
            reg.put("endReg",endReg);
            reg.put("paidEvent",eventObject.paidEvent);
            reg.put("fees",eventObject.fees);
//            reg.put("limitteam",eventObject.limitteam);
            reg.put("max",eventObject.maxTeamMembers);
            reg.put("min",eventObject.minTeamMembers);
            reg.put("eventCag",eventObject.eventCategory);
            reg.put("eventDate",eventDate);

            contact.put("email",eventObject.contactemail);
            contact.put("contacts",eventObject.contact);

            prize.put("prize",eventObject.prizedetails);

            tdp.put("tdp",eventObject.tdpdetails);
            if(eventObject.otherinfo!=null)
                otherinfo = new JSONArray(eventObject.otherinfo);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        LinearLayout locate,call,share,bookmark;
        locate =(LinearLayout)findViewById(R.id.locationLayout);
        call =(LinearLayout)findViewById(R.id.callLayout);
        share =(LinearLayout)findViewById(R.id.shareLayout);
        bookmark =(LinearLayout)findViewById(R.id.bookmarkLayout);

        locate.setOnClickListener(this);
        call.setOnClickListener(this);
        share.setOnClickListener(this);
        bookmark.setOnClickListener(this);

        callviewpager();
    }




    public  void callviewpager(){
        setupViewPager(viewPager);
        if (tabLayout != null) {
            tabLayout.setupWithViewPager(viewPager);
            tabLayout.setVisibility(View.VISIBLE);
        }
    }

    public void setupViewPager(final ViewPager mviewPager) {

        pagerAdapter = new EventPagerAdapter(getSupportFragmentManager());

        pagerAdapter.addFragment(EventAboutFragment.newInstance(0,about.toString()),"About");
        pagerAdapter.addFragment(EventAboutFragment.newInstance(1,reg.toString()),"Registration Details");
        pagerAdapter.addFragment(EventAboutFragment.newInstance(2,contact.toString()),"Contact");
        pagerAdapter.addFragment(EventAboutFragment.newInstance(3,prize.toString()),"Prize Details");
        if(eventObject.requireTDP){
            pagerAdapter.addFragment(EventAboutFragment.newInstance(4,tdp.toString()),"TDP");
        }
        if(otherinfo!=null && otherinfo.length()>0){
            for (int i=0;i<otherinfo.length();i++){
                try {
                    JSONObject tabInfo = (otherinfo.getJSONObject(i));
                    pagerAdapter.addFragment(EventAboutFragment.newInstance(5,tabInfo.toString()),tabInfo.getString("title"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        pagerAdapter.addFragment(EventAboutFragment.newInstance(6,venue.toString()),"Venue & Timings");

        mviewPager.setAdapter(pagerAdapter);
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.locationLayout:
                Mapfragment mapfragment = new Mapfragment();
                Bundle bundle = new Bundle();
                bundle.putString("loc",eventObject.venue);
                bundle.putDouble("lat",12.992964);
                bundle.putDouble("lng",80.232450);
                mapfragment.setArguments(bundle);
                break;
            case R.id.callLayout:
                break;
            case R.id.shareLayout:
                break;
            case R.id.bookmarkLayout:
                break;

        }
    }

    public class EventPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public EventPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            try {
                super.finishUpdate(container);
            } catch (NullPointerException nullPointerException) {
                System.out.println("Catch the NullPointerException in FragmentPagerAdapter.finishUpdate");
            }
        }

    }
}
