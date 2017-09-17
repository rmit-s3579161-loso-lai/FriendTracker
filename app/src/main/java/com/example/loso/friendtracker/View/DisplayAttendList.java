package com.example.loso.friendtracker.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.loso.friendtracker.Controller.AttendListAdapter;
import com.example.loso.friendtracker.Controller.MeetingController;
import com.example.loso.friendtracker.Controller.FriendController;
import com.example.loso.friendtracker.Model.Friend;
import com.example.loso.friendtracker.R;
import com.example.loso.friendtracker.Model.Meeting;

import java.util.ArrayList;

public class DisplayAttendList extends AppCompatActivity {
    private String meetingID = "";
    private MeetingController meetingController;
    private FriendController friendController;
    final static int ATTEND_CHANGED = 128;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_attends);

        // This activity interacts with one specific meetig
        // Grab meetig ID as passed as an extra in the intent
        meetingID = getIntent().getStringExtra("meeting");
        meetingController = new MeetingController();
        friendController = new FriendController();
        ArrayList<Friend> meeting_Attend = meetingController.getMeetingAttendees(meetingID);
        ArrayList<Friend> friendslist = friendController.getFriendsList();

        ArrayList<Friend> filtered = new ArrayList<Friend>();
        // filter friends
        for(int i=0 ; i<friendslist.size() ; i++)
        {
            boolean bSame = false;
            Friend friend = friendslist.get(i);
            for(int j=0 ; j<meeting_Attend.size() ; j++)
            {
                Friend attend = meeting_Attend.get(j);
                if(friend == attend) {
                    bSame = true;
                    break;
                }
            }

            if(!bSame)
                filtered.add(friend);
        }

        ListView friendsList = (ListView)findViewById(R.id.lvFriendsList);
        AttendListAdapter adapter = new AttendListAdapter(getApplicationContext(), filtered);
        friendsList.setAdapter(adapter);
    }
}