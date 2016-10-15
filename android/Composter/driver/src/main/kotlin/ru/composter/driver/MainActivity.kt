package ru.composter.driver

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    var btListener: SocketListener = SocketListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById(R.id.start)!!.setOnClickListener {
            btListener.working = false
            btListener = SocketListener()
            btListener = SocketListener()
            Thread(btListener).start()
        }
        findViewById(R.id.stop)!!.setOnClickListener {
            btListener.working = false
        }
    }

    class SocketListener : Runnable {

        var working = true
            @Synchronized get

        override fun run() {
            Log.v("Driver", "Start")
            val data = "Hello from Driver! внезапно русский текст"
            val btAdapter = BluetoothAdapter.getDefaultAdapter();
            Log.v("Driver", "${btAdapter.address}")
            val uuid = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66")
            Log.v("Driver", "${uuid}")
            val serverSocket = btAdapter.listenUsingInsecureRfcommWithServiceRecord("ComposterDriver", uuid)
            while (working) {
                Log.v("Driver", "working")
                try {
                    val socket = serverSocket.accept(5000)
                    Log.v("Driver", "accepter")
                    if (socket == null) {
                        Log.w("Driver", "Socket is null!")
                    } else {
                        Log.d("Driver", "${socket.remoteDevice.name}")
                        val input = DataInputStream(socket.inputStream)
                        val output = DataOutputStream(socket.outputStream)
                        ///DataInputStream(input).read
                        //DataOutputStream(vds).writeU
                        while (socket.isConnected) {
                            output.writeUTF(data)
                            Thread.sleep(1000)
                            Log.d("Driver", "sended")
                        }
                        Log.d("Driver", "done")
                    }
                } catch (e: IOException) {
                    Log.v("Driver", e.message)
                }
            }
            Log.d("Driver", "finished")
        }

    }
}
