package ru.composter.commands

import android.bluetooth.BluetoothSocket
import android.util.Log
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException

class CommandsProcessor(
        socket: BluetoothSocket,
        val callback: (String) -> (Unit)
) {

    private val input = DataInputStream(socket.inputStream)
    private val output = DataOutputStream(socket.outputStream)
    private var wather: Wather = Wather()

    fun sendString(s: String) {
        output.writeUTF(s)
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
                    callback(s)
                } catch (e: IOException) {
                    Log.w("Driver", e.message)
                }
                Thread.sleep(1000)
            }
        }

    }

}