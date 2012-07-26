package com.technotalkative.viewstubdemo;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class galery extends Activity {

   

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.layout.activity_galery, menu);
        return true;
    }
    

    





        
        
        
   
   @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_galery);
                
               
                
                GridView gridview = (GridView) findViewById(R.id.gridview);
                gridview.setAdapter(new ImageAdapter(this));
                
        }
        
        public class ImageAdapter extends BaseAdapter {
        		public File[] mlistFiles = new File("/sdcard/dcim/camera").listFiles();
        		
                private Context mContext;

                public ImageAdapter(Context c) {
                        mContext = c;
                }

                public int getCount() {
                        return mlistFiles.length;
                }

                public Object getItem(int position) {
                        return position;
                }

                public long getItemId(int position) {
                        return position;
                }

                public View getView(int position, View convertView, ViewGroup parent) {
                	
                        ImageView imageView;
                        if (convertView == null) {
                                imageView = new ImageView(mContext);
                                imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
                                
                                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                imageView.setPadding(8, 8, 8, 8);
                        } else {
                                imageView = (ImageView) convertView;
                        }
                        
                        BitmapFactory.Options options=new BitmapFactory.Options();
                        options.inSampleSize = 20;

                        imageView.setImageBitmap(BitmapFactory.decodeFile(mlistFiles[position].getPath(), options));
                       
                     
                        return imageView;
                }

        }

}
