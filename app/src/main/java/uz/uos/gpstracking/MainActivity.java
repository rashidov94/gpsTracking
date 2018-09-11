package uz.uos.gpstracking;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);


        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, ToastBroadCastReceiver.class);


        boolean isWorking = (PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_NO_CREATE) != null);

        if (isWorking) {
            Log.d("MyLog", "Working");
        } else {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar cal_alarm = Calendar.getInstance();

            cal_alarm.set(Calendar.HOUR_OF_DAY, 9);
            cal_alarm.set(Calendar.MINUTE, 47);
            cal_alarm.set(Calendar.SECOND, 0);

            if (Build.VERSION.SDK_INT >= 23) {
                Log.d("MyLog", "23 api>= Activity");
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
            } else if (Build.VERSION.SDK_INT >= 19) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
            }
        }


    }
}
