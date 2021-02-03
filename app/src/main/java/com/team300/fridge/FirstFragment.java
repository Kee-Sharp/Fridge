package com.team300.fridge;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import java.util.List;

import androidx.navigation.fragment.NavHostFragment;

public class FirstFragment extends Fragment {

    protected RecyclerView mRecyclerView;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        //Step 1.  Setup the recycler view by getting it from our layout in the main window
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.items);

        //Step2. Setup the layout manager and add it to the recycler view
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //Step 3.  Hook up the adapter to the view
        Model model = Model.getInstance();
        List<FoodItem> items = model.getFoodItems();
        mRecyclerView.setAdapter(new SimpleFoodItemRecyclerViewAdapter(items));
        Log.d("Fragment", items.toString());

        return rootView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        View view = getView();
//
//        //Step 1.  Setup the recycler view by getting it from our layout in the main window
//        View recyclerView = view.findViewById(R.id.items);
//        assert recyclerView != null;
//        //Step 2.  Hook up the adapter to the view
//        Model model = Model.getInstance();
//        List<FoodItem> items = model.getFoodItems();
//
//        //If fridge is empty show text and hide list
////        TextView text = view.findViewById(R.id.textview_first);
////        if (items.size() == 0) {
////            text.setVisibility(View.VISIBLE);
////            recyclerView.setVisibility(View.GONE);
////        }
//    }


}