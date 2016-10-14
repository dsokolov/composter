package ru.composter.passanger;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class ConnectedThread extends Thread {

    static final String TAG = "Passanger";

    private final BluetoothSocket mmSocket;
    private final DataInputStream mmInStream;
    private final DataOutputStream mmOutStream;

    public ConnectedThread(BluetoothSocket socket, String socketType) {
        Log.d(TAG, "create ConnectedThread: " + socketType);
        mmSocket = socket;
        DataInputStream tmpIn = null;
        DataOutputStream tmpOut = null;

        // Get the BluetoothSocket input and output streams
        try {
            tmpIn = new DataInputStream(socket.getInputStream());
            tmpOut = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            Log.e(TAG, "temp sockets not created", e);
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        Log.i(TAG, "BEGIN mConnectedThread");
        byte[] buffer = new byte[1024];
        int bytes;

        // Keep listening to the InputStream while connected
        while (true) {//mState == STATE_CONNECTED) {
            try {
                Log.d(TAG, "Message: " + mmInStream.readUTF());
            } catch (IOException e) {
                Log.d(TAG, "disconnected", e);
                break;
            }
        }
    }

    /**
     * Write to the connected OutStream.
     *
     * @param buffer The bytes to write
     */
    public void write(byte[] buffer) {
        try {
            mmOutStream.write(buffer);
        } catch (IOException e) {
            Log.e(TAG, "Exception during write", e);
        }
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "close() of connect socket failed", e);
        }
    }
}
