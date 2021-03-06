package com.example.loso.friendtracker.Alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.example.loso.friendtracker.Controller.MeetingController;
import com.example.loso.friendtracker.Controller.PreferenceController;
import com.example.loso.friendtracker.Model.Meeting;

/**
 * Created by Loso on 2017/10/7.
 */

public class ActionSnoozeReminderActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //cancel first
        Intent intent = new Intent(this, AlarmNotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, AlarmNotificationReceiver.ALARM_NOTIFY_ID, intent, 0);
        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);

        pendingIntent = PendingIntent.getBroadcast(this, AlarmNotificationReceiver.ALARM_NOTIFY_ID, intent, 0);
        //get time limit value
        PreferenceController preferenceController = PreferenceController.getInstance();
        long snooze = System.currentTimeMillis() + (preferenceController.getSnooze() * 60 * 1000);

        //get upcoming
        MeetingController meetingController = new MeetingController();
        Meeting meeting = meetingController.getUpcommingMeeting();
        long meetingStartTime = meeting.getStartDate().getTime();

        if(meetingStartTime <= snooze)
            alarmManager.set(AlarmManager.RTC_WAKEUP, meetingStartTime, pendingIntent);
        else
            alarmManager.set(AlarmManager.RTC_WAKEUP, snooze, pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(AlarmNotificationReceiver.ALARM_NOTIFY_ID);
        finish(); // since finish() is called in onCreate(), onDestroy() will be called immediately
    }
}
