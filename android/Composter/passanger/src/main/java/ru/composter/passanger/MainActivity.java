package ru.composter.passanger;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "Passanger";

    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectDevice(new Intent());
            }
        });
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serverIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE_INSECURE);
            }
        });
        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectThread != null) {
                    connectThread.cancel();
                }
            }
        });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            connectDevice(data);
        }
    }

    private void connectDevice(Intent data) {
        // Get the device MAC address
        String address = "C4:3A:BE:27:BC:CB";//data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        if (bluetoothAdapter.isEnabled()) {
            connect(device);
        }
        else
        {

        }
    }

    public ConnectThread connectThread;

    private void connect(BluetoothDevice device) {
        connectThread = new ConnectThread(device);
        connectThread.start();
    }
}

