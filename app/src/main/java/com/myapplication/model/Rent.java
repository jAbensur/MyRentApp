package com.myapplication.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "rents",
        foreignKeys ={
                @ForeignKey(entity = Tenant.class,
                        parentColumns = "id",
                        childColumns = "tenantId",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Chamber.class,
                        parentColumns = "id",
                        childColumns = "chamberId",
                        onDelete = ForeignKey.CASCADE)
        })
public class Rent {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String startDate;
    private String endDate;
    private double price;
    private int state;
    private int tenantId;
    private int chamberId;
    public Rent(String startDate, String endDate, double price) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
    }
    public Rent() {

    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    public String getEndDate() {
        return endDate;
    }
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getState() {
        return state;
    }
    public void setState(int state) {
        this.state = state;
    }
    public int getTenantId() {
        return tenantId;
    }
    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }
    public int getChamberId() {
        return chamberId;
    }
    public void setChamberId(int chamberId) {
        this.chamberId = chamberId;
    }
}
