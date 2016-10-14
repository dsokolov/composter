package ru.composter.passanger;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.util.UUID;

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
            new ConnectedThread(mmSocket, "Insecure").start();
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
