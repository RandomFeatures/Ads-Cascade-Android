package com.random.adcascade;

import org.acra.ErrorReporter;

import com.mobfox.sdk.BannerListener;
import com.mobfox.sdk.MobFoxView;
import com.mobfox.sdk.Mode;
import com.mobfox.sdk.RequestException;

import android.app.Activity;
import android.content.Context;

public abstract class MobFoxServer extends AdServerBase implements BannerListener{

	private MobFoxView m_MobFoxView;
	private final boolean m_IncludeLocation = true;
	private final boolean m_UseAnimation = true;
	
	public MobFoxServer(Context context, Activity activity, String ServerID) {
		super(context, activity, ServerID);
		try {
			m_MobFoxView = new MobFoxView(m_Context, m_ServerID, Mode.LIVE, m_IncludeLocation, m_UseAnimation);
			m_MobFoxView.setBannerListener(this);
		} catch (Exception e) {
			m_MobFoxView = null;
			ErrorReporter.getInstance().handleException(e);	
		}
		
	}

	@Override
	public MobFoxView getView() {
		return m_MobFoxView;
	}
	
	@Override
	public void sendRequest() {
		try {
			if(m_MobFoxView != null)
				m_MobFoxView.loadNextAd();
			else
				requestFail();
		} catch (Exception e) {
			requestFail();
			ErrorReporter.getInstance().handleException(e);	
		}
		

	}

	@Override
	public void setTestMode() {
		
	}
	
	@Override
	public void noAdFound() {
		requestFail();
	}

	@Override
	public void bannerLoadFailed(RequestException e) {
		requestFail();
	}


	@Override
	public void bannerLoadSucceeded() {
		requestSucceed();	
	}

	@Override
	public void adClicked() {
	}


}
