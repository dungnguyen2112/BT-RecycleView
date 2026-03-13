package com.example.bt_recycleview.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_recycleview.R;
import com.example.bt_recycleview.model.Room;

import java.text.NumberFormat;
import java.util.Locale;

public class RoomAdapter extends ListAdapter<Room, RoomAdapter.RoomViewHolder> {

    public interface OnRoomClickListener {
        void onRoomClick(@NonNull Room room);
    }

    @Nullable
    private final OnRoomClickListener onRoomClickListener;

    public RoomAdapter() {
        this(null);
    }

    public RoomAdapter(@Nullable OnRoomClickListener onRoomClickListener) {
        super(DIFF_CALLBACK);
        this.onRoomClickListener = onRoomClickListener;
    }

    private static final DiffUtil.ItemCallback<Room> DIFF_CALLBACK = new DiffUtil.ItemCallback<Room>() {
        @Override
        public boolean areItemsTheSame(@NonNull Room oldItem, @NonNull Room newItem) {
            return oldItem.getRoomId().equals(newItem.getRoomId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Room oldItem, @NonNull Room newItem) {
            return oldItem.getName().equals(newItem.getName())
                    && oldItem.getRentPrice() == newItem.getRentPrice()
                    && oldItem.isRented() == newItem.isRented()
                    && oldItem.getTenantName().equals(newItem.getTenantName())
                    && oldItem.getPhoneNumber().equals(newItem.getPhoneNumber());
        }
    };

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_room, parent, false);
        return new RoomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        holder.bind(getItem(position), onRoomClickListener);
    }

    static class RoomViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvRoomId;
        private final TextView tvRoomName;
        private final TextView tvPrice;
        private final TextView tvStatus;

        RoomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomId = itemView.findViewById(R.id.tvRoomId);
            tvRoomName = itemView.findViewById(R.id.tvRoomName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }

        void bind(@NonNull Room room, @Nullable OnRoomClickListener onRoomClickListener) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            tvRoomId.setText("Mã phòng: " + room.getRoomId());
            tvRoomName.setText("Tên phòng: " + room.getName());
            tvPrice.setText("Giá thuê: " + formatter.format(room.getRentPrice()));

            if (room.isRented()) {
                tvStatus.setText("Tình trạng: Đã thuê");
                tvStatus.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.status_rented));
            } else {
                tvStatus.setText("Tình trạng: Còn trống");
                tvStatus.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.status_available));
            }

            itemView.setOnClickListener(v -> {
                if (onRoomClickListener != null) {
                    onRoomClickListener.onRoomClick(room);
                }
            });
        }
    }
}
