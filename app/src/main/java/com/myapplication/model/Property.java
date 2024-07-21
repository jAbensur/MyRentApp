package com.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "properties")
public class Property implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "p_name")
    public String nameP;
    @ColumnInfo(name = "p_description")
    public String description;
    @ColumnInfo(name = "p_address")
    public String address;
    @ColumnInfo(name = "p_state")
    public int state;

    public Property() { }

    protected Property(Parcel in){
        id = in.readInt();
        nameP = in.readString();
        description = in.readString();
        address = in.readString();
        state = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nameP);
        dest.writeString(description);
        dest.writeString(address);
        dest.writeInt(state);
    }

    public static final Creator<Property> CREATOR = new Creator<Property>() {
        @Override
        public Property createFromParcel(Parcel in) {
            return new Property(in);
        }

        @Override
        public Property[] newArray(int size) {
            return new Property[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getNameP() {
        return nameP;
    }
}
