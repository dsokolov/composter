package ru.composter.driver

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import ru.composter.commands.CommandsProcessor
import ru.composter.commands.PaymentConfirm
import ru.composter.commands.PaymentRequest
import ru.composter.commands.PaymentSuccess
import ru.composter.driver.api.Api
import ru.composter.driver.api.TransactionSubmitRequest
import java.util.*
import java.util.concurrent.Executors
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    var balanceTextView: TextView by Delegates.notNull()
    var statusTextView: TextView by Delegates.notNull()
    val socketListener = SocketListener()
    val balanceListner = BalanceListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar) as Toolbar)
        balanceTextView = findViewById(R.id.balance) as TextView
        statusTextView = findViewById(R.id.status) as TextView

        val executore = Executors.newFixedThreadPool(5)
        executore.execute(socketListener)
        executore.execute(balanceListner)

        val discoverableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0)
        startActivity(discoverableIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        socketListener.working = false
        balanceListner.working = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //menuInflater.inflate(R.menu.history, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun stateWorking() {
        runOnUiThread {
            statusTextView.setText("Готов к работе")
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
        runOnUiThread {
            balanceTextView.setText("$s ₽")
        }
    }

    inner class SocketListener : Runnable {

        var working = true
            @Synchronized get

        private var commandProcerssor: CommandsProcessor? = null

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

                        commandProcerssor = CommandsProcessor(socket, object : CommandsProcessor.Callback {
                            override fun onPaymentSuccess(ps: PaymentSuccess) {
                                throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                            override fun onPaymentConfirm(pr: PaymentConfirm) {
                                statePaymentConfirm()
                                val r = Api.api.paymentConfirm(TransactionSubmitRequest(
                                        route_number = pr.paymentRequest.routeInfo,
                                        currency = pr.paymentRequest.currency,
                                        price = pr.paymentRequest.price,
                                        timestamp = pr.paymentRequest.timestamp,
                                        vehicle_number = pr.paymentRequest.venchileCode,
                                        publican_sign = pr.paymentRequest.driverSign,
                                        payer_sign = pr.passangerSign,
                                        payer_id = pr.passangerId,
                                        payment_id = pr.paymentRequest.payment_id,
                                        publican_id = pr.paymentRequest.driverId
                                ))
                                val b = r.execute()
                                try {
                                    Log.v("Driver", b.body().state)
                                    sendPaymentSuccess(b.body().payer.balance)
                                } catch (e: Exception) {
                                    Log.e("Driver", e.message)
                                } finally {
                                    stateWorking()
                                    stop()
                                }
                            }

                            override fun onPaymentRequest(pr: PaymentRequest) {
                                //throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
                            }

                        })
                        commandProcerssor?.start()
                        commandProcerssor?.sendPaymentRequest(PaymentRequest(
                                driverId = "58014aa4e9e84",
                                driverName = "Иванов Иван Иванович",
                                price = "125",
                                routeInfo = "Самара - Тольятти",
                                venchileCode = "а 234 аа 163 rus",
                                driverSign = "подпись",
                                timestamp = "1234567",
                                currency = "RUR",
                                payment_id = UUID.randomUUID().toString()
                        ))


                        while (socket.isConnected && (commandProcerssor?.working ?: false)) {
                            Thread.sleep(1000)
                        }

                        commandProcerssor?.stop()
                    }


                    Log.d("Driver", "finished")
                } catch (e: Exception) {
                }
            }

        }

        fun sendPaymentSuccess(bal: String) {
            commandProcerssor?.sendPaymentSuccess(PaymentSuccess(bal))
        }

        fun stop() {
            commandProcerssor?.stop()
        }
    }

    inner class BalanceListener : Runnable {

        var working = true

        override fun run() {
            while (working) {
                Log.d("Balance", "working")
                try {
                    val b = Api.api.balance("58014aa4e9e84").execute()
                    val bal = b.body().balance
                    Log.d("Balance", bal)
                    balance(bal)
                } catch (e: Exception) {
                    Log.w("Balance", e.message)
                }
                Thread.sleep(3000)
            }
        }

    }

}