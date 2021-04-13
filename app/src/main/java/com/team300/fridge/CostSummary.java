package com.team300.fridge;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CostSummary extends RealmObject implements Parcelable {
    @Required
    private String object_id = "user1";

    @PrimaryKey
    private int _id;

    private Date month_year;

    private float monthly_cost;
    private int monthly_waste;
//    private RealmList<Products> monthly_foods;

    @Override
    public int describeContents(){
        return this.hashCode();
    }

    public CostSummary(Date month_year, int monthly_cost, int monthly_waste){
        if (month_year != null) {
            this.month_year = month_year;
            this.monthly_cost = monthly_cost;
            this.monthly_waste = monthly_cost;
        }
    }
    public CostSummary(){
        this(new Date(), 0, 0);
    }

    private CostSummary(Parcel in){
        _id = in.readInt();
        month_year = (Date)in.readSerializable();
        monthly_cost = in.readFloat();
        monthly_waste = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeInt(_id);
        out.writeSerializable((Serializable)month_year);
        out.writeFloat(monthly_cost);
        out.writeInt(monthly_waste);
    }

    public static final Parcelable.Creator<CostSummary> CREATOR
            = new Parcelable.Creator<CostSummary>(){
        @Override
        public CostSummary createFromParcel(Parcel in){
            return new CostSummary(in);
        }

        @Override
        public CostSummary[] newArray(int size){
            return new CostSummary[size];
        }
    };
}
