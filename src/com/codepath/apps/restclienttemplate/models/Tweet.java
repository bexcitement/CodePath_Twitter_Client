package com.codepath.apps.restclienttemplate.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.activeandroid.query.Select;

// allows us to work with tweets rather than json objects
public class Tweet implements Serializable {
	private static final long serialVersionUID = 6523462710255597252L;
	private User user;
	private String body;
	private long uid;
	private boolean favorited;
	private boolean retweeted;
	private String created_at;
	
//	public User getUser() {
//		return user;
//	}
//	
//	public String getBody() {
//		return getString("text");
//	}
//	
//	public long getId() {
//		return getLong("id");
//	}
//	
//	public boolean isFavorited() {
//		return getBool("retweeted"); 
//	}
	
//	public String getTimeOfTweet() {
//		return getString("created_at");
//	}
	
  	public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getId() {
        return uid;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public boolean isRetweeted() {
        return retweeted;
    }
    
    public String getTimeOfTweet() {
		return created_at;
	}
	
//	public static Tweet fromJson(JSONObject jsonObject) {
//		Tweet tweet = new Tweet();
//		
//		try {
//			tweet.jsonObject = jsonObject;
//			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
//		} catch(JSONException e) {
//			e.printStackTrace();
//			return null;
//		}
//		return tweet;
//	}
	
	public static Tweet fromJson(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
        	tweet.body = jsonObject.getString("text");
        	tweet.uid = jsonObject.getLong("id");
        	tweet.favorited = jsonObject.getBoolean("favorited");
        	tweet.retweeted = jsonObject.getBoolean("retweeted");
        	tweet.created_at = jsonObject.getString("created_at");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
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








