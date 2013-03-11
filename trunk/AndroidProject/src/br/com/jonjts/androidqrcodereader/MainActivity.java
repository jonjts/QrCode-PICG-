package br.com.jonjts.androidqrcodereader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import projectpicg.QrCode;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {
	
	private ImageView imgQr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imgQr = (ImageView) findViewById(R.id.imgQr);
		startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), 0);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Bundle bundle = data.getExtras();
		if(bundle != null && bundle.containsKey("data")){
			Bitmap  bitmap = (Bitmap) bundle.get("data");
			imgQr.setImageBitmap(bitmap);
			Uri image = getLastImage();
			File file = new File(image.getPath());
			
			
			byte[] image1;
			try {
				image1 = QrCode.decode(file);
				InputStream is = new ByteArrayInputStream(image1);
				Bitmap bmp = BitmapFactory.decodeStream(is);
				imgQr.setImageBitmap(bmp);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	private Uri getLastImage() {
        final ContentResolver cr = getContentResolver();
        final String[] p1 = new String[] {
                MediaStore.Images.ImageColumns._ID,
                MediaStore.Images.ImageColumns.DATE_TAKEN                   
        };
        Cursor c1 = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, p1, null, null,
                p1[1] + " DESC");

        int imageID = 0;        
        if ( c1.moveToFirst() ) {
            imageID = c1.getInt(0);
        }       
        c1.close();
        Uri uri = Uri.withAppendedPath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(imageID));
        return uri;
    }
	

}
