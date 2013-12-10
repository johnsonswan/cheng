package com.example.googlemap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnMapClickListener,
		OnMapLongClickListener, OnMarkerDragListener {
	private GoogleMap googleMap;
	EditText textField;
	boolean oncemaker;
	private ArrayList<Marker> mMarkerArray;
	LatLng loca;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mMarkerArray = new ArrayList<Marker>();
		textField = (EditText) findViewById(R.id.editText1);	
		Button button = (Button) findViewById(R.id.button1);
		
		textField.setOnEditorActionListener(new OnEditorActionListener() {        
		    @Override
		 

			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				// TODO Auto-generated method stub
		    	 if(actionId==EditorInfo.IME_ACTION_DONE){
		    			Geocoder geocoder = new Geocoder(getBaseContext());
						List<Address> addresses;

						try {

							addresses = geocoder.getFromLocationName(textField
									.getText().toString(), 1);
							if (addresses.size() > 0) {

								if (mMarkerArray.size() > 0) {
									for (Marker maker : mMarkerArray) {
										maker.remove();
									}
								}
								Address addr = addresses.get(0);
								double latitude = addr.getLatitude();
								double longitude = addr.getLongitude();
								loca = new LatLng(latitude, longitude);
								CameraPosition cameraPosition = new CameraPosition.Builder()
										.target(loca).zoom(15).build();

								googleMap.animateCamera(CameraUpdateFactory
										.newCameraPosition(cameraPosition));
								Marker marker = googleMap.addMarker(new MarkerOptions()
										.position(loca).draggable(true));

								mMarkerArray.add(marker);

							}

						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        }
				return false;
			}
		});
	
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Geocoder geocoder = new Geocoder(getBaseContext());
				List<Address> addresses;

				try {

					addresses = geocoder.getFromLocationName(textField
							.getText().toString(), 1);
					if (addresses.size() > 0) {

						if (mMarkerArray.size() > 0) {
							for (Marker maker : mMarkerArray) {
								maker.remove();
							}
						}
						Address addr = addresses.get(0);
						double latitude = addr.getLatitude();
						double longitude = addr.getLongitude();
						loca = new LatLng(latitude, longitude);
						CameraPosition cameraPosition = new CameraPosition.Builder()
								.target(loca).zoom(15).build();

						googleMap.animateCamera(CameraUpdateFactory
								.newCameraPosition(cameraPosition));
						Marker marker = googleMap.addMarker(new MarkerOptions()
								.position(loca).draggable(true));

						mMarkerArray.add(marker);

					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});
		try {
			// Loading map
			initilizeMap();

			// Changing map type
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

			// Showing / hiding your current location
			googleMap.setMyLocationEnabled(true);

			// Enable / Disable zooming controls
			googleMap.getUiSettings().setZoomControlsEnabled(false);

			// Enable / Disable my location button
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

			// Enable / Disable Compass icon
			googleMap.getUiSettings().setCompassEnabled(true);

			// Enable / Disable Rotate gesture
			googleMap.getUiSettings().setRotateGesturesEnabled(true);

			// Enable / Disable zooming functionality
			googleMap.getUiSettings().setZoomGesturesEnabled(true);

			googleMap.setOnMapLongClickListener(this);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	public void onMapLongClick(LatLng point) {

		Geocoder gcd = new Geocoder(this, Locale.getDefault());
		List<Address> addresses;
		try {
			addresses = gcd.getFromLocation(point.latitude, point.longitude, 1);
			if (addresses.size() > 0) {
				textField.setText(addresses.get(0).getAddressLine(0) + ","
						+ addresses.get(0).getLocality() + ","
						+ addresses.get(0).getPostalCode());
			}
			if (mMarkerArray.size() > 0) {
				for (Marker maker : mMarkerArray) {
					maker.remove();
				}
			}
			Marker marker = googleMap.addMarker(new MarkerOptions().position(
					point).draggable(true));

			mMarkerArray.add(marker);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	@Override
	public void onMarkerDrag(Marker arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDragEnd(Marker arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMarkerDragStart(Marker arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub

	}
}
