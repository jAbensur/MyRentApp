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
        foreignKeys = @ForeignKey(  entity = RoomModel.class,
                                    parentColumns = "id",
                                    childColumns = "propertyId",
                                    onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = "propertyId")})
public class RoomModel implements Parcelable {

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

    public RoomModel() {
    }

    public RoomModel(Parcel in)
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

    public static final Creator<RoomModel> CREATOR = new Creator<RoomModel>() {
        @Override
        public RoomModel createFromParcel(Parcel in) { return new RoomModel(in); }

        @Override
        public RoomModel[] newArray(int size) { return new RoomModel[size];}
    };
}
