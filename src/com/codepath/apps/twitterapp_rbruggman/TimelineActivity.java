package com.codepath.apps.twitterapp_rbruggman;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {
	PullToRefreshListView lvTweets;
	TwitterClient client;
	public static final String SETTINGS_EXTRA = "settings";
	public static final int SETTINGS_REQUEST = 123;
	public static final String TWEET_EXTRA = "foo";
	Settings settings;
	TweetsAdapter adapter;
	String newTweet;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		settings = new Settings();
		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
		client = (TwitterClient) TwitterClient.getInstance(TwitterClient.class, this);
		
		// Set a listener to be invoked when the list should be refreshed.
        lvTweets.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list contents
                // Make sure you call listView.onRefreshComplete()
                // once the loading is done. This can be done from here or any
                // place such as when the network request has completed successfully.
            	Log.d("REFRESH", "refresh");
                fetchTimelineAsync(0);
            }
        });
		//Twiiter_Client_rbruggman_App.getRestClient()
		client.getHomeTimeline(0, new JsonHttpResponseHandler() {
			@Override
			// work with arraylist of tweets!
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				PullToRefreshListView lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
//				 cant use this because it refers to the inner class
				adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
				Log.d("DEBUG", jsonTweets.toString());
			}
		});
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    @Override
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
		        customLoadMoreDataFromApi(totalItemsCount); 
		    }
        });
	}
	
	 public void fetchTimelineAsync(int page) {
		 client.getHomeTimeline(0, new JsonHttpResponseHandler() {
        	
        	@Override
        	public void onSuccess(JSONArray json) {
        		adapter.clear();
                adapter.addAll(Tweet.fromJson(json));
            	Log.d("SUCCESS", "timeline: " + json.toString());
                lvTweets.onRefreshComplete();
            }

            public void onFailure(Throwable e, JSONArray json) {
            	Log.d("fail", "Fetch timeline error: " + e.toString());
                Log.d("#failwhale", "Fetch timeline error json: " + json.toString());
            }
        });
	 }
	 
	 public void customLoadMoreDataFromApi(int offset) {
			client.getHomeTimeline(1, new JsonHttpResponseHandler() {
				@Override
				public void onSuccess(JSONArray jsonTweets) {						
					adapter.addAll(Tweet.fromJson(jsonTweets));
					Log.d("DEBUG", jsonTweets.toString());
				}
			});

		}
	 
	 public void onPenPress(MenuItem item) {
		Toast.makeText(this, "Go Warrior!", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this, ComposeTweet.class);
		i.putExtra(TWEET_EXTRA, "bar");
		i.putExtra(SETTINGS_EXTRA, settings);
		startActivityForResult(i, SETTINGS_REQUEST);
	}
	
	 @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  // REQUEST_CODE is defined above
		  if (resultCode == RESULT_OK && requestCode == SETTINGS_REQUEST) {
		     // Extract name value from result extras
		     newTweet = data.getExtras().getString(TWEET_EXTRA);
		     Toast.makeText(this, newTweet, Toast.LENGTH_SHORT).show();
		     client.composeNewTweet(newTweet, new JsonHttpResponseHandler() {
		    		
		        	@Override
		        	public void onSuccess(JSONArray json) {
		            	Log.d("SUCCESS", "newTweet: " + json.toString());
		            }

		            public void onFailure(Throwable e, JSONArray json) {
		            	Log.d("fail", "Fetch timeline error: " + e.toString());
		                Log.d("#failwhale", "Fetch timeline error json: " + json.toString());
		            }
		     });
		    
		  }
	} 
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

}
