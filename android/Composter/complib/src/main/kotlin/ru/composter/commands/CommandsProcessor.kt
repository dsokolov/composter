package ru.composter.commands

import android.bluetooth.BluetoothSocket
import java.io.DataInputStream
import java.io.DataOutputStream

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
                val s = input.readUTF()
                callback(s)
                Thread.sleep(1000)
            }
        }

    }

}