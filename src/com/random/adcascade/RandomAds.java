package com.random.adcascade;

import java.util.Random;

import org.acra.ErrorReporter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.random.games.BuildType;
import com.random.games.urls.AlienUrlsFree;
import com.random.games.urls.AlienUrlsPaid;
import com.random.games.urls.ChristmasUrlsPaid;
import com.random.games.urls.EightBitUrlsFree;
import com.random.games.urls.EightBitUrlsPaid;
import com.random.games.urls.HackersUrlsFree;
import com.random.games.urls.HackersUrlsPaid;
import com.random.games.urls.HalloweenDanceUrlsFree;
import com.random.games.urls.HalloweenUrlsFree;
import com.random.games.urls.HalloweenUrlsPaid;
import com.random.games.urls.MostManUrlsFree;
import com.random.games.urls.PacBallUrlFree;
import com.random.games.urls.XmasUrlsFree;
import com.random.games.urls.XmasUrlsPaid;

public class RandomAds {

	private final Context m_Context;
	private final Activity m_Activity;
	private final BuildType m_BuildType;
	final Random RandGenerator  = new Random();
	
	public RandomAds(Context context, Activity activity, BuildType buildType) {
		m_Context = context;
		m_Activity = activity;
		m_BuildType = buildType;
	}

	public ImageView eightFree(){
		ImageView houseAd = new ImageView(m_Context);
		
		switch(RandGenerator.nextInt(5))
		{
		case 0:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.eightbit_free_5));
			break;
		case 1:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.eightbit_free_4));
			break;
		case 2:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.eightbit_free_3));
			break;
		case 3:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.eightbit_free_2));
			break;
		case 4:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.eightbit_free_1));
			break;
		}
		
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
            public void onClick(View v)
            {
            	try {
            		m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new EightBitUrlsFree(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }
         });
		return houseAd;		
	}
	
	public ImageView eightPaid(){
		ImageView houseAd = new ImageView(m_Context);
		houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.eightbit_paid_1));
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
			public void onClick(View v)
            {
				try {
					m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new EightBitUrlsPaid(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }
         });
		return houseAd;		
	}
	
	public ImageView hackersFree(){
		ImageView houseAd = new ImageView(m_Context);
		
		switch(RandGenerator.nextInt(5))
		{
		case 0:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.hacker_free_6));
			break;
		case 1:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.hacker_free_2));
			break;
		case 2:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.hacker_free_3));
			break;
		case 3:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.hacker_free_4));
			break;
		case 4:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.hacker_free_5));
			break;
		}
	
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
            public void onClick(View v)
            {
            	try {
            		m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new HackersUrlsFree(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }	
         });
		return houseAd;		
	}
	
	public ImageView hackersPaid(){
		ImageView houseAd = new ImageView(m_Context);
		houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.hacker_paid_1));
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
            public void onClick(View v)
            {
            	try {
            		m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new HackersUrlsPaid(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }
         });
		return houseAd;		
	}
	

	public ImageView christmas() {
		ImageView houseAd = new ImageView(m_Context);
		houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.christmas_paid_1));
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
            public void onClick(View v)
            {
            	try {
            		m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new ChristmasUrlsPaid(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }
         });
		return houseAd;		
	}
	
	public ImageView halloween(){
		ImageView houseAd = new ImageView(m_Context);
		houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.halloween_paid_1));
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
            public void onClick(View v)
            {
            	try {
            		m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new HalloweenUrlsPaid(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }
         });
		return houseAd;		
	}
	
	public ImageView halloweenFree(){
		ImageView houseAd = new ImageView(m_Context);
		houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.halloween_free_1));
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
            public void onClick(View v)
            {
            	try {
            		m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new HalloweenUrlsFree(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }
         });
		return houseAd;		
	}
	
	
	public ImageView xmasFree(){
		ImageView houseAd = new ImageView(m_Context);
		houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.xmas_free_1));
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
            public void onClick(View v)
            {
            	try {
            		m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new XmasUrlsFree(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }
         });
		return houseAd;		
	}
	
	public ImageView xmasPaid(){
		ImageView houseAd = new ImageView(m_Context);
		houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.xmas_paid_1));
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
            public void onClick(View v)
            {
            	try {
            		m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new XmasUrlsPaid(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }
         });
		return houseAd;
	}	

	public ImageView xmasPaid2(){
		ImageView houseAd = new ImageView(m_Context);
		houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.xmas_paid_2));
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
            public void onClick(View v)
            {
            	try {
            		m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new XmasUrlsPaid(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }
         });
		return houseAd;
	}	

	public ImageView alienFree(){
		ImageView houseAd = new ImageView(m_Context);
		
		switch(RandGenerator.nextInt(5))
		{
		case 0:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.alien_free_1));
			break;
		case 1:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.alien_free_2));
			break;
		case 2:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.alien_free_3));
			break;
		case 3:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.alien_free_4));
			break;
		case 4:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.alien_free_5));
			break;
		case 5:
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.alien_free_6));
			break;
		}
		
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
            public void onClick(View v)
            {
            	try {
            		m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new AlienUrlsFree(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }
         });
		return houseAd;		
	}

	
	public ImageView halloweenDanceFree(){
		ImageView houseAd = new ImageView(m_Context);
		
		if(RandGenerator.nextBoolean())
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.halloween_dance_free_1));
		else
			houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.halloween_dance_free_2));
		
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
            public void onClick(View v)
            {
            	try {
            		m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new HalloweenDanceUrlsFree(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }
         });
		return houseAd;		
	}

	
	public ImageView alienPaid(){
		ImageView houseAd = new ImageView(m_Context);
		houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.alien_paid_1));
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
            public void onClick(View v)
            {
            	try {
            		m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new AlienUrlsPaid(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }
         });
		return houseAd;
	}
	
	public ImageView PacBallFree(){
		ImageView houseAd = new ImageView(m_Context);
		houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.pb_ad));
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
            public void onClick(View v)
            {
            	try {
            		m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new PacBallUrlFree(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }
         });
		return houseAd;
	}	
	
	public ImageView MostManFree(){
		ImageView houseAd = new ImageView(m_Context);
		houseAd.setImageBitmap(BitmapFactory.decodeResource(m_Context.getResources(), R.drawable.mimf_banner_ad));
		houseAd.setOnClickListener(null);
		houseAd.setOnClickListener( new OnClickListener() {
            public void onClick(View v)
            {
            	try {
            		m_Activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(new MostManUrlsFree(m_Activity,m_BuildType).getUri())));
	            } catch (android.content.ActivityNotFoundException e) {
					ErrorReporter.getInstance().handleException(e);	
				}
            }
         });
		return houseAd;
	}	
	
	
}
