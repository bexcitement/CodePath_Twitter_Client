package com.codepath.apps.twitterapp_rbruggman.fragments;

import org.json.JSONArray;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.twitterapp_rbruggman.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;

// extending tweetsListFragment gives full access to the adapter, listview, etc.
public class MentionsFragment extends TweetsListFragment {
	TwitterClient client;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		client = (TwitterClient) TwitterClient.getInstance(TwitterClient.class, getActivity());
		
		super.onCreate(savedInstanceState);
		client.getMentions(new JsonHttpResponseHandler() {
			
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				getAdapter().addAll(Tweet.fromJson(jsonTweets));
			}
		});
	}
}
