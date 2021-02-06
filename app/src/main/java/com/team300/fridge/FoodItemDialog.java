package com.team300.fridge;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
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
        //getArguments().getString("Food_Item_Brand");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.layout_food_item_dialog, null);
        builder.setView(view)
                .setTitle(name)
                .setNegativeButton("cancel", (dialog, which) -> {

                })
                .setPositiveButton("confirm", (dialog, which) -> {

                });
        editTextQuantity = view.findViewById(R.id.edit_quantity);
        editTextDate = view.findViewById(R.id.edit_date);

        AlertDialog dialog = builder.create();
        dialog.show();
        Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setOnClickListener(new CustomListener(dialog, name));
        dialog.dismiss();
        return dialog;
    }
    public class CustomListener implements View.OnClickListener {
        private final Dialog dialog;
        private String name;

        public CustomListener(Dialog dialog, String name) {
            this.dialog = dialog;
            this.name = name;
        }
        @Override
        public void onClick(View v) {
            String date = editTextDate.getText().toString();
            if (validate(date)) {
                String quantity = editTextQuantity.getText().toString();
                mListener.saveInformation(name, quantity, date);
                dialog.dismiss();
            } else {
                editTextDate.setError("Invalid date format: mm/dd/yyyy");
            }
        }
    }
    private boolean validate(String s) {
        if (s.matches("[01]\\d/[0123]\\d/\\d{4}")) {
            String[] separated = s.split("/");
            int month = Integer.parseInt(separated[0]);
            int day = Integer.parseInt(separated[1]);
            return (month != 0 && day != 0);
        }
        return false;
    }
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
        void saveInformation(String name, String quantity, String date);
    }
}
