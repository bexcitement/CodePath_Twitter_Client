package com.codepath.apps.twitterapp_rbruggman.fragments;

import org.json.JSONArray;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.twitterapp_rbruggman.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimelineFragment extends TweetsListFragment {
	TwitterClient client;
	
	public static UserTimelineFragment newInstance(String userName) {
		UserTimelineFragment fragmentDemo = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("someTitle", userName);
        fragmentDemo.setArguments(args);
        return fragmentDemo;
    }
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		client = (TwitterClient) TwitterClient.getInstance(TwitterClient.class, getActivity());
		String userName = getArguments().getString("someTitle", "");  
		super.onCreate(savedInstanceState);
		client.getUserTimeline(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				getAdapter().addAll(Tweet.fromJson(jsonTweets));
			}
		}, userName);
	}
}
