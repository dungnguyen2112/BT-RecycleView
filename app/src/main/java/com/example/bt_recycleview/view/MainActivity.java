package com.example.bt_recycleview.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_recycleview.R;
import com.example.bt_recycleview.controller.RoomController;
import com.example.bt_recycleview.model.Room;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final RoomController roomController = new RoomController();
    private RoomAdapter roomAdapter;
    private final ActivityResultLauncher<Intent> addRoomLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() != RESULT_OK || result.getData() == null) return;

                Intent data = result.getData();
                String roomId = data.getStringExtra(AddRoomActivity.EXTRA_ROOM_ID);
                String roomName = data.getStringExtra(AddRoomActivity.EXTRA_ROOM_NAME);
                double rentPrice = data.getDoubleExtra(AddRoomActivity.EXTRA_RENT_PRICE, 0);

                if (roomId == null || roomName == null) {
                    Toast.makeText(this, R.string.add_room_failed_data, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (roomController.isRoomIdExists(roomId)) {
                    Toast.makeText(this, R.string.error_room_id_exists, Toast.LENGTH_SHORT).show();
                    return;
                }

                Room room = new Room(roomId, roomName, rentPrice, false, "", "");
                roomController.addRoom(room);
                refreshRooms();

                Toast.makeText(this, R.string.add_room_success, Toast.LENGTH_SHORT).show();
            });

    // Launcher sửa phòng
    private final ActivityResultLauncher<Intent> editRoomLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK) {
                    refreshRooms();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupRecyclerView();
        setupAddRoomButton();
    }

    private void setupRecyclerView() {
        RecyclerView rvRooms = findViewById(R.id.rvRooms);
        roomAdapter = new RoomAdapter(
                room -> {
                    // Click item: sửa phòng
                    Intent intent = new Intent(MainActivity.this, EditRoomActivity.class);
                    intent.putExtra(EditRoomActivity.EXTRA_ROOM_ID, room.getRoomId());
                    editRoomLauncher.launch(intent);
                },
                room -> {
                    // Click nút Xóa: xác nhận và xóa
                    new AlertDialog.Builder(this)
                            .setTitle("Xóa phòng")
                            .setMessage("Bạn có chắc chắn muốn xóa phòng " + room.getName() + " không?")
                            .setPositiveButton("Xóa", (dialog, which) -> {
                                roomController.deleteRoom(room);
                                refreshRooms();
                                Toast.makeText(this, "Đã xóa phòng", Toast.LENGTH_SHORT).show();
                            })
                            .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                            .show();
                }
        );
        rvRooms.setAdapter(roomAdapter);
        refreshRooms();
    }

    private void refreshRooms() {
        if (roomAdapter == null) return;
        roomAdapter.submitList(new ArrayList<>(roomController.getRooms()));
    }

    private void setupAddRoomButton() {
        FloatingActionButton fabAddRoom = findViewById(R.id.fabAddRoom);

        fabAddRoom.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddRoomActivity.class);

            ArrayList<String> roomIds = new ArrayList<>();
            for (Room room : roomController.getRooms()) {
                roomIds.add(room.getRoomId());
            }

            intent.putStringArrayListExtra(AddRoomActivity.EXTRA_EXISTING_ROOM_IDS, roomIds);
            addRoomLauncher.launch(intent);
        });
    }
}

