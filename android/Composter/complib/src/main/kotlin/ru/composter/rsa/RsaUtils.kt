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

    fun encode(key: Key, data: String): ByteArray? {
        var cipherText: ByteArray? = null
        try {
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.ENCRYPT_MODE, key)
            cipherText = cipher.doFinal(data.toByteArray())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return cipherText
    }

    fun decode(key: Key, data: ByteArray): String {
        var dectyptedText: ByteArray? = null
        try {
            val cipher = Cipher.getInstance(ALGORITHM)
            cipher.init(Cipher.DECRYPT_MODE, key)
            dectyptedText = cipher.doFinal(data)

        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return String(dectyptedText!!)
    }

}