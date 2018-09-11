package uz.uos.gpstracking;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class ToastBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("MyLog", "onReceive");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        pendingIntent.cancel();

        boolean isWorking = (PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);
        if (isWorking) {
            Log.d("MyLog", "Working " + "broadcast");
        } else {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            Intent intent1 = new Intent(context, ToastBroadCastReceiver.class);
            PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            Calendar cal_alarm = Calendar.getInstance();

            Calendar tmp = (Calendar) cal_alarm.clone();
            tmp.add(Calendar.MINUTE, 15);

            if (Build.VERSION.SDK_INT >= 23) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, tmp.getTimeInMillis(), pendingIntent1);
            } else if (Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, 15 * 60 * 1000, pendingIntent1);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, 15 * 60 * 1000, pendingIntent1);
            }

        }


        if (!isServiceRunning(context)) {
            Intent serviceIntent = new Intent(context, GpsService.class);
            context.startService(serviceIntent);
        } else
            Log.d("MyLog", "Service runningggggggggggggggggggggg");

    }


    private boolean isServiceRunning(Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (GpsService.class.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}