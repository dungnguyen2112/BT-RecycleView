package com.example.bt_recycleview.controller;

import com.example.bt_recycleview.model.Room;

import java.util.ArrayList;
import java.util.List;

/**
 * Base controller cho Room.
 * Hiện tại chỉ giữ List<Room> trong bộ nhớ, phần 1/2/3/4/5 sẽ dùng và mở rộng thêm.
 */
public class RoomController {
    private final List<Room> rooms = new ArrayList<>();

    public RoomController() {
        // Có thể seed dữ liệu mẫu ở đây nếu cần trong tương lai.
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

