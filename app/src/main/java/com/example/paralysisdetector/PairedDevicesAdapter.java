package com.example.paralysisdetector;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PairedDevicesAdapter extends RecyclerView.Adapter<PairedDevicesAdapter.ViewHolder> {
    private List<BluetoothDevice> pairedDevices;

    public PairedDevicesAdapter(List<BluetoothDevice> pairedDevices) {
        this.pairedDevices = pairedDevices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.paired_device_details, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        TextView deviceName = viewHolder.view.findViewById(R.id.device_name);
        TextView deviceAddress = viewHolder.view.findViewById(R.id.device_address);
        deviceName.setText(pairedDevices.get(i).getName());
        deviceAddress.setText(pairedDevices.get(i).getAddress());
    }

    @Override
    public int getItemCount() {
        return pairedDevices.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
        }
    }
}
