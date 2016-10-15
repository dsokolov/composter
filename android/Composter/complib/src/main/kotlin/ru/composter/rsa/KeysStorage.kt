package ru.composter.rsa

import android.content.Context
import java.io.*
import java.security.KeyPair

object KeysStorage {

    private const val FILE = "keys"

    fun hasKeys(context: Context): Boolean {
        val dataDir = context.filesDir
        val keysStorage = File(dataDir, FILE)
        return keysStorage.exists()
    }

    fun storeKeys(context: Context, keyPair: KeyPair) {
        val dataDir = context.filesDir
        val file = File(dataDir, FILE)
        file.delete()
        val out = ObjectOutputStream(FileOutputStream(file))
        out.writeObject(keyPair)
        out.close()
    }

    fun getKeys(context: Context): KeyPair {
        val dataDir = context.filesDir
        val file = File(dataDir, FILE)
        val out = ObjectInputStream(FileInputStream(file))
        val keys = out.readObject() as KeyPair
        out.close()
        return keys
    }

    fun clear(context: Context) {
        val dataDir = context.filesDir
        val file = File(dataDir, FILE)
        file.delete()
    }

}