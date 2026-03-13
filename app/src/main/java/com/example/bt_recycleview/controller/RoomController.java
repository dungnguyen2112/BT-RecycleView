package com.example.bt_recycleview.controller;

import com.example.bt_recycleview.model.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

    public void deleteRoom(Room room) {
        if (room == null) {
            return;
        }
        for (int i = 0; i < rooms.size(); i++) {
            Room current = rooms.get(i);
            if (current.getRoomId().equals(room.getRoomId())) {
                rooms.remove(i);
                break;
            }
        }
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

    public List<Room> search(String query) {
        if (query == null) {
            return new ArrayList<>(rooms);
        }

        String q = query.trim().toLowerCase(Locale.ROOT);
        if (q.isEmpty()) {
            return new ArrayList<>(rooms);
        }

        List<Room> result = new ArrayList<>();
        for (Room room : rooms) {
            boolean matchPhone = q.length() >= 3; // tránh gõ 1 số lẻ mà match lung tung theo SĐT

            if (containsIgnoreCase(room.getRoomId(), q)
                    || containsIgnoreCase(room.getName(), q)
                    || containsIgnoreCase(room.getTenantName(), q)
                    || (matchPhone && containsIgnoreCase(room.getPhoneNumber(), q))) {
                result.add(room);
            }
        }
        return result;
    }

    private boolean containsIgnoreCase(String value, String queryLower) {
        if (value == null) return false;
        return value.toLowerCase(Locale.ROOT).contains(queryLower);
    }
}

