/**
 * Copyright 2014 Jeff McKenzie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robowebi;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * @author jeff
 * 
 */
public class GpsActivity extends Activity {

	private static final String tag = "AbstractMainActivity";
	private LocationListener locationListener;
	private LocationManager lm;
	private TextView lat;
	private TextView lon;
	private TextView accuracy;
	private TextView altitude;
	private TextView bearing;
	private TextView speed;
	private TextView time;
	private Location lastLocation;

	private class GpsLocationListener implements LocationListener {
		public void onLocationChanged(Location location) {
			Log.v(tag, location.toString());
			lastLocation = location;
			try {
				lat.setText(Double.toString(lastLocation.getLatitude()));
				lon.setText(Double.toString(lastLocation.getLongitude()));
				accuracy.setText(Float.toString(lastLocation.getAccuracy()));
				altitude.setText(Double.toString(lastLocation.getAltitude()));
				bearing.setText(Float.toString(lastLocation.getBearing()));
				speed.setText(Float.toString(lastLocation.getSpeed()));
				time.setText(Long.toString(lastLocation.getTime()));
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.v(tag, "onCreate");
		setContentView(R.layout.gps_layout);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.v(tag, "onResume");

		lat = (TextView) findViewById(R.id.lat);
		lon = (TextView) findViewById(R.id.lon);
		accuracy = (TextView) findViewById(R.id.accuracy);
		altitude = (TextView) findViewById(R.id.altitude);
		bearing = (TextView) findViewById(R.id.bearing);
		speed = (TextView) findViewById(R.id.speed);
		time = (TextView) findViewById(R.id.time);

		Context ctx = getApplicationContext();
		lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getAllProviders();
		Log.v(tag, "LocationManager.getAllProviders: " + providers);
		locationListener = new GpsLocationListener();
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				locationListener);

	}

	@Override
	protected void onPause() {
		super.onPause();
		Log.v(tag, "onPause");
		lm.removeUpdates(locationListener);
	}
}