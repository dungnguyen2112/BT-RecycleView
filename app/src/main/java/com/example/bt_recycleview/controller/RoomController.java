package com.example.bt_recycleview.controller;

import com.example.bt_recycleview.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomController {
    private final List<Room> rooms = new ArrayList<>();

    public RoomController() {
        rooms.add(new Room("P101", "Phòng 101", 2500000, true, "Nguyễn Văn A", "0901111111"));
        rooms.add(new Room("P102", "Phòng 102", 2200000, false, "", ""));
        rooms.add(new Room("P201", "Phòng 201", 3000000, true, "Trần Thị B", "0902222222"));
        rooms.add(new Room("P202", "Phòng 202", 2800000, false, "", ""));
    }

    public List<Room> getRooms() {
        return rooms;
    }
}

