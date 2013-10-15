package com.bk.pushtest;

import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.gcm.GCMRegistrar;

public class PushTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push_test);
		registerGcm();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.push_test, menu);
		
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationUpdater locationUpdater = LocationUpdater.getInstance();
		locationUpdater.setContext(this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, locationUpdater);
		return true;
	}
	public void registerGcm() {
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);

		final String regId = GCMRegistrar.getRegistrationId(this);

		if (regId.equals("")) {
			GCMRegistrar.register(this, "426728137257");
		} else {
			Log.i("reg_id", regId);
		}
	}
    
}
