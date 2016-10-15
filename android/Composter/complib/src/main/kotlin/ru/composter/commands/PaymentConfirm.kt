package ru.composter.commands

import java.io.DataInputStream
import java.io.DataOutputStream

data class PaymentConfirm(
        val driverId: String,
        val driverName: String,
        val venchileCode: String,
        val routeInfo: String,
        val price: String,
        val driverSign: String,
        val passangerId: String,
        val passangerSign: String
) {

    companion object {

        fun fromRequest(pr: PaymentRequest, passId: String, passSn: String) = PaymentConfirm(
                driverId = pr.driverId,
                driverName = pr.driverName,
                venchileCode = pr.venchileCode,
                routeInfo = pr.routeInfo,
                price = pr.price,
                driverSign = pr.driverSign,
                passangerId = passId,
                passangerSign = passSn
        )

        fun load(input: DataInputStream) = PaymentConfirm(
                driverId = input.readUTF(),
                driverName = input.readUTF(),
                venchileCode = input.readUTF(),
                routeInfo = input.readUTF(),
                price = input.readUTF(),
                driverSign = input.readUTF(),
                passangerId = input.readUTF(),
                passangerSign = input.readUTF()
        )
    }

    fun save(output: DataOutputStream) {
        with(output) {
            writeUTF(driverId)
            writeUTF(driverName)
            writeUTF(venchileCode)
            writeUTF(routeInfo)
            writeUTF(price)
            writeUTF(driverSign)
            writeUTF(passangerId)
            writeUTF(passangerSign)
        }
    }

}