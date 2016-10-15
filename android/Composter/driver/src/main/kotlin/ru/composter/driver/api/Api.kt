package ru.composter.driver.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    companion object {

        private val retrofit = Retrofit.Builder().
                baseUrl("http://bigvill.elizar9l.bget.ru/").
                addConverterFactory(GsonConverterFactory.create()).
                build()
        val api: Api = retrofit.create(Api::class.java)

    }

    @POST("transaction.php?m=submit") fun paymentConfirm(@Body body: TransactionSubmitRequest): Call<TransactionSubmitResponse>

    @GET("balance.php") fun balance(@Query("id") userId: String): Call<BalanceResponse>

}