package com.android.orange;

import com.android.orange.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
 
public class fullscreen extends Activity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
 
        // get intent data
        Intent i = getIntent();
 
        // Selected image id
        int position = i.getExtras().getInt("id");
        ImageAdapter imageAdapter = new ImageAdapter(this);
        Bitmap myBitmap = BitmapFactory.decodeFile(imageAdapter.mlistFiles[position].getAbsolutePath());
       
        ImageView imageView = (ImageView) findViewById(R.id.imageView1);
        imageView.setImageBitmap(myBitmap); 
        Intent i1=new Intent(getBaseContext(),FormActivity.class);
        startActivity(i1);
    }
 //public File[] mlistFiles = new File("/sdcard/dcim/camera").listFiles()
}