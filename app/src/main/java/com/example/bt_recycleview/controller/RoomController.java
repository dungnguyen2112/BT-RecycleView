package com.example.bt_recycleview.controller;

import com.example.bt_recycleview.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomController {
    private final List<Room> rooms = new ArrayList<>();

    public RoomController() {
        
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        if (room != null) {
            rooms.add(room);
        }
    }

    public void updateRoom(int index, Room room) {
        if (room == null) return;
        if (index < 0 || index >= rooms.size()) return;
        rooms.set(index, room);
    }

    public void removeRoom(int index) {
        if (index < 0 || index >= rooms.size()) return;
        rooms.remove(index);
    }
}

