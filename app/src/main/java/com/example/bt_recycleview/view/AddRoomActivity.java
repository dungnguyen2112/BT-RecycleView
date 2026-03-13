package com.example.bt_recycleview.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bt_recycleview.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class AddRoomActivity extends AppCompatActivity {

    public static final String EXTRA_EXISTING_ROOM_IDS = "extra_existing_room_ids";
    public static final String EXTRA_ROOM_ID = "extra_room_id";
    public static final String EXTRA_ROOM_NAME = "extra_room_name";
    public static final String EXTRA_RENT_PRICE = "extra_rent_price";

    private TextInputLayout tilRoomId;
    private TextInputLayout tilRoomName;
    private TextInputLayout tilRentPrice;

    private TextInputEditText etRoomId;
    private TextInputEditText etRoomName;
    private TextInputEditText etRentPrice;
    private ArrayList<String> existingRoomIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_room);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.addRoomRoot), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindViews();
        setupEvents();
    }

    private void bindViews() {
        tilRoomId = findViewById(R.id.tilRoomId);
        tilRoomName = findViewById(R.id.tilRoomName);
        tilRentPrice = findViewById(R.id.tilRentPrice);

        etRoomId = findViewById(R.id.etRoomId);
        etRoomName = findViewById(R.id.etRoomName);
        etRentPrice = findViewById(R.id.etRentPrice);
        existingRoomIds = getIntent().getStringArrayListExtra(EXTRA_EXISTING_ROOM_IDS);
        if (existingRoomIds == null) {
            existingRoomIds = new ArrayList<>();
        }
    }

    private void setupEvents() {
        MaterialButton btnAddRoom = findViewById(R.id.btnAddRoom);
        MaterialButton btnCancel = findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(v -> finish());
        btnAddRoom.setOnClickListener(v -> submit());
    }

    private void submit() {
        clearErrors();

        String roomId = getText(etRoomId).trim();
        String roomName = getText(etRoomName).trim();
        String rentPriceText = getText(etRentPrice).trim();

        boolean valid = true;

        if (TextUtils.isEmpty(roomId)) {
            tilRoomId.setError(getString(R.string.error_room_id_required));
            valid = false;
        } else if (!roomId.matches("^[A-Za-z0-9_-]{2,20}$")) {
            tilRoomId.setError(getString(R.string.error_room_id_format));
            valid = false;
        } else if (isRoomIdExists(roomId)) {
            tilRoomId.setError(getString(R.string.error_room_id_exists));
            valid = false;
        }

        if (TextUtils.isEmpty(roomName)) {
            tilRoomName.setError(getString(R.string.error_room_name_required));
            valid = false;
        } else if (roomName.length() < 3) {
            tilRoomName.setError(getString(R.string.error_room_name_too_short));
            valid = false;
        }

        double rentPrice = 0;
        if (TextUtils.isEmpty(rentPriceText)) {
            tilRentPrice.setError(getString(R.string.error_rent_price_required));
            valid = false;
        } else {
            try {
                rentPrice = Double.parseDouble(rentPriceText);
                if (rentPrice <= 0) {
                    tilRentPrice.setError(getString(R.string.error_rent_price_invalid));
                    valid = false;
                }
            } catch (NumberFormatException ex) {
                tilRentPrice.setError(getString(R.string.error_rent_price_invalid));
                valid = false;
            }
        }

        if (!valid) return;

        Intent data = new Intent();
        data.putExtra(EXTRA_ROOM_ID, roomId);
        data.putExtra(EXTRA_ROOM_NAME, roomName);
        data.putExtra(EXTRA_RENT_PRICE, rentPrice);

        setResult(RESULT_OK, data);
        finish();
    }

    private String getText(TextInputEditText editText) {
        return editText.getText() == null ? "" : editText.getText().toString();
    }

    private void clearErrors() {
        tilRoomId.setError(null);
        tilRoomName.setError(null);
        tilRentPrice.setError(null);
    }

    private boolean isRoomIdExists(String roomId) {
        String normalized = roomId.trim();
        for (String id : existingRoomIds) {
            if (id != null && id.trim().equalsIgnoreCase(normalized)) {
                return true;
            }
        }
        return false;
    }
}
