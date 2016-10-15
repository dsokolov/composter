package ru.composter.commands

import java.io.DataInputStream
import java.io.DataOutputStream

data class PaymentSuccess(
        val balance: String
) {

    companion object {

        fun load(input: DataInputStream) = PaymentSuccess(
                balance = input.readUTF()
        )
    }

    fun save(output: DataOutputStream) {
        with(output) {
            writeUTF(balance)
        }
    }

}