package ru.composter.rsa

import java.security.Key
import java.security.KeyPair
import java.security.KeyPairGenerator
import javax.crypto.Cipher

object RsaUtils {

    private val ALGORITHM = "RSA"
    private val SIZE = 1024

    fun generateKeys(): KeyPair {
        val keyGen = KeyPairGenerator.getInstance(ALGORITHM)
        keyGen.initialize(SIZE)
        return keyGen.generateKeyPair()
    }

    fun encode(key: Key, data: String): ByteArray {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, key)
        return cipher.doFinal(data.toByteArray())
    }

    fun decode(key: Key, data: ByteArray): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, key)
        val b = cipher.doFinal(data)
        return String(b)
    }

}