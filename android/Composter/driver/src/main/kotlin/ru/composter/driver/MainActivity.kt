package ru.composter.driver

import android.bluetooth.BluetoothAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.io.PrintStream
import java.util.*

class MainActivity : AppCompatActivity() {

    var btListener: SocketListener = SocketListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById(R.id.start).setOnClickListener {
            btListener.working = false
            btListener = SocketListener()
            Thread(SocketListener()).start()
        }
        findViewById(R.id.stop).setOnClickListener {
            btListener.working = false
        }
    }

    class SocketListener : Runnable {

        var working = true

        override fun run() {
            Log.v("Driver", "Start")
            val data = "Hello from Driver!"
            val btAdapter = BluetoothAdapter.getDefaultAdapter();
            val serverSocket = btAdapter.listenUsingInsecureRfcommWithServiceRecord("ComposterDriver", UUID.randomUUID())
            while (working) {
                Log.v("Driver", "working")
                val socket = serverSocket.accept()
                Log.v("Driver", "accepter")
                if (socket == null) {
                    Log.w("Driver", "Socket is null!")
                } else {
                    Log.v("Driver", "${socket.remoteDevice.name}")
                    val input = socket.inputStream
                    val output = socket.outputStream
                    val writer = PrintStream(output)
                    writer.println(data)
                    socket.close()
                    Log.w("Driver", "done")
                }
            }
            Log.w("Driver", "finished")
        }

    }
}
