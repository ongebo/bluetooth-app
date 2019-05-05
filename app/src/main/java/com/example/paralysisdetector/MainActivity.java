package com.example.paralysisdetector;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextWatcher {
    public static final String PAIRED_DEVICES = "com.example.paralysisdetector.PAIRED_DEVICES";
    public static final int REQUEST_CODE = 1;

    // The EditText views in the main activity.
    private EditText xMessage;
    private EditText xNegMessage;
    private EditText yMessage;
    private EditText yNegMessage;
    private Button sendButton;

    // The texts entered by the user into the text fields.
    private String xMessageText;
    private String xNegMessageText;
    private String yMessageText;
    private String yNegMessageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        setTextChangeListeners();
    }

    private void initViews() {
        xMessage = findViewById(R.id.x_message);
        xNegMessage = findViewById(R.id.x_negative_message);
        yMessage = findViewById(R.id.y_message);
        yNegMessage = findViewById(R.id.y_negative_message);
        sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDataOverBluetooth();
            }
        });
    }

    private void setTextChangeListeners() {
        xMessage.addTextChangedListener(this);
        xNegMessage.addTextChangedListener(this);
        yMessage.addTextChangedListener(this);
        yNegMessage.addTextChangedListener(this);
    }

    private void sendDataOverBluetooth() {
        String payload = String.format(
                "%s&%s&%s&%s", xMessageText, xNegMessageText, yMessageText, yNegMessageText
        );
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothIsNotSetUp(adapter)) {
            return;
        }
        ArrayList<BluetoothDevice> pairedDevices = new ArrayList<>(adapter.getBondedDevices());
        Bundle bundle = new Bundle();
        bundle.putSerializable(PAIRED_DEVICES, pairedDevices);
        Intent intent = new Intent(this, PairedDevicesActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, REQUEST_CODE);
    }

    private boolean bluetoothIsNotSetUp(BluetoothAdapter adapter) {
        if (adapter == null) {
            Toast.makeText(
                    getApplicationContext(), R.string.no_bluetooth_adapter_error, Toast.LENGTH_LONG
            ).show();
            return true;
        }
        if (!adapter.isEnabled()) {
            Toast.makeText(
                    getApplicationContext(), R.string.enable_bluetooth_message, Toast.LENGTH_SHORT
            ).show();
            Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, REQUEST_CODE);
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        boolean sendButtonDisabled;
        xMessageText = xMessage.getText().toString().trim();
        xNegMessageText = xNegMessage.getText().toString().trim();
        yMessageText = yMessage.getText().toString().trim();
        yNegMessageText = yNegMessage.getText().toString().trim();

        if (xMessageText.isEmpty()) {
            sendButtonDisabled = true;
        } else if (xNegMessageText.isEmpty()) {
            sendButtonDisabled = true;
        } else if (yMessageText.isEmpty()) {
            sendButtonDisabled = true;
        } else {
            sendButtonDisabled = yNegMessageText.isEmpty();
        }

        if (sendButtonDisabled) {
            sendButton.setEnabled(false);
        } else {
            sendButton.setEnabled(true);
        }
    }
}
