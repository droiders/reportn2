package com.technotalkative.viewstubdemo;

import java.io.FileNotFoundException;
import java.io.InputStream;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class takephoto extends Activity {
	
	//CONSTANTS
	
	private static final int REQ_CODE_PHOTO_TAKE = 1;	
    
	
	
	//METHODS
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takephoto);
        
		findViewById(R.id.btn_take_photo).setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(i, REQ_CODE_PHOTO_TAKE);
			}
		});        
    }
    
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);	
		
		if(resultCode != RESULT_OK) return;
		
		switch (requestCode) {
		case REQ_CODE_PHOTO_TAKE:
			Uri u = data.getData();
			onPhotoReturned(u);
	    	break;	    	
		default:
			break;
		}
	}
    
	private void onPhotoReturned(Uri u) {
		InputStream is = null;
		try {
			ContentResolver cResolver = getContentResolver();
			is = cResolver.openInputStream(u);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BitmapDrawable bmp = new BitmapDrawable(is);	
		ImageView mainImage = (ImageView)findViewById(R.id.img_main);
		mainImage.setImageDrawable(bmp);
	}
    
    
}