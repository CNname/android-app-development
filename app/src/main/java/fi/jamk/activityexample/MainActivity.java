package fi.jamk.activityexample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int SHOW_NEWACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startActivity(View view){
        // find text that EndUser has typed
        EditText editText = (EditText) findViewById(R.id.editText);
        String line = editText.getText().toString();
        Intent intent = new Intent(this, NewActivity.class);
        intent.putExtra("text",line); // string
        startActivity(intent);
    }

    public void startActivityResult(View view){
        Intent intent = new Intent(this, NewActivity.class);
        startActivityForResult(intent,SHOW_NEWACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == SHOW_NEWACTIVITY && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            int result = extras.getInt("result"); // this is 20.
            Toast.makeText(this,"Result is = " + result, Toast.LENGTH_LONG).show();
        }
    }
}
