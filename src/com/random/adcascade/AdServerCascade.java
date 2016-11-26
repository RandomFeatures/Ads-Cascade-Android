package com.random.adcascade;

import java.util.Locale;
import java.util.Random;

import org.acra.ErrorReporter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.random.games.BuildType;
import com.random.games.MyApps;

public abstract class AdServerCascade {

	private enum ActiveAd {
		None, MobFox, AdMob, adIQuity, MobClix, House
	};
	
    private final static int REFRESH_AD = 101;
	private final static long REFRESH_INTERVAL = 60000;
		

	/** Called when the activity is first created. */
	private final ViewFlipper m_ViewFlipper;

	private Context m_Context;
	private Activity m_Activity;
	private ActiveAd m_ActiveAdServer = ActiveAd.None;
	private CountryCodes m_Country = CountryCodes.NAMERICA;
	private MobFoxServer m_MobFoxServer;
	private AdMobServer m_AdMobServer;
	private ImageView m_HouseAds;
	private final MyApps m_AppType;
	private final BuildType m_BuildType;
	private final RandomAds m_RandomAds;
	private final RelativeLayout.LayoutParams m_adParams;
	final Random RandGenerator  = new Random();
	public abstract void onAdReady();
	//private Looper refreshLooper;
	
	
	private Handler MobFoxFailHandler = new Handler() {
		@Override
		 public void handleMessage(Message msg) {
				try {
					m_AdMobServer.sendRequest();
				} catch (Exception e) {
					ErrorReporter.getInstance().handleException(e);
					m_AdMobServer.requestFail();
				}

		 }
	 };

 	private Handler adMobFailHandler = new Handler() {
		@Override
		 public void handleMessage(Message msg) {
				if(m_ActiveAdServer == ActiveAd.None)
				{
					m_ActiveAdServer = ActiveAd.House;
					try {
						requestHouseAd();
					} catch (Exception e) {
						ErrorReporter.getInstance().handleException(e);	
					}
					onAdReady();
				}
		 }
	 };

	 private Handler refreshHandler = new Handler() {
		@Override
         public void handleMessage(Message msg) {
             switch (msg.what) {
             	case REFRESH_AD:
             	{
             		requestHouseAd();
             		break;
             	}
             }
         }
     };
	 
	public AdServerCascade(MyApps app, BuildType build, Activity activity, String mobFoxID, String adIQuity, String adMobID) {
		m_Activity = activity;
		m_Context = activity;
		m_AppType = app;
		m_BuildType = build;

		m_adParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
				RelativeLayout.LayoutParams.WRAP_CONTENT);
		m_RandomAds = new RandomAds(m_Context, m_Activity, m_BuildType);
		
		switch(m_AppType)
		{
		case XMAS:
		case XMAS_FREE:
		case EIGHTBIT:
		case EIGHTBIT_FREE:
			m_adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		case DANCE:			
		case DANCE_FREE:
		case HALLOWEEN_DANCE_FREE:	
			m_adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			break;
		case HACKERS:
		case HACKERS_FREE:
			m_adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			break;
		}
		
		m_adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		//int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, m_Context.getResources().getDisplayMetrics());
		//m_adParams.height = height;
		
		Locale locale = m_Context.getResources().getConfiguration().locale;
		
		if (locale.getCountry().equals("US") || locale.getCountry().equals("CA")) // AdMobOnly
			m_Country = CountryCodes.NAMERICA;
		else
			m_Country = CountryCodes.EUROPE;
		
		m_ViewFlipper = new ViewFlipper(m_Context) {
			@Override
			protected void onDetachedFromWindow() {
				try {
					super.onDetachedFromWindow();
				} catch (IllegalArgumentException e) {
					stopFlipping();
				}
			}
		};
		
		buildMobFox(mobFoxID);
		buildAdMob(adMobID);
	}
	
	public RelativeLayout.LayoutParams getAdParams() {
		
		switch(m_ActiveAdServer)
		{
		case adIQuity:
			m_adParams.height = 90;
			m_adParams.width = 500;
			break;
		case AdMob:
			break;
		case MobFox:
			m_adParams.height = 75;
			break;
		}
	
		
		return m_adParams;
	}
	
	public CountryCodes getCountry()
	{
		return m_Country;
	}
	
	private void buildAdMob(String id)
	{
		try {
			m_AdMobServer = new AdMobServer(m_Context, m_Activity, id) {

				@Override
				public void requestFail() {

					/*
					 * Well that's great, that's just fuckin' great, man. Now what the
					 * fuck are we supposed to do? We're in some real pretty shit now
					 * man...
					 */
					adMobFailHandler.sendEmptyMessage(0);
				}

				@Override
				public void requestSucceed() {
					if(m_ActiveAdServer == ActiveAd.None)
					{
						m_ActiveAdServer = ActiveAd.AdMob;
						try {
							m_ViewFlipper.addView(this.getView());
						} catch (Exception e) {
							ErrorReporter.getInstance().handleException(e);	
						}
						onAdReady();
					}
				}

			};
		} catch (Exception e) {
			ErrorReporter.getInstance().handleException(e);	
		}
	}
	
	private void buildMobFox(String id)
	{
		try {
			m_MobFoxServer = new MobFoxServer(m_Context, m_Activity, id) {
				@Override
				public void requestFail() {
					MobFoxFailHandler.sendEmptyMessage(0);
				}

				@Override
				public void requestSucceed() {
					
					if(m_ActiveAdServer == ActiveAd.None)
					{
						m_ActiveAdServer = ActiveAd.MobFox;
						try {
							m_ViewFlipper.addView(this.getView());
						} catch (Exception e) {
							ErrorReporter.getInstance().handleException(e);	
						}
						onAdReady();
					}
				}

			};
		} catch (Exception e) {
			ErrorReporter.getInstance().handleException(e);	
		}
	}
	
	public void requestFullHouseAd()
	{
		
		Intent myIntent = null;
		
		int randAd = RandGenerator.nextInt(10);
		try {
			switch (m_AppType) {
			case XMAS_FREE:
				switch (randAd) {
				case 0:// halloween 5% Christmas 5%
				case 3: // Xmas Paid 10%
				case 1:// Eight Free 10%
					myIntent = new Intent(m_Activity, TryEightBit.class);
					break;
				case 2:// Halloween Dance Free 30%
				case 4:
				case 5:
					myIntent = new Intent(m_Activity, TryHalloween.class);
					break;
				case 6:// Halloween Free 20%
				case 8:
					myIntent = new Intent(m_Activity, TryScream.class);
					break;
				case 7:// Dance Free 10%
				case 9:// 5% Haccker Wars
					myIntent = new Intent(m_Activity, TryAlien.class);
					break;
				}
				break;
			case EIGHTBIT_FREE:
				
				switch (randAd) {
				case 0:// halloween 5% Christmas 5%
				case 3: // Xmas Free 10%
				case 1:// Eight Paid 20%
					myIntent = new Intent(m_Activity, TryXmas.class);
					break;
				case 2:// Halloween Dance Free 20%
				case 6:
					myIntent = new Intent(m_Activity, TryHalloween.class);
					break;
				case 4:// Halloween Free 20%
				case 8:
					myIntent = new Intent(m_Activity, TryScream.class);
					break;
				case 5:
				case 7:// Dance Free 10%
				case 9:// 5% Haccker Wars
					myIntent = new Intent(m_Activity, TryAlien.class);
					break;

				}
				break;
			case HACKERS_FREE:
				switch (randAd) {
				case 0:// halloween 5% Christmas 5%
				case 3: // Xmas Free 10%
					myIntent = new Intent(m_Activity, TryXmas.class);
					break;
				case 1:// hacker Paid 20%
				case 2:// Halloween Dance Free 20%
					myIntent = new Intent(m_Activity, TryHalloween.class);
					break;
				case 6:// Halloween Free 20%
				case 8:
					myIntent = new Intent(m_Activity, TryScream.class);
					break;
				case 7://
				case 5:// Eight Free 10%
					myIntent = new Intent(m_Activity, TryEightBit.class);
					break;
				case 4:
				case 9:// 5% Aliens Wars
					myIntent = new Intent(m_Activity, TryAlien.class);
					break;
				}
				break;
			case DANCE_FREE:
				switch (randAd) {
				case 0:// halloween 5% //Christmas 5%
				case 1:
				case 5:// Eight Free 30%
					myIntent = new Intent(m_Activity, TryEightBit.class);
					break;
				case 3: // Xmas Free 10%
				case 8:// Halloween Free 20%
				case 2:// Halloween Dance Free 20%
					myIntent = new Intent(m_Activity, TryXmas.class);
					break;
				case 4:
				case 7:					
					myIntent = new Intent(m_Activity, TryHalloween.class);
					break;
				case 6:
				case 9:// 5% Haccker Wars
					myIntent = new Intent(m_Activity, TryScream.class);
					break;

				}
				break;
			case HALLOWEEN_DANCE_FREE:
				switch (randAd) {
				case 0:// halloween 5% //Christmas 5%
				case 5:
					myIntent = new Intent(m_Activity, TryEightBit.class);
					break;
				case 1:// hacker Free 40%
				case 7:// Eight Free 40%
				case 3: // Xmas Free 10%
					myIntent = new Intent(m_Activity, TryXmas.class);
					break;
				case 2:// Halloween Free 40%
				case 6:
					myIntent = new Intent(m_Activity, TryScream.class);
					break;
				case 8:
				case 4:
				case 9:// 5% Haccker Wars
						myIntent = new Intent(m_Activity, TryAlien.class);
					break;
				}
				break;
			}
			if(myIntent != null)
				m_Activity.startActivity(myIntent);

		} catch (Exception e) {
			ErrorReporter.getInstance().handleException(e);
		}
		
		
	}
	
	private void requestHouseAd()
	{
		
		int randAd = RandGenerator.nextInt(10);
		if (m_ViewFlipper.getChildCount() > 0)
			m_ViewFlipper.removeViewAt(0);
		m_HouseAds = null;
		// Load my own ads and set up my click listners
		try {
			switch (m_AppType) {
			case XMAS_FREE:
				
				//System.out.println(ad);
				switch (randAd) {
				case 0:// halloween 5% Christmas 5%
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.halloween();
					else
						m_HouseAds = m_RandomAds.christmas();
					break;
				case 3: // Xmas Paid 10%
					m_HouseAds = m_RandomAds.xmasPaid();
					break;
				case 1:// Eight Free 10%
					m_HouseAds = m_RandomAds.eightFree();
					break;
				case 2:// Halloween Dance Free 30%
				case 4:
				case 5:
					m_HouseAds = m_RandomAds.halloweenDanceFree();
					break;
				case 6:// Halloween Free 20%
				case 8:
					m_HouseAds = m_RandomAds.halloweenFree();
					break;
				case 7:// Dance Free 10%
					m_HouseAds = m_RandomAds.alienFree();
					break;
				case 9:// 5% Haccker Wars
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.hackersFree();
					else // 5% friends
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.MostManFree();
					else
						m_HouseAds = m_RandomAds.PacBallFree();
					break;
				}
				break;
			case EIGHTBIT_FREE:
				
				switch (randAd) {
				case 0:// halloween 5% Christmas 5%
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.halloween();
					else
						m_HouseAds = m_RandomAds.christmas();
					break;
				case 3: // Xmas Free 10%
					m_HouseAds = m_RandomAds.xmasFree();
					break;
				case 2:// Halloween Dance Free 20%
				case 6:
					m_HouseAds = m_RandomAds.halloweenDanceFree();
					break;
				case 4:// Halloween Free 20%
				case 8:
					m_HouseAds = m_RandomAds.halloweenFree();
					break;
				case 1:// Eight Paid 20%
				case 5:
					m_HouseAds = m_RandomAds.eightPaid();
					break;
				case 7:// Dance Free 10%
					m_HouseAds = m_RandomAds.alienFree();
					break;
				case 9:// 5% Haccker Wars
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.hackersFree();
					else // 5% friends
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.MostManFree();
					else
						m_HouseAds = m_RandomAds.PacBallFree();
					break;

				}
				break;
			case HACKERS_FREE:
				switch (randAd) {
				case 0:// halloween 5% Christmas 5%
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.halloween();
					else
						m_HouseAds = m_RandomAds.christmas();
					break;
				case 3: // Xmas Free 10%
					m_HouseAds = m_RandomAds.xmasFree();
					break;
				case 1:// hacker Paid 20%
				case 7://
					m_HouseAds = m_RandomAds.hackersPaid();
					break;
				case 2:// Halloween Dance Free 20%
				case 4:
					m_HouseAds = m_RandomAds.halloweenDanceFree();
					break;
				case 6:// Halloween Free 20%
				case 8:
					m_HouseAds = m_RandomAds.halloweenFree();
					break;
				case 5:// Eight Free 10%
					m_HouseAds = m_RandomAds.eightFree();
					break;
				case 9:// 5% Aliens Wars
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.alienFree();
					else // 5% friends
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.MostManFree();
					else
						m_HouseAds = m_RandomAds.PacBallFree();
					break;
				}
				break;
			case DANCE_FREE:
				switch (randAd) {
				case 0:// halloween 5% //Christmas 5%
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.halloween();
					else
						m_HouseAds = m_RandomAds.christmas();
					break;
				case 1:
				case 5:// Eight Free 30%
					m_HouseAds = m_RandomAds.eightFree();
					break;
				case 3: // Xmas Free 10%
					m_HouseAds = m_RandomAds.xmasFree();
					break;
				case 2:// Halloween Dance Free 20%
				case 4:
				case 7:					
					m_HouseAds = m_RandomAds.halloweenDanceFree();
					break;
				case 8:// Halloween Free 20%
				case 6:
					m_HouseAds = m_RandomAds.halloweenFree();
					break;
				case 9:// 5% Haccker Wars
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.hackersFree();
					else // 5% friends
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.MostManFree();
					else
						m_HouseAds = m_RandomAds.PacBallFree();
					break;

				}
				break;
			case HALLOWEEN_DANCE_FREE:
				switch (randAd) {
				case 0:// halloween 5% //Christmas 5%
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.halloween();
					else
						m_HouseAds = m_RandomAds.christmas();
					break;
				case 1:// hacker Free 40%
					m_HouseAds = m_RandomAds.hackersFree();
					break;
				case 7:// Eight Free 40%
				case 5:
					m_HouseAds = m_RandomAds.eightFree();
					break;
				case 3: // Xmas Free 10%
					m_HouseAds = m_RandomAds.xmasFree();
					break;
				case 2:// Halloween Free 40%
				case 4:
				case 8:
				case 6:
					m_HouseAds = m_RandomAds.halloweenFree();
					break;
				case 9:// 5% Haccker Wars
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.alienFree();
					else // 5% friends
					if (RandGenerator.nextBoolean())
						m_HouseAds = m_RandomAds.MostManFree();
					else
						m_HouseAds = m_RandomAds.PacBallFree();
					break;

				}
				break;
			}
		} catch (Exception e) {
			ErrorReporter.getInstance().handleException(e);
		}

		m_ViewFlipper.addView(m_HouseAds);
		if (refreshHandler != null) {

			refreshHandler.sendEmptyMessageDelayed(REFRESH_AD, REFRESH_INTERVAL);

		}
	}
	

	public ViewFlipper getAdCascade() {

		
		if(Util.isNetworkConnected(m_Context))
		{
			int randAd = RandGenerator.nextInt(10);
			int percent = 1;
			
			if(m_AppType == MyApps.XMAS_FREE)
				percent = 10;
				
			if(randAd > percent) //80%
			{
				switch(m_Country)
				 {
				 case CHINA:
				 case EUROPE:
				 case RUSSIA:
					 m_MobFoxServer.sendRequest();
					 break;
				 case NAMERICA:
					 m_AdMobServer.sendRequest();
					 break;
				 }
			}else //20%
				adMobFailHandler.sendEmptyMessage(0);
			
			
			//m_MobFoxServer.sendRequest();
			//m_AdMobServer.sendRequest();
			//m_AdIQuityServer.sendRequest();
		}else 
			adMobFailHandler.sendEmptyMessage(0);
		
			
		m_ViewFlipper.setDisplayedChild(0);
		return m_ViewFlipper;
	}
	
	
	public void onPause() {
		switch (m_ActiveAdServer) {
		case MobFox:
			m_MobFoxServer.getView().pause();
			break;
		case House:
			if (refreshHandler != null)
			{
				refreshHandler.removeMessages(REFRESH_AD);
			}
			break;
		}

	}

	public void onResume() {
		switch (m_ActiveAdServer) {
		case MobFox:
			m_MobFoxServer.getView().resume();
			break;
		case House:
			if (refreshHandler != null)
			{
				refreshHandler.removeMessages(REFRESH_AD);
				refreshHandler.sendEmptyMessage(REFRESH_AD);
			}
			break;
		}
	}

	public void onConfigurationChanged(Configuration newConfig) {
		switch (m_ActiveAdServer) {
		case MobFox:
			m_MobFoxServer.getView().pause();
			m_MobFoxServer.getView().resume();
			break;
		}

	}
	
	public void onDestroy() {
		
		adMobFailHandler = null;
		MobFoxFailHandler = null;
		refreshHandler = null;
	}

	
}
