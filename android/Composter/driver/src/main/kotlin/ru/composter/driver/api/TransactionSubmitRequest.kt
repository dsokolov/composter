package ru.composter.driver.api

data class TransactionSubmitRequest(
        val route_number: String,
        val vehicle_number: String,
        val price: String,
        val currency: String,
        val timestamp: String,
        val payment_id: String,
        val publican_id: String,
        val publican_sign: String,
        val payer_id: String,
        val payer_sign: String
)