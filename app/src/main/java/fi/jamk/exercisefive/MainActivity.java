package fi.jamk.exercisefive;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextEntryDialogFragment.TextEntryDialogListener {

    private final String TEXTVIEW_STATEKEY = "TEXTVIEW_STATEKEY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView) findViewById(R.id.textview);
        if(savedInstanceState != null){
            if(savedInstanceState.containsKey(TEXTVIEW_STATEKEY)){
                String text = savedInstanceState.getString(TEXTVIEW_STATEKEY);
                textView.setText(text);
            }
        }
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        TextView textView = (TextView) findViewById(R.id.textview);
        savedInstanceState.putString(TEXTVIEW_STATEKEY, textView.getText().toString());
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String text){
        TextView textView = (TextView) findViewById(R.id.textview);
        textView.setText(text);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog){
        Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
    }

    public void buttonClicked(View view) {
        TextEntryDialogFragment eDialog = new TextEntryDialogFragment();
        eDialog.show(getFragmentManager(), "Text Dialog");
    }

  @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
