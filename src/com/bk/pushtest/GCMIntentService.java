package com.bk.pushtest;

import com.google.android.gcm.GCMBaseIntentService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

public class GCMIntentService extends GCMBaseIntentService {

	private static void generateNotification(Context context, String message) {
		int icon = R.drawable.ic_launcher;
		long when = System.currentTimeMillis();
		
		String title = context.getString(R.string.app_name);
		Intent notificationIntent = new Intent(context, PushTestActivity.class);
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent intent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

		NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification.Builder(context).setSmallIcon(icon).setWhen(when).setContentIntent(intent).setContentText(title).setContentText(message).build();

		notification.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, notification);

	}

	@Override
	protected void onError(Context arg0, String arg1) {

	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		String action = intent.getStringExtra("ActionName");
		Log.i("BK", "Action:" + action);
		Toast.makeText(this, action, Toast.LENGTH_LONG).show();
		
		if(action.equals("VIBRATE")) {
			Log.i("BK Test", "Received Vibrate");
			Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			long[] vibratePattern = {100, 100, 300};
			vibrator.vibrate(300);
			vibrator.vibrate(vibratePattern, 0);
		} else if(action.equals("LOCATE")) {
			Log.i("BK Test", "Received Locate");
			LocationUpdater locationUpdater = LocationUpdater.getInstance();
			locationUpdater.getLocations();
		} else if(action.equals("MESSAGE")) {
			Log.i("BK Test", "Received Message");
			String message = intent.getStringExtra("Message");
			generateNotification(context, message);
		}
	}

	@Override
	protected void onRegistered(Context context, String reg_id) {
		Log.i("BK Test", "Regist Key.(GCM INTENTSERVICE) : " + reg_id);
	}

	@Override
	protected void onUnregistered(Context arg0, String arg1) {
		Log.i("BK Test", "Unregist Key.(GCM INTENTSERVICE) removed.");
	}

}

