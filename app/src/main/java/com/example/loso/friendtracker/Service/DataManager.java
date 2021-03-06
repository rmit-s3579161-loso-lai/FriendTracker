package com.example.loso.friendtracker.Service;

/**
 * Created by Loso on 2017/8/19.
 * Repurposed by Lettisia George on 01/09/2017
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.loso.friendtracker.Model.Friend;
import com.example.loso.friendtracker.Model.FriendModel;
import com.example.loso.friendtracker.Model.Location;
import com.example.loso.friendtracker.Model.Meeting;
import com.example.loso.friendtracker.Model.MeetingModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DataManager {
    private static final String LOG_TAG = DummyLocationService.class.getName();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static ArrayList<Friend> createDummyFriendList(Context context) {
        //scan location first
        DummyLocationService dummyLocationService = DummyLocationService.getSingletonInstance(context);
        //dummyLocationService.logAll();

        //create dummy friend list
        ArrayList<Friend> friends = new ArrayList<Friend>();
        for (int i = 1; i < 5; i++) {
            String id = MeetingModel.createID();
            String name = "Friend" + i;
            String email = "Email" + i;
            Date birthday = new GregorianCalendar(1985 + i, (i + 5) % 12, i % 28).getTime();

            //get dummy location
            Location location = null;
            try {
                //Log.d(LOG_TAG, Calendar.getInstance().getTime());
                location = getFriendLocation(context, name, Calendar.getInstance().getTime());
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());
            }

            friends.add(new Friend(id, name, email, birthday, location));
        }
        return friends;
    }

    public static ArrayList<Meeting> createDummMeetingList() {
        ArrayList<Meeting> meetings = new ArrayList<Meeting>();
        FriendModel mFriendModel = FriendModel.getInstance();
        for (int i = 0; i < 3; i++) {
            String title = "Meeting" + Integer.toString(i);
            ArrayList<Friend> friends = new ArrayList<>();
            friends.add(mFriendModel.getFriends().get(i));
            Meeting item = new Meeting(MeetingModel.createID(), title, friends, null);
            Calendar cal = Calendar.getInstance();
            long time = cal.getTime().getTime();
            time += i * 5 * 60 * 1000; // 5mins
            cal.setTimeInMillis(time);
            item.setStartDate(cal.getTime());
            meetings.add(item);
        }
        return meetings;
    }

    public static Location getFriendLocation(Context context, String name, Date time) {
        List<DummyLocationService.FriendLocation> list = getFriendLocationsForTime(context, name, time, 1, 0);
        return grabFriendLocation(name, list);
    }

    private static Location grabFriendLocation(String name, List<DummyLocationService.FriendLocation> locs) {
        Location found = null;
        for (DummyLocationService.FriendLocation fl : locs) {
            //Log.d(LOG_TAG, fl.name + "=" + name);
            if (fl.name.equals(name)) {
                found = new Location(fl.time, fl.latitude, fl.longitude);
            }
        }
        return found;
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static List<DummyLocationService.FriendLocation> getFriendLocationsForTime(Context context, String name, Date time, int periodMinutes, int periodSeconds) {
        DummyLocationService dummyLocationService = DummyLocationService.getSingletonInstance(context);
        List<DummyLocationService.FriendLocation> matched = null;
        // 2 mins either side of 9:46:30 AM
        try {
            matched = dummyLocationService.getFriendLocationsForTime(time, 2, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (matched.size() > 0) {
            //Log.i(LOG_TAG, "Matched Query:");
            dummyLocationService.log(matched);
        }
        return matched;
    }
}