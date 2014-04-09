package com.codepath.apps.twitterapp_rbruggman;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.twitterapp_rbruggman.fragments.UserTimelineFragment;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class OtherProfilesActivity extends FragmentActivity {
	TwitterClient client;
	Tweet thisTweet;
	String userName;
	String tweetBody;
	String tagLine;
	String OtherProfileUrl;
	User thisUser;
	int otherFollowing;
	int otherFollowers;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_other_profiles);
		client = (TwitterClient) TwitterClient.getInstance(TwitterClient.class, this);
		thisTweet = (Tweet) getIntent().getSerializableExtra("result");
		
		TextView tvOtherName = (TextView) findViewById(R.id.tvOtherName);
		TextView tvOtherTagline = (TextView) findViewById(R.id.tvOtherTagline);
		TextView tvOtherFollowers = (TextView) findViewById(R.id.tvOtherFollowers);
		TextView tvOtherFollowing = (TextView) findViewById(R.id.tvOtherFollowing);
		ImageView ivOtherProfileImage = (ImageView) findViewById(R.id.ivOtherProfileImage);
		
		
		thisUser = (User) thisTweet.getUser();
		getActionBar().setTitle("@" + thisUser.getScreenName());
		
		userName = (String) thisUser.getScreenName();
		tagLine = (String) thisUser.getTagline();
		OtherProfileUrl = (String) thisUser.getProfileImageUrl();
		otherFollowing = (int) thisUser.getFriendsCount();
		otherFollowers = (int) thisUser.getFollowersCount();
		
		tvOtherName.setText(userName);
		tvOtherTagline.setText(tagLine);
		tvOtherFollowers.setText(otherFollowers + " Followers");
		tvOtherFollowing.setText(otherFollowing + " Following");
		ImageLoader.getInstance().displayImage(OtherProfileUrl, ivOtherProfileImage);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Replace the container with the new fragment
		UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(userName);
		ft.replace(R.id.my_placeholder, fragmentUserTimeline);
		// or ft.add(R.id.your_placeholder, new FooFragment());
		// Execute the changes specified
		ft.commit();
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
