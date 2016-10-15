package ru.composter.driver

import org.junit.Assert
import org.junit.Test
import ru.composter.driver.api.Api

class IntegrationTest {

    @Test fun balance() {
        val b = Api.api.balance("58014aa4e9e84")
        val balance = b.execute()
        Assert.assertEquals("400", balance.body().balance)
    }

}
