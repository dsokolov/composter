package ru.composter.passanger;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "Passanger";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent serverIntent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivity(serverIntent);
            }
        });
        findViewById(R.id.apply_discovery).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent discovery = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discovery.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discovery);
            }
        });
    }
}

