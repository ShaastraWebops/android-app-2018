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
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import webops.shaastra.iitm.shaastra2018.R;
import webops.shaastra.iitm.shaastra2018.fragments.EventAboutFragment;
import webops.shaastra.iitm.shaastra2018.fragments.Homefragment;
import webops.shaastra.iitm.shaastra2018.objects.EventObject;

public class EventInfoActivity extends AppCompatActivity implements EventAboutFragment.OnFragmentInteractionListener {

    private EventObject eventObject;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private EventPagerAdapter pagerAdapter;
    private JSONObject about,reg,contact,prize,tdp,venue;
    private JSONArray otherinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        eventObject = (EventObject) args.getParcelable("eventinfo");

        TextView tv_event_name = (TextView)findViewById(R.id.tv_eventName);
        TextView tv_event_date = (TextView)findViewById(R.id.tv_date);
        TextView tv_event_venue = (TextView)findViewById(R.id.tv_venue);

        viewPager = (ViewPager)findViewById(R.id.container);
        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.GRAVITY_CENTER);

        setTitle("EventInfo");

        tv_event_name.setText(eventObject.name);
        tv_event_venue.setText(eventObject.venue);
        tv_event_date.setText(eventObject.eventDate);

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
            reg.put("startReg",eventObject.startReg);
            reg.put("endReg",eventObject.endReg);
            reg.put("paidEvent",eventObject.paidEvent);
            reg.put("fees",eventObject.fees);
//            reg.put("limitteam",eventObject.limitteam);
            reg.put("max",eventObject.maxTeamMembers);
            reg.put("min",eventObject.minTeamMembers);
            reg.put("eventCag",eventObject.eventCategory);
            reg.put("eventDate",eventObject.eventDate);

            contact.put("email",eventObject.contactemail);
            contact.put("contacts",eventObject.contact);

            prize.put("prize",eventObject.prizedetails);

            tdp.put("tdp",eventObject.tdpdetails);
            if(eventObject.otherinfo!=null)
                otherinfo = new JSONArray(eventObject.otherinfo);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
