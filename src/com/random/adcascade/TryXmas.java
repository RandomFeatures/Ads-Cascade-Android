package com.random.adcascade;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.random.games.GameInfo;
import com.random.games.urls.XmasUrlsFree;

public class TryXmas extends Activity {
	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.tryxmas);

		// setContentView(R.layout.feedback);
		final Button btnDownload = (Button) findViewById(R.id.btnDownload);
		btnDownload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(final View v) {
				// Launch the web browser!
				XmasUrlsFree MyAppUrl = new XmasUrlsFree(TryXmas.this, GameInfo.getBuildType(TryXmas.this));
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
