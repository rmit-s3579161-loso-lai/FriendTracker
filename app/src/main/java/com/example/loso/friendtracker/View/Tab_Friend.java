package com.example.loso.friendtracker.View;

/**
 * Created by Loso on 2017/8/19.
 */
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

//import com.example.loso.friendtracker.Controller.DataManager;
import com.example.loso.friendtracker.R;

public class Tab_Friend extends Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_friend, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] items = {"AAA", "BBB", "CCC", "DDD", "EEE", "FFF"};
        ListView lvFriend = (ListView)rootView.findViewById(R.id.friendlist);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, items);
        lvFriend.setAdapter(adapter);
    }
}
