package fi.jamk.ex5shoppinglist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Ultoros on 10.10.2016.
 */

public class AddProductDialogFragment extends DialogFragment{

    public interface DialogListener {
        void onDialogPositiveClick(DialogFragment dialog, String productName, int count, float price );
        void onDialogNegativeClick(DialogFragment dialog);
    }

    DialogListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement DialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // create AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.product_dialog, null);
        builder.setView(dialogView)
                .setTitle("Add a new Product")
                // Add action buttons
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // find a name and score
                        EditText editName = (EditText) dialogView.findViewById(R.id.productName);
                        String name = editName.getText().toString();
                        EditText editCount = (EditText) dialogView.findViewById(R.id.count);
                        int count = Integer.valueOf(editCount.getText().toString());

                        EditText editPrice = (EditText) dialogView.findViewById(R.id.price);
                        float price = Float.valueOf(editPrice.getText().toString());

                        // Send the positive button event back to the host activity
                        mListener.onDialogPositiveClick(AddProductDialogFragment.this,name,count,price);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Send the negative button event back to the host activity
                        mListener.onDialogNegativeClick(AddProductDialogFragment.this);
                    }
                });
        return builder.create();
    }

}
