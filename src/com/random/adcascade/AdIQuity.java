package com.random.adcascade;

import org.acra.ErrorReporter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.webkit.WebSettings;

import com.adiquity.android.AdIquityAdListener;
import com.adiquity.android.AdIquityAdView;

public abstract class AdIQuity extends AdServerBase implements AdIquityAdListener {

	private AdIquityAdView mAdView;
	
	public AdIQuity(Context context, Activity activity, String ServerID) {
		super(context, activity, ServerID);
		
		try {
			mAdView = new AdIquityAdView(m_Activity);
			mAdView.setBackgroundColor(0);
			mAdView.init(ServerID, m_Activity, AdIquityAdView.ADIQUITY_AD_UNIT_300_50);
			// To request an ad in test mode (For testing/development purpose)
			
			WebSettings webSettings = mAdView.getSettings();
			webSettings.setSavePassword(false);
			webSettings.setSaveFormData(false);
			webSettings.setJavaScriptEnabled(true);
			webSettings.setSupportZoom(false);

			// mAdView.setTestMode();
			
			// To get Ads only once set Refresh Time < 0 - Default : 30secs
			mAdView.setRefreshTime(40);
			
			// To Receive call back event on error and on success ads
			mAdView.setAdListener(this);
		} catch (Exception e) {
			mAdView = null;
			ErrorReporter.getInstance().handleException(e);	
		}
	}

	@Override
	public View getView() {
		return mAdView;
	}

	@Override
	public void sendRequest() {
		try {
			// Start the ads now
			mAdView.startAds();
		} catch (Exception e) {
			requestFail();
			ErrorReporter.getInstance().handleException(e);	
		} 
	}

	@Override
	public void setTestMode() {
		// TODO Auto-generated method stub

	}
	
	
	@Override
	public void adRequestCompleted(AdIquityAdView arg0) {
		//arg0.loadUrl( "javascript:window.location.reload( true )" );
		requestSucceed();
	}

	@Override
	public void adRequestFailed(AdIquityAdView arg0) {
		requestFail();
	}

	

}
