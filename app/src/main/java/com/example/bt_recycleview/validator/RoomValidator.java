package com.example.bt_recycleview.validator;

import android.text.TextUtils;

/**
 * Centralized validation for Room input fields.
 * This app stores data in-memory only (List), so validation is done at the View layer.
 */
public final class RoomValidator {

    private RoomValidator() {
        // no-op
    }

    /**
     * Result object for room validation.
     * Contains both validation errors and normalized/parsed values.
     */
    public static final class Result {
        public boolean valid;

        public String nameError;
        public String priceError;
        public String tenantNameError;
        public String phoneError;

        public double parsedPrice;
        public String normalizedTenantName;
        public String normalizedPhone;
    }

    /**
     * Validates inputs for editing/creating a room.
     *
     * @param name       room name (trimmed)
     * @param priceText  rent price text input (trimmed)
     * @param rented     rental status
     * @param tenantName tenant name (trimmed)
     * @param phone      phone number (trimmed)
     */
    public static Result validate(String name,
                                  String priceText,
                                  boolean rented,
                                  String tenantName,
                                  String phone) {
        Result r = new Result();
        r.valid = true;

        // Name
        if (TextUtils.isEmpty(name)) {
            r.nameError = "Vui lòng nhập tên phòng";
            r.valid = false;
        }

        // Price
        if (TextUtils.isEmpty(priceText)) {
            r.priceError = "Vui lòng nhập giá thuê";
            r.valid = false;
        } else {
            try {
                r.parsedPrice = Double.parseDouble(priceText);
                if (r.parsedPrice <= 0) {
                    r.priceError = "Giá thuê phải > 0";
                    r.valid = false;
                }
            } catch (NumberFormatException e) {
                r.priceError = "Giá thuê không hợp lệ";
                r.valid = false;
            }
        }

        // Tenant info (only required when rented)
        if (rented) {
            if (TextUtils.isEmpty(tenantName)) {
                r.tenantNameError = "Vui lòng nhập tên người thuê";
                r.valid = false;
            }
            if (TextUtils.isEmpty(phone)) {
                r.phoneError = "Vui lòng nhập số điện thoại";
                r.valid = false;
            }
            r.normalizedTenantName = tenantName;
            r.normalizedPhone = phone;
        } else {
            // Normalize when available
            r.normalizedTenantName = "";
            r.normalizedPhone = "";
        }

        return r;
    }
}
