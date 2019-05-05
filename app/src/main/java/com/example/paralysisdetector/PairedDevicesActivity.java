package com.example.paralysisdetector;

import android.bluetooth.BluetoothDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class PairedDevicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paired_devices);

        RecyclerView recyclerView;
        Bundle bundle = getIntent().getExtras();
        ArrayList<BluetoothDevice> pairedDevices;
        pairedDevices = (ArrayList) bundle.getSerializable(
                MainActivity.PAIRED_DEVICES
        );

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new PairedDevicesAdapter(pairedDevices));
    }
}
