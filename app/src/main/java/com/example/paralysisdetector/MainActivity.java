package com.example.paralysisdetector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements TextWatcher {
    private EditText xMessage;
    private EditText xNegMessage;
    private EditText yMessage;
    private EditText yNegMessage;
    private Button sendButton;

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
    }

    private void setTextChangeListeners() {
        xMessage.addTextChangedListener(this);
        xNegMessage.addTextChangedListener(this);
        yMessage.addTextChangedListener(this);
        yNegMessage.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        boolean sendButtonDisabled;
        String x = xMessage.getText().toString().trim();
        String xNeg = xNegMessage.getText().toString().trim();
        String y = yMessage.getText().toString().trim();
        String yNeg = yNegMessage.getText().toString().trim();

        if (x.isEmpty()) {
            sendButtonDisabled = true;
        } else if (xNeg.isEmpty()) {
            sendButtonDisabled = true;
        } else if (y.isEmpty()) {
            sendButtonDisabled = true;
        } else {
            sendButtonDisabled = yNeg.isEmpty();
        }

        if (sendButtonDisabled) {
            sendButton.setEnabled(false);
        } else {
            sendButton.setEnabled(true);
        }
    }
}
