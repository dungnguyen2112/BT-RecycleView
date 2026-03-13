package com.example.bt_recycleview.controller;

import com.example.bt_recycleview.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomController {

    private static final List<Room> rooms = new ArrayList<>();
    private static boolean seeded = false;

    public RoomController() {
        if (!seeded) {
            seeded = true;
            rooms.add(new Room("P101", "Phòng 101", 2500000, true, "Nguyễn Văn A", "0901111111"));
            rooms.add(new Room("P102", "Phòng 102", 2200000, false, "", ""));
            rooms.add(new Room("P201", "Phòng 201", 3000000, true, "Trần Thị B", "0902222222"));
            rooms.add(new Room("P202", "Phòng 202", 2800000, false, "", ""));
        }
    }

    public List<Room> getRooms() {
        return rooms;
    }

    // CREATE
    public void addRoom(Room room) {
        if (room != null) {
            rooms.add(room);
        }
    }

    public boolean isRoomIdExists(String roomId) {
        if (roomId == null) return false;

        String normalized = roomId.trim();
        for (Room room : rooms) {
            if (room.getRoomId().equalsIgnoreCase(normalized)) {
                return true;
            }
        }
        return false;
    }

    // READ
    public Room getRoomById(String roomId) {
        int idx = findRoomIndexById(roomId);
        if (idx < 0) return null;
        return rooms.get(idx);
    }

    public int findRoomIndexById(String roomId) {
        if (roomId == null) return -1;

        for (int i = 0; i < rooms.size(); i++) {
            Room r = rooms.get(i);
            if (r != null && roomId.equals(r.getRoomId())) {
                return i;
            }
        }
        return -1;
    }

    // UPDATE
    public boolean updateRoomById(String roomId, Room room) {
        int idx = findRoomIndexById(roomId);
        if (idx < 0) return false;

        updateRoom(idx, room);
        return true;
    }

    public void updateRoom(int index, Room room) {
        if (room == null) return;
        if (index < 0 || index >= rooms.size()) return;

        rooms.set(index, room);
    }

    // DELETE
    public void removeRoom(int index) {
        if (index < 0 || index >= rooms.size()) return;
        rooms.remove(index);
    }
}