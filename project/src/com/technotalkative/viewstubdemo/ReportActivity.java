package com.technotalkative.viewstubdemo;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

public class ReportActivity extends MapActivity implements
	LocationListener {

    private MapView mapView = null;
    private LocationManager lm = null;
    private double lat = 0;
    private double lng = 0;
    private MapController mc = null;
    private MyLocationOverlay myLocation = null;
    double lat0;
    double lng0;
    EditText text;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_report);
	mapView = (MapView) this.findViewById(R.id.mapView);
	mapView.setBuiltInZoomControls(true);
	text= (EditText)findViewById(R.id.adresse);
	lm = (LocationManager) this.getSystemService(LOCATION_SERVICE);
	lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
	lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0,
		this);

	mc = mapView.getController();
	mc.setZoom(15);

	myLocation = new MyLocationOverlay(getApplicationContext(), mapView);
	myLocation.disableCompass();
	myLocation.runOnFirstFix(new Runnable() {
	    public void run() {
		mc.animateTo(myLocation.getMyLocation());
		mc.setZoom(17);
		handler.sendEmptyMessage(1);
	    }
	});
	mapView.getOverlays().add(myLocation);
	Log.i("mylocation","avant");
	 
	
	         ImageButton btn_cam = (ImageButton) findViewById(R.id.button_camera);
	   		 
	   		 btn_cam.setOnClickListener(new View.OnClickListener() {
	   				
	   				
	   				public void onClick(View view) {
	   					// Launching News Feed Screen
	   					AlertDialog.Builder alertDialog = new AlertDialog.Builder(ReportActivity.this);
	   					 
	   	                // Setting Dialog Title
	   	                alertDialog.setTitle("vous devez joindre une photo");
	   	 
	   	                // Setting Dialog Message
	   	                alertDialog.setMessage("allez à l'emplacement de la photo");
	   	 
	   	                // Setting Icon to Dialog
	   	                alertDialog.setIcon(R.drawable.gd_action_bar_slideshow);
	   	 
	   	                // Setting Positive "Yes" Button
	   	                alertDialog.setPositiveButton("camera", new DialogInterface.OnClickListener() {
	   	                    public void onClick(DialogInterface dialog, int which) {
	   	                    	Intent i = new Intent(getApplicationContext(), takephoto.class);
	   	            			startActivity(i);
	   	                    }
	   	                });
	   	 
	   	                // Setting Negative "NO" Button
	   	                alertDialog.setNegativeButton("galerie", new DialogInterface.OnClickListener() {
	   	                    public void onClick(DialogInterface dialog, int which) {
	   	                    	Intent i = new Intent(getApplicationContext(), galery.class);
	   	            			startActivity(i);// User pressed No button. Write Logic Here
	   	                    //Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
	   	                    }
	   	                });
	   	 
	   	                // Setting Netural "Cancel" Button
	   	                alertDialog.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
	   	                    public void onClick(DialogInterface dialog, int which) {
	   	                    // User pressed Cancel button. Write Logic Here
	   	                    Toast.makeText(getApplicationContext(), "You clicked on Cancel",
	   	                                        Toast.LENGTH_SHORT).show();
	   	                    }
	   	                });
	   	 
	   	                // Showing Alert Message
	   	                alertDialog.show();
	   				}
	   			});
	       }
	
    

    @Override
    protected void onResume() {
	super.onResume();
	myLocation.enableMyLocation();
	myLocation.enableCompass();
    }

    @Override
    protected boolean isRouteDisplayed() {
	return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_S) {
	    mapView.setSatellite(!mapView.isSatellite());
	    return true;
	}
	return super.onKeyDown(keyCode, event);
    }

    public void onLocationChanged(Location location) {
	lat = location.getLatitude();
	lng = location.getLongitude();
	
	GeoPoint p = new GeoPoint((int) (lat * 1E6), (int) (lng * 1E6));
	mc.animateTo(p);
	mc.setCenter(p);
    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }


    public boolean onTouchEvent(MotionEvent event, MapView mapView) 
    {   
        //---when user lifts his finger---
        if (event.getAction() == 1) {                
            GeoPoint p = mapView.getProjection().fromPixels(
                (int) event.getX(),
                (int) event.getY());

            Geocoder geoCoder = new Geocoder(
                getBaseContext(), Locale.getDefault());
            try {
                List<Address> addresses = geoCoder.getFromLocation(
                    p.getLatitudeE6()  / 1E6, 
                    p.getLongitudeE6() / 1E6, 1);

                String add = "";
                if (addresses.size() > 0) 
                {
                    for (int i=0; i<addresses.get(0).getMaxAddressLineIndex(); 
                         i++)
                       add += addresses.get(0).getAddressLine(i) + "\n";
                }

                Toast.makeText(getBaseContext(), add, Toast.LENGTH_SHORT).show();
            }
            catch (IOException e) {                
                e.printStackTrace();
            }   
            return true;
        }
        else                
            return false;
    }        
    
    
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    private Handler handler = new Handler() {

    	  public void handleMessage(android.os.Message msg) {

    	  
    	   if(msg.what == 1) {

    	    Geocoder geoCoder1 = new Geocoder(
    	      getBaseContext(), Locale.getDefault());
    	    try {

    	     GeoPoint p1 = new GeoPoint(myLocation.getMyLocation().getLatitudeE6(), myLocation.getMyLocation().getLongitudeE6());
    	     List<Address> addresses1 = geoCoder1.getFromLocation(
    	       p1.getLatitudeE6()  / 1E6, 
    	       p1.getLongitudeE6() / 1E6, 1);


    	     String AdresseLocation;
			if (addresses1.size() > 0) 
    	     {
    	      AdresseLocation="";
    	      for (int i=0; i<3; 
    	        i++){

    	       AdresseLocation += addresses1.get(0).getAddressLine(i) + "  ";
    	      }
    	     }else{AdresseLocation="Adresse Actuelle inconnue";}

    	     text.setHint(AdresseLocation);
    	    }
    	    catch (IOException e) {  

    	     e.printStackTrace();
    	    } 


    	   }

    	  };

    	 };
}
