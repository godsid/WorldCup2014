package info.srihawong.worldcup2014.app.Activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.GoogleAnalytics;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import info.srihawong.worldcup2014.app.AlarmReceiver;
import info.srihawong.worldcup2014.app.Config;
import info.srihawong.worldcup2014.app.MatchAdapter;
import info.srihawong.worldcup2014.app.Object.Match;
import info.srihawong.worldcup2014.app.Object.ThaiDate;
import info.srihawong.worldcup2014.app.R;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    private PendingIntent mAlarmIntent;
    public static InterstitialAd interstitialAds;
    public AdRequest adRequest;
    public static Tracker gaTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*******************************************************/
        //Create the interstitial Ads.
        interstitialAds = new InterstitialAd(this);
        interstitialAds.setAdUnitId(Config.adsInterstitialUnitIDFullPage);
        interstitialAds.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                //super.onAdLoaded();
                Log.d("tui", "interstitial Ads Loaded");
            }

            @Override
            public void onAdClosed() {
                Log.d("tui","interstitial Ads Close");
                interstitialAds.loadAd(adRequest);
                //super.onAdClosed();
            }
        });

        // Create ad request.
        adRequest = new AdRequest.Builder()
                .addKeyword(Config.adsInterstitialKeyword)
                .build();

        // Begin loading your interstitial.
        interstitialAds.loadAd(adRequest);
        /*******************************************************/

        //gaTracker = GoogleAnalytics.getInstance(this).newTracker(R.string.ga_trackingId);


        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onBackPressed() {
        if(interstitialAds.isLoaded()) {
            interstitialAds.show();
        }
        super.onBackPressed();
    }
    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(getApplicationContext()).activityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(getApplicationContext()).activityStop(this);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        AQuery aq;
        MatchAdapter matchListAdapter;
        ArrayList<Match> matchArrayList;
        JSONArray matchDate;
        LinearLayout dateLayout;
        GridView matchGridView;
        Spinner dateSpinner;

        ArrayList<String> listSpinner;
        ArrayAdapter<String> arrayAdapter;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER,0);
            View rootView;
            aq = new AQuery(getActivity().getApplicationContext());
            //Log.d("tui",String.valueOf(sectionNumber));
            switch(sectionNumber){

                case 2:
                    rootView = inflater.inflate(R.layout.fragment_2nd_stage, container, false);
                    fragment_2nd_stage(rootView);
                    break;
                case 3:
                    rootView = inflater.inflate(R.layout.fragment_groups, container, false);
                    fragment_groups(rootView);
                    break;
                case 1:
                default:
                    rootView = inflater.inflate(R.layout.fragment_matchs, container, false);
                    fragment_matchs(rootView);
                    break;
            }
            return rootView;
        }

        private View fragment_matchs(final View rootView){
            matchGridView  = (GridView)rootView.findViewById(R.id.matchGridView);
            //dateLayout = (LinearLayout)rootView.findViewById(R.id.dateLayout);
            dateSpinner = (Spinner)rootView.findViewById(R.id.dateSpinner);
            //HorizontalScrollView dateHorScrollView = (HorizontalScrollView)rootView.findViewById(R.id.dateHorScrollView);
            //GridView dateListView = (GridView)rootView.findViewById(R.id.dataListView);
            matchArrayList = new ArrayList<Match>();
            matchListAdapter = new MatchAdapter(getActivity().getBaseContext(),matchArrayList);
            matchGridView.setAdapter(matchListAdapter);
            listSpinner = new ArrayList<String>();
            arrayAdapter = new ArrayAdapter<String>(rootView.getContext(),android.R.layout.simple_list_item_1,listSpinner);
            dateSpinner.setAdapter(arrayAdapter);
            dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("tui",String.valueOf(position));
                    drawMatch(position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            matchGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Match selectItem = matchListAdapter.getItem(position);
                    if(selectItem.isAlarm()){
                        matchArrayList.get(position).setAlarm(false);
                        deleteAlarm(selectItem.getTimestamp());
                        //cancelAlarm(new Date().getTime()+10000);
                        Toast.makeText(getActivity().getBaseContext(),"ยกเลิกการแจ้งเตือนแล้ว",Toast.LENGTH_SHORT).show();
                    }else{
                        matchArrayList.get(position).setAlarm(true);
                        setAlarm(selectItem.getTimestamp());
                        addAlarm(selectItem.getTimestamp());
                        Toast.makeText(getActivity().getBaseContext(),"เริ่มกำหนดเวลาแจ้งเตือนแล้ว "+ThaiDate.format(Config.dateTimeFormat),Toast.LENGTH_SHORT).show();
                    }
                    matchListAdapter.notifyDataSetChanged();
                }
            });

            String apiURL = Config.apiURL;
            Log.d("tui","call:"+apiURL);

            aq.ajax(apiURL, JSONArray.class,Config.apiCacheTime,new AjaxCallback<JSONArray>(){
                @Override
                public void callback(String url, JSONArray object, AjaxStatus status) {
                    matchDate = object;
                    Log.d("tui","msg:"+status.getMessage()+" callback:"+url);
                    //super.callback(url, object, status);
                    if(object!=null){
                        JSONObject matchDate;
                        JSONArray matchTeams;
                        JSONObject matchTeam;
                        Log.d("tui",String.valueOf(object.length()));
                        for(int i=0,j=object.length();i<j;i++){
                            try {
                                matchDate = object.getJSONObject(i);
                                matchTeams = matchDate.getJSONArray("match");
                                //TextView dateTextView = new TextView(rootView.getContext());
                                listSpinner.add(ThaiDate.format(matchDate.getLong("timestamps")*1000, Config.dateFormat));
                                if((ThaiDate.format(matchDate.getLong("timestamps")*1000,Config.dateFormat).equals(ThaiDate.format(Config.dateFormat)))) {
                                    dateSpinner.setSelection(i);
                                    drawMatch(i);
                                }else{
                                    //dateSpinner.getSelectedItemPosition();
                                }
                                //dateTextView.setText(ThaiDate.format(matchDate.getLong("timestamps")*1000, Config.dateFormat));
                                //dateLayout.addView(dateTextView);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            });

            return rootView;
        }
        private void drawMatch(int position){
            JSONArray matchTeams = null;
            JSONObject matchTeam = null;
            try {
                matchArrayList.clear();
                matchTeams = matchDate.getJSONObject(position).getJSONArray("match");
                for(int k=0,l=matchTeams.length();k<l;k++){
                    matchTeam = matchTeams.getJSONObject(k);
                    long timestamp = matchTeam.getLong("times")*1000;

                    JSONArray channalJson =  matchTeam.getJSONArray("chanel");
                    String[] channalArr = new String[channalJson.length()];

                    for(int i=0,j=channalJson.length();i<j;i++){
                        channalArr[i] = channalJson.getString(i);
                    }
                    matchArrayList.add(new Match(timestamp,
                            matchTeam.getString("name"),
                            matchTeam.getJSONObject("team1").getString("name"),
                            matchTeam.getJSONObject("team2").getString("name"),
                            channalArr,
                            checkAlarm(timestamp)));
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("tui","Json error"+e.getMessage());
            }
            matchListAdapter.notifyDataSetChanged();
        }
        private Boolean checkAlarm(long timestamp){
            SharedPreferences sharedPref = getActivity().getSharedPreferences(Config.SHAREDPREF_ALARM,Context.MODE_PRIVATE);
            String alarmData = sharedPref.getString(Config.SHAREDPREF_ALARM_DATA,"");
            return alarmData.contains(String.valueOf(timestamp)+"|");
        }
        private void setAlarm(long timestamp){
            SharedPreferences sharedPref = getActivity().getSharedPreferences(Config.SHAREDPREF_ALARM,Context.MODE_PRIVATE);
            String alarmData = sharedPref.getString(Config.SHAREDPREF_ALARM_DATA,"");
            alarmData+=String.valueOf(timestamp)+"|";
            sharedPref.edit().putString(Config.SHAREDPREF_ALARM_DATA,alarmData).commit();
        }
        private void deleteAlarm(long timestamp){
            SharedPreferences sharedPref = getActivity().getSharedPreferences(Config.SHAREDPREF_ALARM,Context.MODE_PRIVATE);
            String alarmData = sharedPref.getString(Config.SHAREDPREF_ALARM_DATA,"");
            alarmData = alarmData.replace(String.valueOf(timestamp)+"|","");
            sharedPref.edit().putString(Config.SHAREDPREF_ALARM_DATA,alarmData).commit();
        }
        private void addAlarm(long timestamp){
            Context context = getActivity().getBaseContext();
            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,(int)timestamp/1000, intent, 0);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);
        }

        private void cancelAlarm(long timestamp){
            Context context = getActivity().getBaseContext();
            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,(int)timestamp/1000, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, timestamp, pendingIntent);



        }

        private View fragment_groups(View view){
            return view;
        }
        private View fragment_2nd_stage(View view){

            return view;
        }
    }

}
