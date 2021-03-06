package ru.composter.driver

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import ru.composter.commands.CommandsProcessor
import ru.composter.commands.PaymentConfirm
import ru.composter.commands.PaymentRequest
import ru.composter.commands.PaymentSuccess
import java.io.IOException
import java.util.*

class DebugActivity : AppCompatActivity() {

    var btListener: SocketListener = SocketListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug)
        findViewById(R.id.desc)!!.setOnClickListener {
            val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0)
            startActivity(discoverableIntent)
        }
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
                        var b = true
                        Log.d("Driver", "${socket.remoteDevice.name}")
                        val commandProcerssor = CommandsProcessor(socket, object : CommandsProcessor.Callback {
                            override fun onPaymentSuccess(ps: PaymentSuccess) {
                                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onPaymentConfirm(pr: PaymentConfirm) {
                                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onPaymentRequest(pr: PaymentRequest) {
                                //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                        })
                        commandProcerssor.start()
                        ////while (socket.isConnected) {
                        commandProcerssor.sendPaymentRequest(PaymentRequest(
                                driverId = "123456",
                                driverName = "Весёлый бомбила Ашот",
                                price = "25",
                                routeInfo = "Кюда надо слющай?",
                                venchileCode = "а999аа63rus",
                                driverSign = "подпись",
                                payment_id = "",
                                currency = "",
                                timestamp = ""

                        ))
                        //  Thread.sleep(1000)
                        Log.d("Driver", "sended")
                        //}

                        while (b) {
                            Thread.sleep(1000)
                        }
                        commandProcerssor.stop()
                        serverSocket.close()
                        break
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
