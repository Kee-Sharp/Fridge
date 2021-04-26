package com.team300.fridge.POJOs;

import android.os.Parcel;
import android.os.Parcelable;

import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class CostSummary extends RealmObject implements Parcelable {
    @Required
    private String object_id;

    @PrimaryKey
    private ObjectId _id;

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
            Model model = Model.getInstance();
            this.object_id = model.getCurrentUser().getEmail();
            this._id = ObjectId.get();
            this.month_year = month_year;
            this.monthly_cost = monthly_cost;
            this.monthly_waste = monthly_cost;
        }
    }
    public CostSummary(){
        this(new Date(), 0, 0);
    }

    private CostSummary(Parcel in){
        object_id = in.readString();
        _id = (ObjectId) in.readSerializable();
        month_year = (Date)in.readSerializable();
        monthly_cost = in.readFloat();
        monthly_waste = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString(object_id);
        out.writeSerializable(_id);
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
