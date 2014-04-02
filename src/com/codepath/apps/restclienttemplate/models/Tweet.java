package com.codepath.apps.restclienttemplate.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.query.Select;

// allows us to work with tweets rather than json objects
public class Tweet extends BaseModel{

	private User user;
	
	public User getUser() {
		return user;
	}
	
	public String getBody() {
		return getString("text");
	}
	
	public long getId() {
		return getLong("id");
	}
	
	public boolean isFavorited() {
		return getBool("retweeted"); 
	}
	
	public String getTimeOfTweet() {
		return getString("created_at");
	}
	
	public static Tweet fromJson(JSONObject jsonObject) {
		Tweet tweet = new Tweet();
		
		try {
			tweet.jsonObject = jsonObject;
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
		} catch(JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;
	}
	
	public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
		
		for (int i=0; i < jsonArray.length(); i++) {
			JSONObject tweetJson= null;
			
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			
			Tweet tweet = Tweet.fromJson(tweetJson);
			
			if (tweet != null) {
				tweets.add(tweet);
			}
		}
		return tweets;
	}
	
//	public static Tweet byPhotoUid(Long id) {
//	   return new Select().from(Tweet.class).where("uid = ?", uid).executeSingle();
//	}
}







