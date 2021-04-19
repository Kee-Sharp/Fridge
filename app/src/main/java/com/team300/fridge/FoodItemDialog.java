package com.team300.fridge;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDate;

public class FoodItemDialog extends AppCompatDialogFragment {
    private EditText editTextQuantity;
    private EditText editTextDate;
    private FoodItemDialogListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String name = getArguments().getString("Food_Item_Name");
        int id = getArguments().getInt("Product_id");
        //TODO: Once we have brand information show that as well
        //getArguments().getString("Food_Item_Brand");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_food_item_dialog, null);
        builder.setView(view)
                .setTitle(name)
                .setNegativeButton("cancel", (dialog, which) -> {})
                .setPositiveButton("confirm", (dialog, which) -> {});

        editTextQuantity = view.findViewById(R.id.edit_quantity);
        editTextDate = view.findViewById(R.id.edit_date);

        AlertDialog dialog = builder.create();
        dialog.show();
        Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setOnClickListener(new CustomListener(dialog, name, id));
        dialog.dismiss();
        return dialog;
    }

    //We need a custom OnClickListener to be able to validate input before dismissing the dialog
    public class CustomListener implements View.OnClickListener {
        private final Dialog dialog;
        private String name;
        private int id;

        public CustomListener(Dialog dialog, String name, int id) {
            this.dialog = dialog;
            this.name = name;
            this.id = id;
        }
        @Override
        public void onClick(View v) {
            boolean error = false;
            String quantity = editTextQuantity.getText().toString();
            String dateString = editTextDate.getText().toString();
            //quantity error handling
            if (Integer.parseInt(quantity) <= 0) {
                editTextQuantity.setError("Invalid quantity: must be > 0");
                error = true;
            }
            //date error handling
            if (!validate(dateString)) {
                editTextDate.setError("Invalid date format: mm/dd/yyyy");
                error = true;
            } else {
                String[] separated = dateString.split("/");
                int month = Integer.parseInt(separated[0]);
                int day = Integer.parseInt(separated[1]);
                int year = Integer.parseInt(separated[2]);
                try {
                    LocalDate date = LocalDate.of(year, month, day);
                } catch (java.time.DateTimeException e) {
                    editTextDate.setError("Invalid date");
                    error = true;
                }
            }
            if (!error) {
                mListener.saveInformation(name, id, quantity, dateString);
                dialog.dismiss();
            }
        }
    }

    //make sure that the string inputted is formatted like a date
    private boolean validate(String s) {
        return s.matches("[01]\\d/[0123]\\d/\\d{4}");
    }

    //The activity is linked to the dialog as its' listener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (FoodItemDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement FoodItemDialogListener");
        }
    }

    public interface FoodItemDialogListener {
        void saveInformation(String name, int _id, String quantity, String date);
    }
}
