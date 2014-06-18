package info.srihawong.worldcup2014.app.Activity;

import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.Tracker;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


import info.srihawong.worldcup2014.app.Config;
import info.srihawong.worldcup2014.app.R;


public class AlertTimeActivity extends ActionBarActivity {

    public static InterstitialAd interstitialAds;
    public AdRequest adRequest;
    public static Tracker gaTracker;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_time);

        mediaPlayer = MediaPlayer.create(this, R.raw.worldcup);
        mediaPlayer.seekTo(0);
        mediaPlayer.setLooping(true);
        //mediaPlayer.setVolume(100,100);
        mediaPlayer.start();

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

    }

    @Override
    protected void onPause() {
        mediaPlayer.stop();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        if(interstitialAds.isLoaded()) {
            interstitialAds.show();
        }
        mediaPlayer.stop();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.alert_time, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onStart() {
        super.onStart();
        //ActionBarActivity activity
        //EasyTracker.getInstance(getApplicationContext()).activityStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        //EasyTracker.getInstance(getApplicationContext()).activityStop(this);
    }
}
