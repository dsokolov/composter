package ru.composter.passanger.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.UUID;

import ru.composter.commands.CommandsProcessor;
import ru.composter.commands.PaymentConfirm;
import ru.composter.commands.PaymentRequest;
import ru.composter.commands.PaymentSuccess;
import ru.composter.passanger.R;
import ru.composter.passanger.model.User;

public class ApplyActivity extends Activity {

    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    public ConnectThread connectThread;
    public ConnectedThread connectedThread;
    ProgressDialog progressDialog;
    PaymentRequest paymentRequest;
    PaymentConfirm paymentConfirm;

    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply);
        final User user = User.getUserInfo(this);
        findViewById(R.id.root).setVisibility(View.INVISIBLE);
        findViewById(R.id.apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connectedThread != null && paymentRequest != null) {
                    connectedThread.send(new PaymentConfirm(paymentRequest, user.getId(), "Типо публичный ключ"));
                }
            }
        });
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        connectDevice(getIntent());
    }

    private void connectDevice(Intent data) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Получение информации о водителе");
        progressDialog.setCancelable(false);
        progressDialog.show();
        // Get the device MAC address
        String address = data.getExtras().getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        // Get the BluetoothDevice object
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        // Attempt to connect to the device
        if (bluetoothAdapter.isEnabled()) {
            connect(device);
        } else {
            progressDialog.cancel();
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
        private CommandsProcessor commandsProcessor;
        private ProgressDialog pd;

        public ConnectedThread(BluetoothSocket socket, String socketType) {
            Log.d(TAG, "create ConnectedThread: " + socketType);
            mmSocket = socket;
            commandsProcessor = new CommandsProcessor(mmSocket, new CommandsProcessor.Callback() {
                @Override
                public void onPaymentSuccess(@NotNull final PaymentSuccess ps) {
                    User user = User.getUserInfo(ApplyActivity.this);
                    user.setBalance(ps.getBalance());
                    user.save(ApplyActivity.this);
                    hide();
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            AlertDialog.Builder alertDialog = new AlertDialog.Builder(ApplyActivity.this);
                            alertDialog.setTitle("Успешно");
                            alertDialog.setMessage("Оплата прошла успешно! Ваш баланс: " + ps.getBalance() + " \u20BD");
                            alertDialog.setCancelable(false);
                            alertDialog.setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(ApplyActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            });
                            alertDialog.show();
                        }
                    });
                }

                @Override
                public void onPaymentRequest(@NotNull final PaymentRequest pr) {
                    new Handler(getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            paymentRequest = pr;
                            findViewById(R.id.root).setVisibility(View.VISIBLE);
                            ((TextView) findViewById(R.id.name)).setText(pr.getDriverName());
                            ((TextView) findViewById(R.id.car)).setText(pr.getVenchileCode().toUpperCase());
                            String[] route = pr.getRouteInfo().split(" - ");
                            ((TextView) findViewById(R.id.route_from)).setText(route[0]);
                            ((TextView) findViewById(R.id.route_to)).setText(route[1]);
                            ((Button) findViewById(R.id.apply)).setText("Оплатить " + pr.getPrice() + " \u20BD");
                            progressDialog.cancel();
                        }
                    });
                }

                @Override
                public void onPaymentConfirm(@NotNull PaymentConfirm pr) {
                }
            });
        }

        public void run() {
            Log.i(TAG, "BEGIN mConnectedThread");
            byte[] buffer = new byte[1024];
            int bytes;
            commandsProcessor.start();
        }

        void send(PaymentConfirm pc) {
            Log.i(TAG, "Message sent");
            show("Выполняется оплата..\nЭто может занять несколько секунд");
            commandsProcessor.sendPaymentConfirm(pc);
        }

        public void cancel() {
            try {
                commandsProcessor.stop();
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "close() of connect socket failed", e);
            }
        }

        private void show(final String msg) {
            new Handler(getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    pd = new ProgressDialog(ApplyActivity.this);
                    pd.setMessage(msg);
                    pd.show();
                }
            });
        }

        private void hide() {
            if (pd != null) {
                pd.dismiss();
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
                    progressDialog.cancel();
                    Log.d(TAG, "unable to connect");
                    mmSocket.close();
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ApplyActivity.this, "Не удалось подключиться к водителю", Toast.LENGTH_SHORT).show();
                            onBackPressed();
                        }
                    });
                } catch (IOException e2) {
                    Log.d(TAG, "unable to close() " + mSocketType +
                            " socket during connection failure", e2);
                }
                //connectionFailed();
                return;
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
