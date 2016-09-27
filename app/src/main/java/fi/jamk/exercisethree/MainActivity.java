package fi.jamk.exercisethree;

import android.app.DialogFragment;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements ClockDialogFragment.ClockDialogListener {

    private int input_hour = 0;
    private int input_minute = 0;
    private int notification_id = 1;
    private boolean timerStatus = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


    }

    public void clockDialog(View view) {
        ClockDialogFragment eDialog = new ClockDialogFragment();
        eDialog.show(getFragmentManager(), "Tell this man the time");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, int hour, int minute) {
        input_hour = hour;
        input_minute = minute;
        countTime();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        //Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
    }

    public void countTime(){

        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minutes = c.get(Calendar.MINUTE);

        final TextView textView = (TextView) findViewById(R.id.textView);
        int timeToWait = 0;
        int timetoWaitMinutes = 0;
        if(input_minute <= minutes){
            timetoWaitMinutes = minutes - input_minute;
        }else if (input_minute > minutes){
            timetoWaitMinutes = input_minute - minutes;
        }

        if(input_hour < hour){
            timeToWait = 24 - hour + input_hour;
        }else if(input_hour > hour) {
            timeToWait = input_hour - hour;
        }else if(input_hour == hour){
            timeToWait = 0;
        }
        timeToWait = (timeToWait * 60 + timetoWaitMinutes) * 60 * 1000;

        // start the timer
        if(timerStatus != true) {
            makeNotification();
            CountDownTimer timer = new CountDownTimer(timeToWait, 1000) {

                public void onTick(long millisUntilFinished) {
                    timerStatus = true;
                    textView.setText("I still have to wait " + millisUntilFinished / 1000 + " seconds");
                }

                public void onFinish() {
                    timerStatus = false;
                    textView.setText("Oh it's here, thanks");
                    NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.cancel(notification_id);
                }
            }.start();
            //timer.cancel();
        }
    }
    public void makeNotification() {
        // create a notification
        Notification notification = new Notification.Builder(this)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .setContentTitle("Clock is ticking")
                .setContentText("Man is waiting.. impatiently")
                .setSmallIcon(R.drawable.clock)
                .setAutoCancel(true)
                .setVisibility(Notification.VISIBILITY_PUBLIC).build();
        // connect notification manager
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // make a new notification with a new unique id
        notification_id++;
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(notification_id, notification);
    }
}
