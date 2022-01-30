package com.postpc.scope;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_DISCOVER_BT = 1;

    BluetoothAdapter mBluetoothAdapter;
//
    Button connectBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         connectBtn = (Button) findViewById(R.id.connectButton);
        // Adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

         //OnClick connect Button
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //         Check if BT is available
                if(mBluetoothAdapter == null)
                {
                    // BT is not available - Device is not support BT
                    Toast.makeText(MainActivity.this, "Phone does not have BT", Toast.LENGTH_SHORT).show();
                }
                else
                {
//                   BT is available - Device does support BT
                    if(!mBluetoothAdapter.isEnabled())
                    {
//                        Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
                        final AlertDialog.Builder BTDialog = new AlertDialog.Builder(MainActivity.this);
                        BTDialog.setMessage("Turn On BT?");
                        BTDialog.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mBluetoothAdapter.enable();

                                        Intent BTDev = new Intent(getApplicationContext(), Bluetooth_devices.class);
                                        startActivity(BTDev);
                                    }
                                }
                        );
                        BTDialog.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                        Toast.makeText(MainActivity.this, "You have to enable BT to proceed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                        );

                        AlertDialog BTAlert= BTDialog.create();
                        BTAlert.show();
                    }
                    else
                    {

                        Intent BTDev = new Intent(getApplicationContext(), Bluetooth_devices.class);
                        startActivity(BTDev);
                    }

                }
            }
        });
    }
}
