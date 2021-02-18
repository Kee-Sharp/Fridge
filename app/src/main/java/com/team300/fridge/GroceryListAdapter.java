package com.team300.fridge;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.List;

public class GroceryListAdapter
        extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {


    private List<GroceryList> mGroceryLists;
    private String userInput;
    /**
     * This inner class represents a ViewHolder which provides us a way to cache information
     * about the binding between the model element (in this case a GroceryList) and the widgets in
     * the list view (in this case the two TextViews)
     */

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mDateView;
        public GroceryList mGroceryList;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.name);
            mDateView = (TextView) view.findViewById(R.id.createdOn);
        }
            // return name of list
        @Override
        public String toString(){ return super.toString() + " '" + mNameView.getText() + "'";}

    }
    /**
     * set the items to be used by the adapter
     * @param groceryLists the list of grocery lists to be displayed in the recycler view
     */
    public GroceryListAdapter(List<GroceryList> groceryLists) { mGroceryLists = groceryLists;}
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        /*
              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/grocery_row_item.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.grocery_row_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final Model model = Model.getInstance();
        /*
        This is where we have to bind each data element in the list (given by position parameter)
        to an element in the view (which is one of our two TextView widgets
         */
        //start by getting the element at the correct position
        GroceryList item = mGroceryLists.get(position);
        viewHolder.mGroceryList = item;
        /*
          Now we bind the data to the widgets.  In this case, pretty simple, put the date in
          one textview and the name of a grocery list in the other.
         */
        viewHolder.mDateView.setText("" + item.getCreatedOn());
        viewHolder.mNameView.setText(item.getName());

        /*
         * set up a listener to handle if the user clicks on this list item, what should happen?
         */
        viewHolder.mView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //get view context
                Context context = v.getContext();
                //create popup with delete options
                PopupMenu popup = new PopupMenu(context, v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.rename_menu, popup.getMenu());
                popup.show();
                //handle which option the user selects
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.rename:
                                //rename
                                String newName = rename(context);
                                int listId = viewHolder.getAdapterPosition();
                                mGroceryLists.get(listId).setName(newName);
                                return true;
                            default:
                                return false;
                        }
                    }
                });
            }
        });
    }

    public String rename(Context context) {
        //create an alert dialogue with an edittext component to get new name from user
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Rename");
        //input
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        //buttons
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i){
                userInput = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        builder.show();
        return userInput;
    }

    @Override
    public int getItemCount() {
        return mGroceryLists.size();
    }
}
