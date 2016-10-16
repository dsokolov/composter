package ru.composter.complib

import android.content.Context
import android.support.test.InstrumentationRegistry
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.composter.commands.PaymentRequest
import java.io.*
import kotlin.properties.Delegates

class StreamTest {

    var context: Context by Delegates.notNull()

    @Before fun setUp() {
        context = InstrumentationRegistry.getTargetContext()
    }

    @Test fun a() {
        val dataDir = context.filesDir
        val f = File(dataDir, "tmp")
        f.delete()
        val output = DataOutputStream(FileOutputStream(f))
        output.writeUTF("A")
        output.writeUTF("B")
        output.close()

        val input = DataInputStream(FileInputStream(f))
        Assert.assertEquals("A", input.readUTF())
        Assert.assertEquals("B", input.readUTF())
        input.close()
    }

    @Test fun paymentRequest() {
        val dataDir = context.filesDir
        val f = File(dataDir, "tmp")
        f.delete()
        val output = DataOutputStream(FileOutputStream(f))
        val pr1 = PaymentRequest("A", "B", "C", "D", "200", "sign", "1232", "RUR", "cddfd")
        pr1.save(output)
        output.close()

        val input = DataInputStream(FileInputStream(f))
        val pr2 = PaymentRequest.load(input)
        input.close()

        Assert.assertEquals(pr1, pr2)
    }

}
