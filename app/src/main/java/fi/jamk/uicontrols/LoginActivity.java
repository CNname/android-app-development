package fi.jamk.uicontrols;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.login);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                new String[]{"Pasi", "Juha", "Jouni", "Esa", "Hannu"});
        actv.setAdapter(aa);
    }

    public void onLogin(View view){
        AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.login);
        String usernameText = autoCompleteTextView.getText().toString();
        EditText editText = (EditText) findViewById(R.id.editPassword);
        String toastMessage = editText.getText().toString();
        Toast.makeText(getApplicationContext(), usernameText + " " + toastMessage, Toast.LENGTH_SHORT).show();
    }
}
