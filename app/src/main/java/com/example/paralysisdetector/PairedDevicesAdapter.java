package com.example.paralysisdetector;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PairedDevicesAdapter extends RecyclerView.Adapter<PairedDevicesAdapter.DeviceViewHolder> {
    private List<BluetoothDevice> pairedDevices;
    private PairedDevicesActivity activity;

    public PairedDevicesAdapter(List<BluetoothDevice> pairedDevices, Activity activity) {
        this.pairedDevices = pairedDevices;
        this.activity = (PairedDevicesActivity) activity;
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.paired_device_details, viewGroup, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder viewHolder, int i) {
        TextView deviceName = viewHolder.view.findViewById(R.id.device_name);
        TextView deviceAddress = viewHolder.view.findViewById(R.id.device_address);
        deviceName.setText(pairedDevices.get(i).getName());
        deviceAddress.setText(pairedDevices.get(i).getAddress());

        final BluetoothDevice selectedDevice = pairedDevices.get(i);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackgroundColor(activity.getResources().getColor(R.color.highLightColor));
                Bundle bundle = new Bundle();
                bundle.putParcelable(MainActivity.SELECTED_DEVICE, selectedDevice);
                Intent intent = new Intent();
                intent.putExtras(bundle);
                activity.setResult(MainActivity.RESULT_OK, intent);
                activity.finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return pairedDevices.size();
    }

    public static class DeviceViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public DeviceViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }
}
