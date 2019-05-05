package com.example.paralysisdetector;

import android.bluetooth.BluetoothDevice;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class PairedDevicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paired_devices);

        Bundle bundle = getIntent().getExtras();
        ArrayList<BluetoothDevice> pairedDevices;
        pairedDevices = (ArrayList) bundle.getSerializable(
                MainActivity.PAIRED_DEVICES
        );
        setUpRecyclerView(pairedDevices);
    }

    private void setUpRecyclerView(ArrayList<BluetoothDevice> pairedDevices) {
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                       @NonNull RecyclerView parent,
                                       @NonNull RecyclerView.State state) {
                outRect.top = 10;
                outRect.right = 10;
                outRect.bottom = 10;
                outRect.left = 10;
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PairedDevicesAdapter(pairedDevices, this));
    }
}
