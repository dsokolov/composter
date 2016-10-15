package ru.composter.complib

import android.content.Context
import android.support.test.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.composter.rsa.KeysStorage
import ru.composter.rsa.RsaUtils
import kotlin.properties.Delegates

class KeyStorageTest {

    var context: Context by Delegates.notNull()

    @Before fun setUp() {
        context = InstrumentationRegistry.getTargetContext()
    }

    @Test fun clearAndHas() {
        KeysStorage.clear(context)
        Assert.assertFalse(KeysStorage.hasKeys(context))
    }

    @Test fun addKeysClear() {
        val keys = RsaUtils.generateKeys()
        KeysStorage.storeKeys(context, keys)
        Assert.assertTrue(KeysStorage.hasKeys(context))
        KeysStorage.clear(context)
        Assert.assertFalse(KeysStorage.hasKeys(context))
    }

    @Test fun addKeys() {
        val keys = RsaUtils.generateKeys()
        KeysStorage.storeKeys(context, keys)
        Assert.assertTrue(KeysStorage.hasKeys(context))
        val storedKeys = KeysStorage.getKeys(context)
        Assert.assertEquals(keys.private, storedKeys.private)
        Assert.assertEquals(keys.public, storedKeys.public)
    }

}