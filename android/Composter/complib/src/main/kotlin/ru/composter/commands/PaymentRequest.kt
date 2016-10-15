package ru.composter.commands

import java.io.DataInputStream
import java.io.DataOutputStream

data class PaymentRequest(
        val payment_id: String,
        val timestamp: String,
        val driverId: String,
        val driverName: String,
        val venchileCode: String,
        val routeInfo: String,
        val price: String,
        val currency: String,
        val driverSign: String
) {

    companion object {
        fun load(input: DataInputStream) = PaymentRequest(
                payment_id = input.readUTF(),
                timestamp = input.readUTF(),
                driverId = input.readUTF(),
                driverName = input.readUTF(),
                venchileCode = input.readUTF(),
                routeInfo = input.readUTF(),
                price = input.readUTF(),
                currency = input.readUTF(),
                driverSign = input.readUTF()
        )
    }

    fun save(output: DataOutputStream) {
        with(output) {
            writeUTF(payment_id)
            writeUTF(timestamp)
            writeUTF(driverId)
            writeUTF(driverName)
            writeUTF(venchileCode)
            writeUTF(routeInfo)
            writeUTF(price)
            writeUTF(currency)
            writeUTF(driverSign)
        }
    }


}