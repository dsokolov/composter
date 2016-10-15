package ru.composter.complib

import org.junit.Assert
import org.junit.Test
import ru.composter.rsa.RsaUtils

class RsaUtilsTest {

    @Test fun encodeDecode() {
        val text = "Hello! Привет!"
        val keys = RsaUtils.generateKeys()
        val b = RsaUtils.encode(keys.private, text)
        val s = RsaUtils.decode(keys.public, b)
        Assert.assertEquals(text, s)
    }

}