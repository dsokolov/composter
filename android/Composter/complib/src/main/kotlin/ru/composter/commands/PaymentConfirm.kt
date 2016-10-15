package ru.composter.commands

import java.io.DataInputStream
import java.io.DataOutputStream

data class PaymentConfirm(
        val paymentRequest: PaymentRequest,
        val passangerId: String,
        val passangerSign: String
) {

    companion object {

        fun fromRequest(pr: PaymentRequest, passId: String, passSn: String) = PaymentConfirm(
                paymentRequest = pr,
                passangerId = passId,
                passangerSign = passSn
        )

        fun load(input: DataInputStream) = PaymentConfirm(
                paymentRequest = PaymentRequest.load(input),
                passangerId = input.readUTF(),
                passangerSign = input.readUTF()
        )
    }

    fun save(output: DataOutputStream) {
        with(output) {
            paymentRequest.save(output)
            writeUTF(passangerId)
            writeUTF(passangerSign)
        }
    }

}