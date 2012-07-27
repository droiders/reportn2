package com.android.orange;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class takephoto extends Activity implements SurfaceHolder.Callback,
PictureCallback, ShutterCallback {
    /** Called when the activity is first created. */
    private Uri ur;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Camera camera;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_takephoto);
        surfaceView = (SurfaceView)findViewById(R.id.surface_view);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        Button prendrePhoto = (Button) findViewById(R.id.btn_prendrePhoto);
        prendrePhoto.setOnClickListener(new View.OnClickListener() {
        public void onClick(View viewParam) {
        camera.takePicture(takephoto.this, null,takephoto.this);
        Intent i = new Intent(getBaseContext(),FormActivity.class);
        i.putExtra("uri", ur);
        startActivity(i);
        }
        });
    }
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
			if (camera != null) {
			camera.stopPreview();
			Camera.Parameters p = this.camera.getParameters();
			p.setPreviewSize(width, height);
			this.camera.setParameters(p);
			try {
			this.camera.setPreviewDisplay(holder);
			this.camera.startPreview();
			} catch (IOException e) {
			Log.e(getClass().getSimpleName(),
			"Erreur E/S lors du setPreviewDisplay sur l’objet Camera", e);
			}
			}
			}
			public void surfaceCreated(SurfaceHolder holder) {
			camera = Camera.open();
			}
			public void surfaceDestroyed(SurfaceHolder holder) {
			if (camera != null) {
			camera.stopPreview();
			camera.release();
			}
			}
			public void onPictureTaken(byte[] data, Camera arg1) { 
				ContentValues values = new ContentValues();
				values.put(Media.TITLE, "Mon image");
				values.put(Media.DESCRIPTION, "Image prise par le téléphone");
				Uri uri = getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI,values); 
				ur=uri;
				Log.i("info", uri.toString());
				OutputStream os;
				try {
				os = getContentResolver().openOutputStream(uri);
				os.write(data);
				os.flush();
				os.close();
				} catch (FileNotFoundException e) {
				Log.e(getClass().getSimpleName(), "Fichier non trouvé à l’écriture de l’image", e);
				} catch (IOException e) {
				Log.e(getClass().getSimpleName(), "Erreur E/S à l’enregistrement de	l’image", e);
				}
				camera.startPreview();
				}
				public void onShutter() { 
				Log.d(getClass().getSimpleName(), "Clic clac !");
				}
				
}