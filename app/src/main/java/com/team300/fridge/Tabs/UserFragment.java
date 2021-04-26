package com.team300.fridge.Tabs;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.team300.fridge.AboutActivity;
import com.team300.fridge.FinanceTrackerActivity;
import com.team300.fridge.NotificationUtils;
import com.team300.fridge.OpenSettingsDialogFragment;
import com.team300.fridge.POJOs.AppUser;
import com.team300.fridge.R;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    private static final String USER_ARG = "user";
    private AppUser mUser;
    private NotificationUtils mNotificationUtils;
    CheckBox[] checkBoxes;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param appUser The user for this fragment.
     * @return A new instance of fragment UserFragment.
     */
    public static UserFragment newInstance(AppUser appUser) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER_ARG, appUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getView();

        if (getArguments() != null) {
            mUser = getArguments().getParcelable(USER_ARG);
        }
        mNotificationUtils = new NotificationUtils(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        assert view != null;

        TextView firstName = view.findViewById(R.id.firstName);
        firstName.setText(mUser.getName().split(" ")[0]);
        TextView lastName = view.findViewById(R.id.lastName);
        lastName.setText(mUser.getName().split(" ")[1]);
        TextView email = view.findViewById(R.id.email);
        email.setText(mUser.getEmail());

        checkBoxes = new CheckBox[7];
        checkBoxes[0] = view.findViewById(R.id.monday);
        checkBoxes[1] = view.findViewById(R.id.tuesday);
        checkBoxes[2] = view.findViewById(R.id.wednesday);
        checkBoxes[3] = view.findViewById(R.id.thursday);
        checkBoxes[4] = view.findViewById(R.id.friday);
        checkBoxes[5] = view.findViewById(R.id.saturday);
        checkBoxes[6] = view.findViewById(R.id.sunday);

        //pre-select the dates according to the user's settings
        DayOfWeek[] allDates = {DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY};
        List<DayOfWeek> notificationDates = mUser.getNotificationDates();
        for (int i = 0; i < 7; i++) {
            if (notificationDates.contains(allDates[i])) {
                checkBoxes[i].setChecked(true);
            } else {
                checkBoxes[i].setChecked(false);
            }
        }

        Button demoNotification = view.findViewById(R.id.demo);
        demoNotification.setOnClickListener((v)->{
            //for demonstration purposes, we have to trigger a notification
            //set up a notification for 5 seconds later if today is one of the notification dates
            NotificationChannel myNotificationChannel = mNotificationUtils.getManager().getNotificationChannel(NotificationUtils.ANDROID_CHANNEL_ID);
            //check if the user has enabled heads-up notifications
            if (myNotificationChannel.getImportance() == NotificationManager.IMPORTANCE_HIGH) {
                LocalDate today = LocalDate.now();
                Log.v("DATE", today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.US));
                //check if today is marked as a notification day
                if (notificationDates.contains(today.getDayOfWeek())) {
                    Context context = demoNotification.getContext();
                    createNotification(context);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "No Notifications Today", Toast.LENGTH_SHORT).show();
                }
            } else {
                //otherwise their is a user setting preventing the heads-up notification, open dialog to
                //ask user to change it
                OpenSettingsDialogFragment dialog = new OpenSettingsDialogFragment();
                Bundle args = new Bundle();
                args.putString("Channel_Id", myNotificationChannel.getId());
                dialog.setArguments(args);
                dialog.show(getActivity().getSupportFragmentManager(), "Open Settings Dialog");
            }
        });

        //show finance tracker activity
        Button finance = view.findViewById(R.id.financeButton);
        finance.setOnClickListener(this::onClick);

        //show about the app activity
        Button about = view.findViewById(R.id.about);
        about.setOnClickListener(this::onClickTwo);
    }


    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        DayOfWeek day = DayOfWeek.MONDAY;
        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.monday:
                day = DayOfWeek.MONDAY;
                break;
            case R.id.tuesday:
                day = DayOfWeek.TUESDAY;
                break;
            case R.id.wednesday:
                day = DayOfWeek.WEDNESDAY;
                break;
            case R.id.thursday:
                day = DayOfWeek.THURSDAY;
                break;
            case R.id.friday:
                day = DayOfWeek.FRIDAY;
                break;
            case R.id.saturday:
                day = DayOfWeek.SATURDAY;
                break;
            case R.id.sunday:
                day = DayOfWeek.SUNDAY;
                break;
        }
        if (checked) {
            mUser.addDate(day);
        } else {
            mUser.removeDate(day);
        }
    }

    //DEMO-NOTIF
    //notif icon drawable for custom icon if we want
    private void createNotification(Context context){
        String notifMsg = "";
        /* NOTIFICATION SHELL FOR INTEGRATION WITH DATABASE---------------
        //int expireItems = 0;
        // expireItems = TODO: add call to retrieve # of items expiring in the fridge within 3 days
        // String expireItemsNames = TODO: get the names of the items
       if(expireItems == 0){
            notifMsg = "You have no items expiring soon, nice!";
        } else {
            notifMsg = "You have " + expireItems + " items expiring soon. "; //+ "These items are: " + expireItemsNames;
        }
        */
        //DUMMY NOTIFICATION---------------
        Random rand = new Random();
        int msgNum = rand.nextInt(3);
        if(msgNum == 0){
            notifMsg = "You have no items expiring soon, nice!";
        } else if (msgNum == 1) {
            notifMsg = "You have 3 items expiring soon: Apple, Broccoli, Bread";
        } else {
            notifMsg = "You have 1 item expiring soon: Milk";
        }
        //Notification object creation
        Notification.Builder builder = mNotificationUtils.getAndroidChannelNotification("What's in your Fridge?", notifMsg);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mNotificationUtils.getManager().notify(0, builder.build());
            }
        }, 5000);
    }

    private void onClick(View v) {
        startActivity(new Intent(getActivity(), FinanceTrackerActivity.class));
    }

    private void onClickTwo(View v) {
        startActivity(new Intent(getActivity(), AboutActivity.class));
    }
}