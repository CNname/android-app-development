package fi.jamk.uicontrols;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class GPSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gps);
    }
    public void showMap(View view){
        // get Latitude and longitude. Display map after that
        EditText editText1 = (EditText) findViewById(R.id.gps_textLat);
        EditText editText2 = (EditText) findViewById(R.id.gps_textLong);
        String numberOne = editText1.getText().toString();
        String numberTwo = editText2.getText().toString();
        double lat = Double.parseDouble(numberOne);
        double lng = Double.parseDouble(numberTwo);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:"+lat+","+lng));
        startActivity(intent);
    }
}
