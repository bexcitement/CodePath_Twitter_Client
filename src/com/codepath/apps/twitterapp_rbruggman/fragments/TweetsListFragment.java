package com.codepath.apps.twitterapp_rbruggman.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.twitterapp_rbruggman.EndlessScrollListener;
import com.codepath.apps.twitterapp_rbruggman.OtherProfilesActivity;
import com.codepath.apps.twitterapp_rbruggman.ProfileActivity;
import com.codepath.apps.twitterapp_rbruggman.R;
import com.codepath.apps.twitterapp_rbruggman.TweetsAdapter;
import com.codepath.apps.twitterapp_rbruggman.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;

public class TweetsListFragment extends Fragment {
	TweetsAdapter adapter;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	TwitterClient client;
	
	@Override
	// must be defined for every fragment as this is where you inflate the fragment xml file
	public View onCreateView(LayoutInflater inf, ViewGroup parent, Bundle savedInstanceState) {
//		View view = inf.inflate(R.layout.fragments_tweet_list, parent, false);
//		ListView lvTweets = (ListView) view.findViewById(R.id.lvTweets);
//		lvTweets.setAdapter(getAdapter());
////		setListViewListeners();
//		return view;
		return inf.inflate(R.layout.fragments_tweet_list, parent,  false);
	}
	
	@Override
	// fired whenever the fragment is being displayed on screen with an activity
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
//		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
//		PullToRefreshListView lvTweets = (PullToRefreshListView) getActivity().findViewById(R.id.lvTweets);
		ListView lvTweets = (ListView) getActivity().findViewById(R.id.lvTweets);
//		 cant use this because it refers to the inner class
		adapter = new TweetsAdapter(getActivity(), tweets);
		lvTweets.setAdapter(adapter);
		client = (TwitterClient) TwitterClient.getInstance(TwitterClient.class, getActivity());
		// getActivity(); would allow you to have access to the activity that the fragment currently existed within
		
		lvTweets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				Intent i = new Intent(getActivity(), OtherProfilesActivity.class);
				Tweet thisTweet = tweets.get(position);
				i.putExtra("result", thisTweet);
				startActivity(i);	
			}

		});
		
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
		    public void onLoadMore(int page, int totalItemsCount) {
	                // Triggered only when new data needs to be appended to the list
	                // Add whatever code is needed to append new items to your AdapterView
		        customLoadMoreDataFromApi(page); 
		    }
        });
		
		
	}
	
	public void customLoadMoreDataFromApi(int offset) {
	 	Toast.makeText(getActivity(), "Pusheen!", Toast.LENGTH_SHORT).show();
		client.getHomeTimeline(offset, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {						
				adapter.addAll(Tweet.fromJson(jsonTweets));
//				Log.d("DEBUG", jsonTweets.toString());
			}
		});

	}
	
//	public void setOnItemClickListener(OnItemClickListener listener) {
//	    lvTweets.setOnItemClickListener(listener);
//	}
	
	public TweetsAdapter getAdapter() {
		return adapter;
	}

}
