package com.codepath.apps.twitterapp_rbruggman.fragments;

import org.json.JSONArray;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.twitterapp_rbruggman.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class HomeTimelineFragment extends TweetsListFragment {
	TwitterClient client;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		client = (TwitterClient) TwitterClient.getInstance(TwitterClient.class, getActivity());
		
		super.onCreate(savedInstanceState);
		client.getHomeTimeline(0, new JsonHttpResponseHandler() {
			@Override

			public void onSuccess(JSONArray jsonTweets) {
				getAdapter().addAll(Tweet.fromJson(jsonTweets));
			}
		});
	}
}
