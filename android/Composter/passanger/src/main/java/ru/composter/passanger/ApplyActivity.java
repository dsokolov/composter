package ru.composter.passanger;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

import ru.composter.commands.CommandsProcessor;
import ru.composter.commands.PaymentRequest;

public class ApplyActivity extends Activity {

    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public ConnectThread connectThread;
    public ConnectedThread connectedThread;

    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        findViewById(R.id.apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectedThread != null) {
                    connectedThread.send("Какая-то инфа");
                }
            }
        });
        connectDevice(getIntent());
    }

    private void connectDevice(Intent data) {
        // Get the device MAC address
        String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        if (bluetoothAdapter.isEnabled()) {
            connect(device);
        }
        else
        {
            Toast.makeText(this, "Нужно включить блюпуп", Toast.LENGTH_LONG).show();
        }
    }

    private void connect(BluetoothDevice device) {
        connectThread = new ConnectThread(device);
        connectThread.start();
    }

    public class ConnectedThread extends Thread {

        static final String TAG = "Passanger";

        private final BluetoothSocket mmSocket;
        CommandsProcessor commandsProcessor;
        PaymentRequest paymentRequest;

        public ConnectedThread(BluetoothSocket socket, String socketType) {
            Log.d(TAG, "create ConnectedThread: " + socketType);
            mmSocket = socket;
            commandsProcessor = new CommandsProcessor(mmSocket, new CommandsProcessor.Callback() {
                @Override
                public void onPaymentRequest(@NotNull final PaymentRequest pr) {
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            paymentRequest = pr;
                            ((TextView) findViewById(R.id.name)).setText(pr.getDriverName());
                            ((TextView) findViewById(R.id.car)).setText(pr.getVenchileCode());
                            ((TextView) findViewById(R.id.route)).setText(pr.getRouteInfo());
                        }
                    });
                }
            });

        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;

            commandsProcessor.start();
        }

        public void send(String s) {
            Log.i(TAG, "Message sent");
            commandsProcessor.sendString(s);
        }

        public void cancel() {
            try {
                commandsProcessor.stop();
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }
    }

    public class ConnectThread extends Thread {

        static final String TAG = "Passanger";

        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        private String mSocketType;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            BluetoothSocket tmp = null;
            mSocketType = "Insecure";

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                tmp = device.createInsecureRfcommSocketToServiceRecord(UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66"));
            } catch (IOException e) {
                Log.e(TAG, "Socket Type: " + mSocketType + "create() failed", e);
            }
            mmSocket = tmp;
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectThread SocketType:" + mSocketType);
            setName("ConnectThread" + mSocketType);

            // Make a connection to the BluetoothSocket
            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                Log.d(TAG, "try to connect");
                mmSocket.connect();
                connectedThread = new ConnectedThread(mmSocket, "Insecure");
                connectedThread.start();
            } catch (IOException e) {
                // Close the socket
                try {
                    Log.d(TAG, "unable to connect");
                    mmSocket.close();
                } catch (IOException e2) {
                    Log.d(TAG, "unable to close() " + mSocketType +
                            " socket during connection failure", e2);
                }
                //connectionFailed();
                return;
            } finally {
                Log.d(TAG, "connected");
            }

        }

        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect " + mSocketType + " socket failed", e);
            }
        }
    }
}
