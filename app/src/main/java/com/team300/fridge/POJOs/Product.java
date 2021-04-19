package com.team300.fridge.POJOs;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Product extends RealmObject implements Parcelable, Serializable {
    @Required
    private String object_id = "PUBLIC";

//    @Required
    @PrimaryKey
    private int _id;

    @Required
    private String name;

    private String name_sub;
    private String category;
    private String keywords;
    private int pantry_open_exp;
    private int pantry_closed_exp;
    private int fridge_open_exp;
    private int fridge_closed_exp;
    private int freezer_open_exp;
    private int freezer_closed_exp;
    private double price;

    @Override
    public int describeContents(){
        return this.hashCode();
    }


    /**
     *  Creates a new FoodKeeper product
     * @param name Name of product
     * @param name_sub subtitle of product which elaborates any necessary details
     * @param category numerical category which product belongs to
     * @param keywords any tags that may be used for searching this product
     * @param pantry_open_exp # days for product to expire if stored in pantry already opened
     * @param pantry_closed_exp # days for product to expire if stored in pantry already opened
     */
    public Product(String name, String name_sub, String category, String keywords,
                    int pantry_open_exp, int pantry_closed_exp, int fridge_open_exp,
                    int fridge_closed_exp, int freezer_open_exp, int freezer_closed_exp, double price) {
        if (name != null && name.length() > 0 && price >= 0) {
            this.name = name;
            this.name_sub = name_sub;
            this.category = category;
            this.keywords = keywords;
            this.pantry_open_exp = pantry_open_exp;
            this.pantry_closed_exp = pantry_closed_exp;
            this.fridge_open_exp = fridge_open_exp;
            this.fridge_closed_exp = fridge_closed_exp;
            this.freezer_open_exp = freezer_open_exp;
            this.freezer_closed_exp = freezer_closed_exp;
        }
    }


    public Product(){
        this("nothing", null, null,null, 0, 0,0,0,0, 0, 0);
    }

    public Product(String name, String name_sub, String category, String keywords,
                    int pantry_exp, int fridge_exp, int freezer_exp, double price) {
        this(name, name_sub, category, keywords, pantry_exp, pantry_exp, fridge_exp, fridge_exp, freezer_exp, freezer_exp, price);
    }
    private Product(Parcel in) {
        Model model = Model.getInstance();

        object_id = in.readString();
        _id = in.readInt();
//        Product p = model.get_products().get(_id);
//        name = p.getName();
//        name_sub = p.getName_sub();
//        category = p.category;
//        keywords = p.keywords;
//        pantry_open_exp = p.pantry_open_exp;
//        pantry_closed_exp = p.pantry_closed_exp;
//        fridge_open_exp = p.fridge_open_exp;
//        fridge_closed_exp = p.fridge_closed_exp;
//        freezer_open_exp = p.freezer_open_exp;
//        freezer_closed_exp = p.freezer_closed_exp;
        name = in.readString();
        name_sub = in.readString();
        category = in.readString();
        keywords = in.readString();
        pantry_open_exp = in.readInt();
        pantry_closed_exp = in.readInt();
        fridge_open_exp = in.readInt();
        fridge_closed_exp = in.readInt();
        freezer_open_exp = in.readInt();
        freezer_closed_exp = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString(object_id);
        out.writeInt(_id);
        out.writeString(name);
        out.writeString(name_sub);
        out.writeString(category);
        out.writeString(keywords);
        out.writeInt(pantry_open_exp);
        out.writeInt(pantry_closed_exp);
        out.writeInt(pantry_open_exp);
        out.writeInt(pantry_closed_exp);
        out.writeInt(freezer_open_exp);
        out.writeInt(freezer_closed_exp);
    }

    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>(){
        @Override
        public Product createFromParcel(Parcel in){
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size){
            return new Product[size];
        }
    };


//    public void writeObject(java.io.ObjectOutputStream out)
//            throws IOException{
//        out.writeUTF(object_id);
//        out.writeInt(_id);
//    }
//
//    public void readObject(java.io.ObjectInputStream in)
//            throws IOException, ClassNotFoundException {
//        Model model = Model.getInstance();
//
//        object_id = in.readUTF();
//        _id = in.readInt();
//        Product p = model.get_products().get(_id);
//        name = p.getName();
//        name_sub = p.getName_sub();
//        category = p.category;
//        keywords = p.keywords;
//        pantry_open_exp = p.pantry_open_exp;
//        pantry_closed_exp = p.pantry_closed_exp;
//        fridge_open_exp = p.fridge_open_exp;
//        fridge_closed_exp = p.fridge_closed_exp;
//        freezer_open_exp = p.freezer_open_exp;
//        freezer_closed_exp = p.freezer_closed_exp;
//
//    }
//
//    private void readObjectNoData()
//            throws ObjectStreamException{}
////    private void readObjectNoData()
////            throws ObjectStreamException {}

    public String getObject_id() { return object_id; }

    public int get_id() { return _id; }

    public String getName() { return name; }

    public String getName_sub() { return name_sub; }

    public String getCategory() { return category; }

    public String getKeywords() { return keywords; }

    public int getPantry_exp() {
        int pantry_exp = 0;
        if (pantry_open_exp > 0 && pantry_closed_exp > 0) {
            pantry_exp = (int)Math.ceil((pantry_closed_exp+pantry_open_exp)/2.0);
        } else {
            pantry_exp = (pantry_closed_exp > pantry_open_exp) ? pantry_closed_exp : pantry_open_exp;
        }
        return pantry_exp;
    }

    public int getFridge_exp() {
        int fridge_exp = 0;
        if (fridge_open_exp > 0 && fridge_closed_exp > 0) {
            fridge_exp = (int)Math.ceil((fridge_closed_exp+fridge_open_exp)/2.0);
        } else {
            fridge_exp = (fridge_closed_exp > fridge_open_exp) ? fridge_closed_exp : fridge_open_exp;
        }
        return fridge_exp;
    }

    public int getFreezer_exp() {
        int freezer_exp = 0;
        if (freezer_open_exp > 0 && freezer_closed_exp > 0) {
            freezer_exp = (int)Math.ceil((freezer_closed_exp+freezer_open_exp)/2.0);
        } else {
            freezer_exp = (freezer_closed_exp > freezer_open_exp) ? freezer_closed_exp : freezer_open_exp;
        }
        return freezer_exp;
    }

    public double getPrice() {
        return price;
    }

    public String getNameAndSub(){
        if (name_sub != null && name_sub.length() > 0) {
            return (name + ": " + name_sub);
        } else {
            return name;
        }
    }

    public void set_id(int _id) { this._id = _id; }

    public void setName(String name) { this.name = name; }

    public void setName_sub(String name_sub) { this.name_sub = name_sub; }

    public void setCategory(String category) { this.category = category; }

    public void setKeywords(String keywords) { this.keywords = keywords; }

    public void setPantry_exp(int pantry_exp) {
        this.pantry_open_exp = pantry_exp;
        this.pantry_closed_exp = pantry_exp;
    }

    public void setFridge_exp(int fridge_exp) {
        this.fridge_open_exp = fridge_exp;
        this.fridge_closed_exp = fridge_exp;
    }

    public void setFreezer_exp(int freezer_exp) {
        this.freezer_open_exp = freezer_exp;
        this.freezer_closed_exp = freezer_exp;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString(){
        return String.format("id:%s\t name: %s\t subtitle: %s\t\n category: %s\t keywords: %s\t\n pantry_exp: %d\t fridge_exp: %d\t freezer_exp: %d",
                _id, name, name_sub, category, keywords, getPantry_exp(), getFridge_exp(), getFreezer_exp());
    }
}
