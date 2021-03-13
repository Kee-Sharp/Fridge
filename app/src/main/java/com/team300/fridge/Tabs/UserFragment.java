package com.team300.fridge.Tabs;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.team300.fridge.NotificationUtils;
import com.team300.fridge.R;
import com.team300.fridge.User;

import java.time.DayOfWeek;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {

    private static final String USER_ARG = "user";
    private User mUser;
    private NotificationUtils mNotificationUtils;
    CheckBox[] checkBoxes;

    public UserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user The user for this fragment.
     * @return A new instance of fragment UserFragment.
     */
    public static UserFragment newInstance(User user) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        args.putParcelable(USER_ARG, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        TextView name = view.findViewById(R.id.name);
        name.setText(mUser.getName());
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
            //set up a notification for 15 seconds later
            Context context = demoNotification.getContext();
            createNotification(context);
        });
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
    //create notification for non-demo will need to take in values from notificationDates as well
    //notif icon drawable for custom icon if we want
    private void createNotification(Context context){
        System.out.println("Create notification");
        String notifMsg = "It's time to check your fridge before your food goes bad!";
        //Notification object creation
        Notification.Builder builder = mNotificationUtils.getAndroidChannelNotification("What's in your Fridge?", notifMsg);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                mNotificationUtils.getManager().notify(0, builder.build());
            }
        }, 15000);
    }

}