package com.codepath.apps.twitterapp_rbruggman;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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

public class ProfileActivity extends FragmentActivity {
	TwitterClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		client = (TwitterClient) TwitterClient.getInstance(TwitterClient.class, this);
//		Tweet thisTweet = (Tweet) getIntent().getSerializableExtra("result");
		loadProfileInfo();
//		loadOtherUserProfile();
	}
	
	public void loadProfileInfo(){
		client.getMyInfo(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject json) {
				User u = User.fromJson(json);
				getActionBar().setTitle("@" + u.getScreenName());
				populateProfileHeader(u);
			}
		});
	}
	
	private void populateProfileHeader(User user) {
		TextView tvName = (TextView) findViewById(R.id.tvName);
		TextView tvTagline = (TextView) findViewById(R.id.tvTagline);
		TextView tvFollowers = (TextView) findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView) findViewById(R.id.tvFollowing);
		ImageView ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
		
		tvName.setText(user.getName());
		tvFollowers.setText(user.getFollowersCount() + " Followers");
		tvFollowing.setText(user.getFriendsCount() + " Following");
		tvTagline.setText(user.getTagline());
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), ivProfileImage);
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		// Replace the container with the new fragment
		UserTimelineFragment fragmentUserTimeline = UserTimelineFragment.newInstance(user.getName());
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
