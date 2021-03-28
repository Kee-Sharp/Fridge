package com.team300.fridge;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.Button;

import java.security.InvalidParameterException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class User extends AppCompatActivity implements Parcelable {

    private String name;
    private String email;
    private List<DayOfWeek> notificationDates;

    public User(String name, String email) {
        if (name == null) {
            throw new InvalidParameterException("Name must not be null");
        } else if (email == null || !email.matches("^.+@.+\\..+")) {
            throw new InvalidParameterException("Invalid email");
        } else {
            this.name = name;
            this.email = email;
            this.notificationDates = new ArrayList<>();
        }
    }

    //create User from Parcel information
    private User(Parcel in) {
        name = in.readString();
        email = in.readString();
        notificationDates = new ArrayList<>();
        String[] dates = (String[]) in.readArray(String.class.getClassLoader());
        for (String date : dates) {
            notificationDates.add(DayOfWeek.valueOf(date));
        }

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        List<String> dates = new ArrayList<>();
        for (DayOfWeek day : notificationDates) {
            dates.add(day.toString());
        }
        dest.writeArray(dates.toArray());
    }

    //need creator field for new items
    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>(){
        @Override
        public User createFromParcel(Parcel in){
            return new User(in);
        }
        @Override
        public User[] newArray(int size){
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<DayOfWeek> getNotificationDates() {
        return notificationDates;
    }

    //returns true if the new day was added
    public boolean addDate(DayOfWeek dow) {
        if (notificationDates.contains(dow)) {
            return false;
        } else {
            notificationDates.add(dow);
            Collections.sort(notificationDates);
            return true;
        }
    }

    public boolean removeDate(DayOfWeek dow) {
        return notificationDates.remove(dow);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button finance = findViewById(R.id.financeButton);
        finance.setOnClickListener((view)->{
            setContentView(R.layout.activity_view_finance);
        });
    }


}
