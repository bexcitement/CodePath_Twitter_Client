package com.codepath.apps.twitterapp_rbruggman;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.impl.cookie.DateParseException;
import org.json.JSONException;

import android.content.Context;
import android.text.Html;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet>{
	public static final long SECOND_IN_MILLIS = 0x00000000000003E8;

	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
		// TODO Auto-generated constructor stub
	}
	
	public String getRelativeTimeAgo(String rawJsonDate) {
		   String twitterFormat="EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		   SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		   sf.setLenient(true);
		   String relativeDate = "";
			try {
				long dateMillis = sf.parse(rawJsonDate).getTime();
				relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
						System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		 
			return relativeDate;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// convertview is views that can be reused
		View view = convertView;
		
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// if null we go get more tweets
			view = inflater.inflate(R.layout.tweet_item, null);
		}
		
		Tweet tweet = getItem(position);
		
		ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
		// allows us to async load from the network and set the image view
		// have to pass imageView in for our bitmap
		ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
		
		TextView nameView = (TextView) view.findViewById(R.id.tvName);
		String formattedName = "<b>" + tweet.getUser().getName() + "</b>" + "<small><font color='#777777'>@" + tweet.getUser().getScreenName() + "</font></small>";
		nameView.setText(Html.fromHtml(formattedName));
		
		TextView bodyView = (TextView) view.findViewById(R.id.tvBody);
		bodyView.setText(Html.fromHtml(tweet.getBody()));
		
		TextView timeView = (TextView) view.findViewById(R.id.tvTime);
		String tweetDate = tweet.getTimeOfTweet();
		timeView.setText(getRelativeTimeAgo(tweetDate));
		
		return view;
	}

}
