package com.app.fixy.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Utils {
	Context mActivity;
	SharedPreferences preferences;

	public Utils(Context activity) {
		mActivity = activity;
		preferences = activity.getSharedPreferences(activity.getPackageName(), Context.MODE_PRIVATE);
	}

	public void setString(String key, String value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public String getString(String key, String defaultValue) {
		return preferences.getString(key, defaultValue);
	}

	public void setBoolean(String key, Boolean value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public boolean getBoolean(String key, Boolean defaultValue) {
		return preferences.getBoolean(key, defaultValue);
	}

	public void setInt(String key, int value) {
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public int getInt(String key, int defaultValue) {
		return preferences.getInt(key, defaultValue);
	}

	public void clear_shf() {
		preferences.edit().clear().commit();
	}

	public static int dpToPx(float dp, Resources resources){
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
		return (int) px;
	}

	public static int getRelativeTop(View myView) {
		if(myView.getId() == android.R.id.content)
			return myView.getTop();
		else
			return myView.getTop() + getRelativeTop((View) myView.getParent());
	}

	public static int getRelativeLeft(View myView) {
		if(myView.getId() == android.R.id.content)
			return myView.getLeft();
		else
			return myView.getLeft() + getRelativeLeft((View) myView.getParent());
	}

	public static int dpToPx(Context context, float dp) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return Math.round(dp * scale);
	}

	public static boolean hasJellyBean() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	public static boolean hasLollipop() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
	}

	public static float dp2px(Resources resources, float dp) {
		final float scale = resources.getDisplayMetrics().density;
		return  dp * scale + 0.5f;
	}

	public static float sp2px(Resources resources, float sp){
		final float scale = resources.getDisplayMetrics().scaledDensity;
		return sp * scale;
	}


	public void hideKeyboard(Activity mContext) {
		// Check if no view has focus:
		View view = mContext.getCurrentFocus();
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public void hideKeyboardDialog(View view,Context mContext) {
		// Check if no view has focus:
		if (view != null) {
			InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}

	public static long getUtcTime(long time) {
		long utcTime = 0;
		String dateValue = "";
		try {
			SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			Calendar calUtc = Calendar.getInstance();

			SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			localFormat.setTimeZone(TimeZone.getDefault());
			Calendar calLocal = Calendar.getInstance();

			calLocal.setTimeInMillis(time);
			dateValue = localFormat.format(calLocal.getTime());
			Date value = localFormat.parse(dateValue);

			dateValue = utcFormat.format(value);
			calUtc.setTime(utcFormat.parse(dateValue));
			utcTime = calUtc.getTimeInMillis();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return utcTime;
	}

	public static long getLocalTime(long time) {
		long localTime = 0;
		String dateValue = "";
		try {
			SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			Calendar calUtc = Calendar.getInstance();

			SimpleDateFormat localFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			localFormat.setTimeZone(TimeZone.getDefault());
			Calendar calLocal = Calendar.getInstance();

			calUtc.setTimeInMillis(time);
			dateValue = utcFormat.format(calUtc.getTime());
			Date value = utcFormat.parse(dateValue);

			dateValue = localFormat.format(value);
			Date localvalue = localFormat.parse(dateValue);
			localTime = localvalue.getTime();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return localTime;
	}

}
