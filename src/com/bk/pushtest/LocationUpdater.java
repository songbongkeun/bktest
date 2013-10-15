package com.bk.pushtest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class LocationUpdater implements LocationListener {
	private Location myLocation;
	private Geocoder geoCoder;
	private static LocationUpdater locationUpdater;
	
	private LocationUpdater() {}
	public static LocationUpdater getInstance() {
		if(locationUpdater == null) {
			locationUpdater = new LocationUpdater();
		}
		return locationUpdater;
	}
	public void setContext(Context context) {
		geoCoder = new Geocoder(context, Locale.KOREAN);
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.i("location", "location changed");
		myLocation = location;
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

	public void getLocations() {
		// 텍스트뷰를 찾음
		StringBuffer juso = new StringBuffer();

		if (myLocation != null) {
			double latPoint = myLocation.getLatitude();
			double lngPoint = myLocation.getLongitude();
			float speed = (float) (myLocation.getSpeed() * 3.6);

			try {
				// 위도,경도를 이용하여 현재 위치의 주소를 가져온다.
				List<Address> addresses;
				addresses = geoCoder.getFromLocation(latPoint, lngPoint, 1);
				for (Address addr : addresses) {
					int index = addr.getMaxAddressLineIndex();
					for (int i = 0; i <= index; i++) {
						juso.append(addr.getAddressLine(i));
						juso.append(" ");
					}
					juso.append("\n");
				}
				Log.i("BK Test", "latPoint : " + latPoint);
				Log.i("BK Test", "lngPoint : " + lngPoint);
				Log.i("BK Test", "speed : " + speed);
				Log.i("BK Test", "juso : " + juso);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}
