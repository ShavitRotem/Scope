package com.postpc.scope;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Bluetooth_devices extends AppCompatActivity {

    BluetoothAdapter mBluetoothAdapter;
    BroadcastReceiver receiver;
    List<String> devices_names = new ArrayList<String>();
    List<String> devices_addresses = new ArrayList<String>();
    List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
    ListView BTDevicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_devices);

//        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        BTDevicesList = findViewById(R.id.BTDevList);

        int permissionCheck = ContextCompat.checkSelfPermission(Bluetooth_devices.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                | ContextCompat.checkSelfPermission(Bluetooth_devices.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(Bluetooth_devices.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION}, 642);
        }

        final ArrayAdapter arrayAdapter = new ArrayAdapter<>(Bluetooth_devices.this,
                android.R.layout.simple_list_item_1, devices_names);
        BTDevicesList.setAdapter(arrayAdapter);

        BTDevicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mBluetoothAdapter.cancelDiscovery();
                BluetoothDevice toConn = devices.get(i);
                Log.d("BT connection attempt", "Trying to pair with " + toConn.getName());

                toConn.createBond();

                //TODO Connect to device first
//                Intent scope = new Intent(getApplicationContext(), Scope.class);
//                startActivity(scope);

                Intent serial = new Intent(getApplicationContext(), BTSerial.class);
                startActivity(serial);
            }
        });

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        for(BluetoothDevice paired : mBluetoothAdapter.getBondedDevices())
        {
            devices.add(paired);
            devices_names.add(paired.getName());
            devices_addresses.add(paired.getAddress());
        }

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action))
                {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    assert device != null;
                    String deviceName = device.getName();
                    String deviceAdd = device.getAddress();

                    Log.d("BT OnReceive", "Name is " + deviceName + "\n" + "Address is "
                            + deviceAdd + "Device is " + device);

                    if(deviceName != null && !devices_addresses.contains(deviceAdd))
                    {
                        devices.add(device);
                        devices_addresses.add(deviceAdd);
                        devices_names.add(deviceName);
                        arrayAdapter.notifyDataSetChanged();
                        //findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                        Toast.makeText(Bluetooth_devices.this, deviceName, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter);

        mBluetoothAdapter.startDiscovery();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(receiver);
    }
}
