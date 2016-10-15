package ru.composter.driver

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.TextView
import ru.composter.commands.CommandsProcessor
import ru.composter.commands.PaymentConfirm
import ru.composter.commands.PaymentRequest
import java.util.*
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    var balanceTextView: TextView by Delegates.notNull()
    var statusTextView: TextView by Delegates.notNull()
    val socketListener = SocketListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        balanceTextView = findViewById(R.id.balance) as TextView
        statusTextView = findViewById(R.id.status) as TextView
        Thread(socketListener).start()

        balance("999.99")

        val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0)
        startActivity(discoverableIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        socketListener.working = false
    }

    fun stateWorking() {
        runOnUiThread {
            statusTextView.setText("Поиск...")
        }
    }

    fun stateConnection(name: String) {
        runOnUiThread {
            statusTextView.setText("Соединение с $name")
        }
    }

    fun statePaymentConfirm() {
        runOnUiThread {
            statusTextView.setText("Подтверждение оплаты")
        }
    }

    fun balance(s: String) {
        balanceTextView.setText("Баланс: $s ₽")
    }

    inner class SocketListener : Runnable {

        var working = true
            @Synchronized get

        override fun run() {
            Log.v("Driver", "Start")
            val btAdapter = BluetoothAdapter.getDefaultAdapter();
            Log.v("Driver", "${btAdapter.address}")
            val uuid = UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66")
            Log.v("Driver", "${uuid}")
            val serverSocket = btAdapter.listenUsingInsecureRfcommWithServiceRecord("ComposterDriver", uuid)
            while (working) {
                Log.v("Driver", "working")
                stateWorking()
                try {
                    val socket = serverSocket.accept(5000)
                    Log.v("Driver", "accepter")
                    if (socket == null) {
                        Log.w("Driver", "Socket is null!")
                    } else {

                        Log.d("Driver", socket.remoteDevice.name)
                        stateConnection(socket.remoteDevice.name)

                        val commandProcerssor = CommandsProcessor(socket, object : CommandsProcessor.Callback {
                            override fun onPaymentConfirm(pr: PaymentConfirm) {
                                statePaymentConfirm()
                            }

                            override fun onPaymentRequest(pr: PaymentRequest) {
                                //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                        })
                        commandProcerssor.start()
                        commandProcerssor.sendPaymentRequest(PaymentRequest(
                                driverId = "123456",
                                driverName = "Весёлый бомбила Ашот",
                                price = "25",
                                routeInfo = "Кюда надо слющай?",
                                venchileCode = "а999аа63rus",
                                driverSign = "подпись"

                        ))


                        while (socket.isConnected) {
                            Thread.sleep(1000)
                        }

                        commandProcerssor.stop()
                    }


                    Log.d("Driver", "finished")
                } catch (e: Exception) {
                }
            }

        }
    }

}