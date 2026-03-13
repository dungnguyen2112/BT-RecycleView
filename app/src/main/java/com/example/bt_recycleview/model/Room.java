package com.example.bt_recycleview.model;


public class Room {
    private final String roomId;     // Mã phòng
    private String name;             // Tên phòng
    private double rentPrice;        // Giá thuê
    private boolean rented;          // true = Đã thuê, false = Còn trống
    private String tenantName;       // Tên người thuê
    private String phoneNumber;      // Số điện thoại

    public Room(String roomId,
                String name,
                double rentPrice,
                boolean rented,
                String tenantName,
                String phoneNumber) {
        this.roomId = roomId;
        this.name = name;
        this.rentPrice = rentPrice;
        this.rented = rented;
        this.tenantName = tenantName;
        this.phoneNumber = phoneNumber;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getName() {
        return name;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public boolean isRented() {
        return rented;
    }

    public String getTenantName() {
        return tenantName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

