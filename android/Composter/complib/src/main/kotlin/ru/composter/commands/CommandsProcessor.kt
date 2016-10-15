package ru.composter.commands

import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

class CommandsProcessor(
        socket: BluetoothSocket,
        val callback: Callback
) {

    companion object {
        private const val PAYMNET_REQUEST = "payment_request"
        private const val PAYMNET_CONFIRM = "payment_confirm"
    }

    private val input = DataInputStream(socket.inputStream)
    private val output = DataOutputStream(socket.outputStream)
    private var wather: Wather = Wather()
    val working: Boolean
        get() = wather.working


    fun sendString(s: String) {
        output.writeUTF(s)
    }

    fun sendPaymentRequest(pr: PaymentRequest) {
        output.writeUTF(PAYMNET_REQUEST)
        pr.save(output)
    }

    fun sendPaymentConfirm(pc: PaymentConfirm) {
        output.writeUTF(PAYMNET_CONFIRM)
        pc.save(output)
    }

    fun start() {
        wather.working = false
        wather = Wather()
        Thread(wather).start()
    }

    fun stop() {
        wather.working = true
    }

    inner class Wather : Runnable {

        var working = true

        override fun run() {
            while (working) {
                try {
                    val s = input.readUTF()
                    when (s) {
                        PAYMNET_REQUEST -> {
                            val pr = PaymentRequest.load(input)
                            callback.onPaymentRequest(pr)
                        }
                        PAYMNET_CONFIRM -> {
                            val pc = PaymentConfirm.load(input)
                            callback.onPaymentConfirm(pc)
                        }
                        else -> {
                            //callback(s)
                        }
                    }
                } catch (e: IOException) {
                    Log.w("Driver", e.message)
                    working = false
                }
                Thread.sleep(1000)
            }
        }

    }

    interface Callback {

        fun onPaymentRequest(pr: PaymentRequest)

        fun onPaymentConfirm(pr: PaymentConfirm)

    }

}