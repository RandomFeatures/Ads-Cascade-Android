package com.random.adcascade;

import java.util.HashSet;
import java.util.Set;

import org.acra.ErrorReporter;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.google.ads.AdRequest.ErrorCode;

public abstract class AdMobServer extends AdServerBase implements AdListener {

	private AdView m_AdMobView;
	private AdRequest m_adRequest;
	
	public AdMobServer(Context context, Activity activity, String ServerID) {
		super(context, activity, ServerID);
		try {
			// Create and setup the AdMob view
			//if(Util.isTabletDevice(m_Context))
				//bigger add for tablets
			//	m_AdMobView = new AdView(m_Activity, AdSize.IAB_LEADERBOARD, m_ServerID);
			//else {
				m_AdMobView = new AdView(m_Activity, AdSize.BANNER, m_ServerID);
			//}
			m_adRequest = new AdRequest();
			m_AdMobView.setAdListener(this);
			Set<String> set = new HashSet<String>();
			set.add("android");
			set.add("game");
			m_adRequest.setKeywords(set);
			int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, m_Context.getResources().getDisplayMetrics());
			m_AdMobView.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.WRAP_CONTENT, height)); 
			
			
		} catch (Exception e) {
			m_AdMobView = null;
			ErrorReporter.getInstance().handleException(e);	
		}
	}

	@Override
	public AdView getView() {
		return m_AdMobView;
	}

	@Override
	public void sendRequest() {
		if(m_AdMobView != null)
		{
			try {
				m_AdMobView.loadAd(m_adRequest);
			} catch (Exception e) {
				requestFail();
				ErrorReporter.getInstance().handleException(e);	
			}
		}else
			requestFail();
	}
	
	@Override
	public void setTestMode() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		requestFail();
	}

	@Override
	public void onReceiveAd(Ad arg0) {
		requestSucceed();
	}
	
	
	@Override
	public void onDismissScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

}
