package com.example.research_app.ui;


import android.os.Parcel;
import android.os.Parcelable;

public class RequiredItemsList implements Parcelable
{
    public String name;
    public String date;
    public int quantity;
    public String units;

    public RequiredItemsList()
    {}
    private RequiredItemsList(Parcel in) {
        this.name = in.readString();
        this.date = in.readString();
        this.quantity = in.readInt();
        this.units=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.date);
        dest.writeInt(this.quantity);
        dest.writeString(this.units);
    }

    public static final Parcelable.Creator<RequiredItemsList> CREATOR = new Parcelable.Creator<RequiredItemsList>() {
        @Override
        public RequiredItemsList createFromParcel(Parcel in) {
            return new RequiredItemsList(in);
        }

        @Override
        public RequiredItemsList[] newArray(int size) {
            return new RequiredItemsList[size];
        }
    };
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}