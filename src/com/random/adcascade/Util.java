package com.random.adcascade;

import java.lang.reflect.Method;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Util {

	public static boolean isTabletDevice(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= 11) { // honeycomb
	        // test screen size, use reflection because isLayoutSizeAtLeast is only available since 11
	        Configuration con = context.getResources().getConfiguration();
	        try {
	            Method mIsLayoutSizeAtLeast = con.getClass().getMethod("isLayoutSizeAtLeast", int.class);
	            Boolean r = (Boolean) mIsLayoutSizeAtLeast.invoke(con, 0x00000004); // Configuration.SCREENLAYOUT_SIZE_XLARGE
	            return r;
	        } catch (Exception x) {
	            //x.printStackTrace();
	            return false;
	        }
        }
        return false;
	}
	
	public static boolean isNetworkConnected(Context context){
		ConnectivityManager conMan = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = conMan.getActiveNetworkInfo();
		return activeNetwork != null && activeNetwork.isConnected();
	}
}
