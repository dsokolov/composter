package ru.composter.commands

import java.io.DataInputStream
import java.io.DataOutputStream

data class PaymentRequest(
        val driverId: String,
        val driverName: String,
        val venchileCode: String,
        val routeInfo: String,
        val driverSign: String
) {

    companion object {
        fun load(input: DataInputStream) = PaymentRequest(
                driverId = input.readUTF(),
                driverName = input.readUTF(),
                venchileCode = input.readUTF(),
                routeInfo = input.readUTF(),
                driverSign = input.readUTF()
        )
    }

    fun save(output: DataOutputStream) {
        with(output) {
            writeUTF(driverId)
            writeUTF(driverName)
            writeUTF(venchileCode)
            writeUTF(routeInfo)
            writeUTF(driverSign)
        }
    }

}