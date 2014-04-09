package com.codepath.apps.twitterapp_rbruggman;

import org.json.JSONArray;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.twitterapp_rbruggman.fragments.HomeTimelineFragment;
import com.codepath.apps.twitterapp_rbruggman.fragments.MentionsFragment;
import com.codepath.apps.twitterapp_rbruggman.fragments.TweetsListFragment;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;

// Every activity that is going to have fragments embedded 
public class TimelineActivity extends FragmentActivity implements TabListener {
//	PullToRefreshListView lvTweets;
	ListView lvTweets;
	TwitterClient client;
	public static final String SETTINGS_EXTRA = "settings";
	public static final int SETTINGS_REQUEST = 123;
	public static final String TWEET_EXTRA = "foo";
	Settings settings;
	TweetsAdapter adapter;
	String newTweet;
	TweetsListFragment fragmentTweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupNavigationTabs();
		// gives us access to our fragment
		// always need to use the getsupportfrgamentmanager when accessing a fragment
//		fragmentTweets = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentTweets);
		
		settings = new Settings();
//		lvTweets = (PullToRefreshListView) findViewById(R.id.lvTweets);
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		client = (TwitterClient) TwitterClient.getInstance(TwitterClient.class, this);
	}
	
	 private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		Tab tabHome = actionBar.newTab().setText("Home").setTag("HomeTimeLineFragment").setIcon(R.drawable.ic_home).setTabListener(this);
		Tab tabMentions = actionBar.newTab().setText("Mentions").setTag("MentionsTimeLineFragment").setIcon(R.drawable.ic_mentions).setTabListener(this);
		
		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
	}
	 
	 public void onPenPress(MenuItem item) {
		Toast.makeText(this, "Go Warrior!", Toast.LENGTH_SHORT).show();
		Intent i = new Intent(this, ComposeTweet.class);
		i.putExtra(TWEET_EXTRA, "bar");
		i.putExtra(SETTINGS_EXTRA, settings);
		startActivityForResult(i, SETTINGS_REQUEST);
	}
	 
	 public void onProfileView(MenuItem mi) {
			Intent i = new Intent(this, ProfileActivity.class);
			startActivity(i);
	} 
	 
	 @Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  // REQUEST_CODE is defined above
		  if (resultCode == RESULT_OK && requestCode == SETTINGS_REQUEST) {
		     // Extract name value from result extras
		     newTweet = data.getExtras().getString(TWEET_EXTRA);
		     final Tweet thisTweet = (Tweet) data.getSerializableExtra(SETTINGS_EXTRA);
		     
		     Toast.makeText(this, newTweet, Toast.LENGTH_SHORT).show();
		     client.composeNewTweet(newTweet, new JsonHttpResponseHandler() {
		    		
		        	@Override
		        	public void onSuccess(JSONArray json) {
		        		adapter.insert(thisTweet, 0);
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

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		FragmentManager manager = getSupportFragmentManager();
		// sets up the transaction to be able to make changes to the fragments on screen
		android.support.v4.app.FragmentTransaction fts = manager.beginTransaction();
		if(tab.getTag() == "HomeTimeLineFragment") {
			// setting the fragment to home timeline
			// give replace an ID and a fragment
			// it will take care of creating teh fragment for you and putting it into frame layout
			fts.replace(R.id.frameContainer, new HomeTimelineFragment());
		} else {
			// setting the fragment to the mentions timeline
			fts.replace(R.id.frameContainer, new MentionsFragment());
		}
		// actually makes the changes you have described
		fts.commit();
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

}
