package com.codepath.apps.twitterapp_rbruggman;

import org.json.JSONArray;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ComposeTweet extends Activity {
	private String name;
	private EditText newTweet;
	TwitterClient client;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose_tweet);
		name = getIntent().getStringExtra("name");
	}
	
	public void onSubmit(View v) {
		  // closes the activity and returns to first screen
		  EditText newTweet = (EditText) findViewById(R.id.etNewTweet);
		  Intent data = new Intent();
		  data.putExtra(TimelineActivity.TWEET_EXTRA, newTweet.getText().toString());
		  setResult(RESULT_OK, data);
		  
		  this.finish(); 
		  Toast.makeText(this, newTweet.getText().toString(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose_tweet, menu);
		return true;
	}

}
