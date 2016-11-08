package fi.jamk.exerciseseven;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;

public class ScrollingActivity extends AppCompatActivity {

    private JSONArray golf_courses;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    FetchDataTask task = new FetchDataTask();
    private List<GolfCourse> mGolfCourseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        task.execute("http://student.labranet.jamk.fi/~H3409/Android/JSON/golf_courses.json");
    }

    public void initSoftware(){
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // connect recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.golfCourseRecycleView);
        // create layoutManager
        mLayoutManager = new LinearLayoutManager(this);
        // set manager to recycler view
        mRecyclerView.setLayoutManager(mLayoutManager);
        // create adapter
        mAdapter = new GolfCourseAdapter(mGolfCourseList);
        // set adapter to recycler view
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    class FetchDataTask extends AsyncTask<String, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(String... urls) {
            HttpURLConnection urlConnection = null;
            JSONObject json = null;
            try {
                URL url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                json = new JSONObject(stringBuilder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) urlConnection.disconnect();
            }
            return json;
        }

        protected void onPostExecute(JSONObject json) {

            mGolfCourseList = new ArrayList<>();
            try {
                golf_courses = json.getJSONArray("kentat");
                for (int i = 0; i < golf_courses.length(); i++) {
                    //Log.e("loading JSON", ""+golf_courses.length());
                    try {
                        JSONObject golf = golf_courses.getJSONObject(i);
                        GolfCourse golfCourse = new GolfCourse(
                                golf.getString("Tyyppi"),
                                golf.getDouble("lat"),
                                golf.getDouble("lng"),
                                golf.getString("Kentta"),
                                golf.getString("Puhelin"),
                                golf.getString("Sahkoposti"),
                                golf.getString("Osoite"),
                                golf.getString("Kuva"),
                                golf.getString("Kuvaus"),
                                golf.getString("Webbi")

                        );
                       // Log.e("Latitude JSON", ""+golf.getDouble("lat"));
                        mGolfCourseList.add(golfCourse);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            initSoftware();
        }

    }


}
