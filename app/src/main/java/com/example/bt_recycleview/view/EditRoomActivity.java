package com.example.bt_recycleview.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat.Type;
import androidx.core.view.WindowInsetsCompat;

import com.example.bt_recycleview.R;
import com.example.bt_recycleview.controller.RoomController;
import com.example.bt_recycleview.model.Room;
import com.example.bt_recycleview.validator.RoomValidator;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * EditRoomActivity đóng vai trò View (MVC) cho module Update (Sửa phòng).
 * - Load dữ liệu phòng theo roomId
 * - Validate dữ liệu
 * - Cập nhật lại List thông qua RoomController
 */
public class EditRoomActivity extends AppCompatActivity {

    public static final String EXTRA_ROOM_ID = "extra_room_id";

    private final RoomController roomController = new RoomController();

    private String roomId;

    private TextInputLayout tilName;
    private TextInputLayout tilPrice;
    private TextInputLayout tilTenantName;
    private TextInputLayout tilPhone;

    private TextInputEditText edtName;
    private TextInputEditText edtPrice;
    private TextInputEditText edtTenantName;
    private TextInputEditText edtPhone;

    private MaterialSwitch swRented;
    private MaterialTextView tvRoomId;

    /**
     * Initializes UI, reads the {@link #EXTRA_ROOM_ID} from the intent, loads the selected room data,
     * and wires up interactions for editing.
     * <p>
     * If the room id is missing or the room cannot be found, this screen closes immediately.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_room);
        // Preserve existing padding (e.g., 16dp) and add system bar insets on top of it.
        final android.view.View root = findViewById(R.id.edit_room_root);
        final int baseLeft = root.getPaddingLeft();
        final int baseTop = root.getPaddingTop();
        final int baseRight = root.getPaddingRight();
        final int baseBottom = root.getPaddingBottom();
        ViewCompat.setOnApplyWindowInsetsListener(root, (v, insets) -> {
            Insets systemBars = insets.getInsets(Type.systemBars());
            v.setPadding(
                    baseLeft + systemBars.left,
                    baseTop + systemBars.top,
                    baseRight + systemBars.right,
                    baseBottom + systemBars.bottom
            );
            return insets;
        });

        bindViews();

        roomId = getIntent().getStringExtra(EXTRA_ROOM_ID);
        if (TextUtils.isEmpty(roomId)) {
            Toast.makeText(this, "Không tìm thấy mã phòng để sửa", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Room room = roomController.getRoomById(roomId);
        if (room == null) {
            Toast.makeText(this, "Phòng không tồn tại", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadRoom(room);
        setupInteractions();
    }

    /**
     * Binds all view references and registers click handlers for Cancel/Save actions.
     */
    private void bindViews() {
        MaterialToolbar toolbar = findViewById(R.id.toolbarEditRoom);
        toolbar.setNavigationOnClickListener(v -> finish());

        tilName = findViewById(R.id.tilName);
        tilPrice = findViewById(R.id.tilPrice);
        tilTenantName = findViewById(R.id.tilTenantName);
        tilPhone = findViewById(R.id.tilPhone);

        edtName = findViewById(R.id.edtName);
        edtPrice = findViewById(R.id.edtPrice);
        edtTenantName = findViewById(R.id.edtTenantName);
        edtPhone = findViewById(R.id.edtPhone);

        swRented = findViewById(R.id.swRented);
        tvRoomId = findViewById(R.id.tvRoomId);

        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnSave = findViewById(R.id.btnSave);

        btnCancel.setOnClickListener(v -> finish());
        btnSave.setOnClickListener(v -> saveChanges());
    }

    /**
     * Sets up UI interactions (e.g., switching between rented/available) and updates the form state.
     */
    private void setupInteractions() {
        swRented.setOnCheckedChangeListener((buttonView, isChecked) -> updateTenantFieldsEnabled(isChecked));
        updateTenantFieldsEnabled(swRented.isChecked());
    }

    /**
     * Enables/disables tenant-related fields based on rental status.
     * <p>
     * When the room is marked as available (not rented), tenant name/phone are cleared and disabled.
     */
    private void updateTenantFieldsEnabled(boolean rented) {
        tilTenantName.setEnabled(rented);
        tilPhone.setEnabled(rented);

        if (!rented) {
            if (edtTenantName.getText() != null) edtTenantName.getText().clear();
            if (edtPhone.getText() != null) edtPhone.getText().clear();
            tilTenantName.setError(null);
            tilPhone.setError(null);
        }
    }

    /**
     * Populates the form fields with the existing room data to be edited.
     */
    private void loadRoom(Room room) {
        if (room == null) return;
        setTitle("Sửa phòng");
        if (tvRoomId != null) {
            tvRoomId.setText("Mã phòng: " + room.getRoomId());
        }

        if (edtName.getText() != null) edtName.setText(room.getName());
        if (edtPrice.getText() != null) edtPrice.setText(String.valueOf(room.getRentPrice()));
        swRented.setChecked(room.isRented());

        if (edtTenantName.getText() != null) edtTenantName.setText(room.getTenantName());
        if (edtPhone.getText() != null) edtPhone.setText(room.getPhoneNumber());
    }

    /**
     * Clears validation errors shown in {@link TextInputLayout}s.
     */
    private void clearErrors() {
        tilName.setError(null);
        tilPrice.setError(null);
        tilTenantName.setError(null);
        tilPhone.setError(null);
    }

    /**
     * Validates user input and updates the in-memory list via {@link RoomController}.
     * <p>
     * On success, returns {@link #RESULT_OK} so {@link MainActivity} can refresh the RecyclerView list.
     */
    private void saveChanges() {
        clearErrors();

        String name = edtName.getText() == null ? "" : edtName.getText().toString().trim();
        String priceText = edtPrice.getText() == null ? "" : edtPrice.getText().toString().trim();
        boolean rented = swRented.isChecked();

        String tenantName = edtTenantName.getText() == null ? "" : edtTenantName.getText().toString().trim();
        String phone = edtPhone.getText() == null ? "" : edtPhone.getText().toString().trim();

        RoomValidator.Result result = RoomValidator.validate(name, priceText, rented, tenantName, phone);
        if (!result.valid) {
            if (!TextUtils.isEmpty(result.nameError)) tilName.setError(result.nameError);
            if (!TextUtils.isEmpty(result.priceError)) tilPrice.setError(result.priceError);
            if (!TextUtils.isEmpty(result.tenantNameError)) tilTenantName.setError(result.tenantNameError);
            if (!TextUtils.isEmpty(result.phoneError)) tilPhone.setError(result.phoneError);
            return;
        }

        Room updated = new Room(
                roomId,
                name,
                result.parsedPrice,
                rented,
                result.normalizedTenantName,
                result.normalizedPhone
        );
        boolean updatedOk = roomController.updateRoomById(roomId, updated);
        if (!updatedOk) {
            Toast.makeText(this, "Cập nhật thất bại (không tìm thấy phòng)", Toast.LENGTH_SHORT).show();
            return;
        }

        setResult(RESULT_OK);
        finish();
    }
}