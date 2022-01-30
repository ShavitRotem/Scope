package com.postpc.scope;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class BTSerial extends AppCompatActivity {

    TextView btmsg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btserial);

        btmsg = findViewById(R.id.BTtext);

    }
}