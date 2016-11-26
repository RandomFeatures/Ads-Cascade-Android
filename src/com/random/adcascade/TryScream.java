package com.random.adcascade;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.random.games.GameInfo;
import com.random.games.urls.HalloweenUrlsFree;

public class TryScream extends Activity {

	
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.tryscream);

		// setContentView(R.layout.feedback);
		final Button btnDownload = (Button) findViewById(R.id.btnDownload);
		btnDownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				// Launch the web browser!
				HalloweenUrlsFree MyAppUrl = new HalloweenUrlsFree(TryScream.this, GameInfo.getBuildType(TryScream.this));
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MyAppUrl.getUri())));
				finish();
			}
		});

		final Button btnLater = (Button) findViewById(R.id.btnLater);
		btnLater.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				 finish();
			}
		});
	}
	
}
