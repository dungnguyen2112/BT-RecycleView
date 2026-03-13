package com.example.bt_recycleview.view;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bt_recycleview.R;
import com.example.bt_recycleview.controller.RoomController;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final RoomController roomController = new RoomController();

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
        setupSearchView();
    }

    private void setupRecyclerView() {
        RecyclerView rvRooms = findViewById(R.id.rvRooms);
        RoomAdapter roomAdapter = new RoomAdapter();
        rvRooms.setAdapter(roomAdapter);

        roomAdapter.submitList(new ArrayList<>(roomController.getRooms()));
    }

    private void setupSearchView() {
        SearchView searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateListWithQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                updateListWithQuery(newText);
                return true;
            }
        });
    }

    private void updateListWithQuery(String query) {
        RecyclerView rvRooms = findViewById(R.id.rvRooms);
        RoomAdapter adapter = (RoomAdapter) rvRooms.getAdapter();
        if (adapter == null) return;

        adapter.submitList(new ArrayList<>(roomController.search(query)));
    }
}

