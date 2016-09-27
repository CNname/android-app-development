package fi.jamk.exercisethree;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;


/**
 * Created by Ultoros on 26.9.2016.
 */

public class ClockDialogFragment extends DialogFragment {

    private int hour = 0;
    private int minute = 0;

    public interface ClockDialogListener{
        void onDialogPositiveClick(DialogFragment dialog, int hour, int minute);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    ClockDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the ClockDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the ClockDialogListener so we can send events to the host
            mListener = (ClockDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString() + " must implement ClockDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // create a new AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View dialogView = inflater.inflate(R.layout.clock_dialog, null);
        builder.setView(dialogView)
                // Set title
                .setTitle("Tell this man the time")
                // Add action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        TimePicker timePicker = (TimePicker) dialogView.findViewById(R.id.timePicker);

                        if (Build.VERSION.SDK_INT >= 23) {
                            hour = timePicker.getHour();
                            minute = timePicker.getHour();
                        }else{
                            hour = timePicker.getCurrentHour();
                            minute = timePicker.getCurrentMinute();
                        }
                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(ClockDialogFragment.this,hour, minute);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(ClockDialogFragment.this);
                    }
                });
        return builder.create();
    }
}
