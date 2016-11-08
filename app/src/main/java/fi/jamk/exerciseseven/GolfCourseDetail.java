package fi.jamk.exerciseseven;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

/**
 * Created by Ultoros on 4.11.2016.
 */

public class GolfCourseDetail extends AppCompatActivity {

    private GolfCourse selected;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.golf_detail);
        selected = (GolfCourse) getIntent().getSerializableExtra("selected");
        setContent();
        setTitle(selected.field);
    }

    public void setContent(){
        TextView address = (TextView) findViewById(R.id.fieldDetailAddressTextView);
        TextView phone = (TextView) findViewById(R.id.fieldDetailPhoneTextView);
        TextView email = (TextView) findViewById(R.id.fieldDetailEmailTextView);
        TextView description = (TextView) findViewById(R.id.fieldDetailDescriptionTextView);
        TextView webSite = (TextView) findViewById(R.id.fieldDetailUrlTextView);
        Button latlng = (Button) findViewById(R.id.fieldDetailLatLngButton);
        ImageView imageUrl = (ImageView) findViewById(R.id.fieldDetailImageView);

        new DownloadImageTask(imageUrl).execute(selected.imageSite + selected.imageUrl);

        address.setText(selected.address);
        phone.setText(selected.phonenumber);
        email.setText(selected.email);
        description.setText(selected.description);
        webSite.setText(selected.webSite);
        latlng.setText("Show location on map");
    }

    public void openFieldLocation(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:"+selected.lat+","+selected.lng+ "?q=" +selected.lat+","+selected.lng+ "( " +selected.field + ")"));
        intent.setPackage("com.google.android.apps.maps");
        if (intent.resolveActivity(getPackageManager()) == null) {
            intent.setPackage("com.here.app.maps");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }else{
                Toast.makeText(getApplicationContext(), "Install Google Maps or Here maps first", Toast.LENGTH_LONG ).show();
            }
        }else{
            startActivity(intent);
        }

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView fieldImage;

        public DownloadImageTask(ImageView mImage) {
            this.fieldImage = mImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            fieldImage.setImageBitmap(result);
        }
    }

}
