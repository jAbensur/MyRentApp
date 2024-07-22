package com.myapplication.model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "room",
        foreignKeys = @ForeignKey(  entity = Property.class,
                                    parentColumns = "id",
                                    childColumns = "propertyId",
                                    onDelete = ForeignKey.CASCADE))
public class Room implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "r_name")
    public String nameR;
    @ColumnInfo(name = "r_number")
    public int number;
    @ColumnInfo(name = "r_capacity")
    public int capacity;
    @ColumnInfo(name = "r_description")
    public String description;
    @ColumnInfo(name = "r_price_per_month")
    public double pricePerMonth;
    @ColumnInfo(name = "r_available")
    public int available;
    @ColumnInfo(name = "r_material_type")
    public String materialType;
    @ColumnInfo(name = "r_state")
    public int state;

    public int propertyId;

    public Room() {
    }

    public Room(Parcel in)
    {
        id = in.readInt();
        nameR = in.readString();
        number = in.readInt();
        capacity = in.readInt();
        description = in.readString();
        pricePerMonth = in.readDouble();
        available = in.readInt();
        materialType = in.readString();
        state = in.readInt();
        propertyId = in.readInt();
    }

    public String getNameR() {
        return nameR;
    }

    public int getId() {
        return id;
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nameR);
        dest.writeInt(number);
        dest.writeInt(capacity);
        dest.writeString(description);
        dest.writeDouble(pricePerMonth);
        dest.writeInt(available);
        dest.writeString(materialType);
        dest.writeInt(state);
        dest.writeInt(propertyId);
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) { return new Room(in); }

        @Override
        public Room[] newArray(int size) { return new Room[size];}
    };

    public String getMaterialType() {
        return materialType;
    }
}
