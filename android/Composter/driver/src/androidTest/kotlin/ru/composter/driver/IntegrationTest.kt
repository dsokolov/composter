package ru.composter.driver

import org.junit.Assert
import org.junit.Test
import ru.composter.driver.api.Api
import ru.composter.driver.api.TransactionSubmitRequest
import java.util.*

class IntegrationTest {

    @Test fun balance() {
        val b = Api.api.balance("58014aa4e9e84")
        val balance = b.execute()
        Assert.assertEquals("400", balance.body().balance)
    }

    @Test fun paymentSubmit() {
        val r = Api.api.paymentConfirm(TransactionSubmitRequest(
                route_number = "47к",
                vehicle_number = "x001ep163rus",
                price = "25",
                currency = "RUR",
                timestamp = "1234567890",
                payment_id = UUID.randomUUID().toString(),
                publican_id = "58014aa4e9e84",
                publican_sign = "ddddd",
                payer_id = "580149d1edf4d",
                payer_sign = "ffffffff"
        ))
        val sumb = r.execute()
        Assert.assertEquals(200, sumb.code())
        Assert.assertEquals("success", sumb.body().state)
    }

}
