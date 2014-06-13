package info.srihawong.worldcup2014.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import info.srihawong.worldcup2014.app.Activity.AlertTimeActivity;

/**
 * Created by Banpot.S on 6/13/14 AD.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        /*Calendar now = Calendar.getInstance();
        DateFormat formatter = SimpleDateFormat.getTimeInstance();
        Toast.makeText(context, formatter.format(now.getTime()), Toast.LENGTH_SHORT).show();
        */
        Intent intentAlert = new Intent(context, AlertTimeActivity.class);
        intentAlert.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentAlert);

    }
}
