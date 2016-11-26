package com.random.adcascade;

import android.app.Activity;
import android.content.Context;
import android.view.View;

public abstract class AdServerBase {

	protected final Context m_Context;
	protected final Activity m_Activity;
	protected final String m_ServerID;
	public AdServerBase(Context context, Activity activity, String ServerID) {
		m_Context = context;
		m_Activity = activity;
		m_ServerID =  ServerID;
	}
	public boolean isActive() { return (!m_ServerID.equals("")); }
	public abstract void requestFail();
	public abstract void requestSucceed();
	public abstract void sendRequest();
	public abstract void setTestMode();
	public abstract View getView();
}
