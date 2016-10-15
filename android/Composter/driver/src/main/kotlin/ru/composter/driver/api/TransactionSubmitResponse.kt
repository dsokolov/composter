package ru.composter.driver.api

data class TransactionSubmitResponse(
        val payer: Payer,
        val publican: Publican,
        val transactionId: String,
        val state: String
) {

    data class Payer(
            val id: String,
            val balance: String
    )

    data class Publican(
            val id: String,
            val balance: String
    )

}