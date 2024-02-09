package simulation

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals

class TickSimulatorTest {
    @Test
    fun `test tick simulator`() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)

        val tickSimulator: Simulator = TickSimulator(10.0)
        tickSimulator.schedule(TickEvent(printStream, tickSimulator), 0.5)
        tickSimulator.execute()

        assertEquals(
            """
            Tick at 0.5
            Tick at 1.5
            Tick at 2.5
            Tick at 3.5
            Tick at 4.5
            Tick at 5.5
            Tick at 6.5
            Tick at 7.5
            Tick at 8.5
            Tick at 9.5

            """.trimIndent(),
            outputStream.toString(),
        )
    }

    @Test
    fun `test multiple events`() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)

        val tickSimulator: Simulator = TickSimulator(10.0)
        tickSimulator.schedule(TickEvent(printStream, tickSimulator), 0.5)
        tickSimulator.schedule(TickEvent(printStream, tickSimulator), 2.0)
        tickSimulator.execute()

        assertEquals(
            """
            Tick at 0.5
            Tick at 1.5
            Tick at 2.0
            Tick at 2.5
            Tick at 3.0
            Tick at 3.5
            Tick at 4.0
            Tick at 4.5
            Tick at 5.0
            Tick at 5.5
            Tick at 6.0
            Tick at 6.5
            Tick at 7.0
            Tick at 7.5
            Tick at 8.0
            Tick at 8.5
            Tick at 9.0
            Tick at 9.5

            """.trimIndent(),
            outputStream.toString(),
        )
    }

    @Test
    fun `test negative schedule time`() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)

        val tickSimulator: Simulator = TickSimulator(10.0)
        tickSimulator.schedule(TickEvent(printStream, tickSimulator), -0.5)
        tickSimulator.execute()

        assertEquals(
            """
            Tick at -0.5
            Tick at 0.5
            Tick at 1.5
            Tick at 2.5
            Tick at 3.5
            Tick at 4.5
            Tick at 5.5
            Tick at 6.5
            Tick at 7.5
            Tick at 8.5
            Tick at 9.5

            """.trimIndent(),
            outputStream.toString(),
        )
    }

    @Test
    fun `test zero schedule time`() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)

        val tickSimulator: Simulator = TickSimulator(10.0)
        tickSimulator.schedule(TickEvent(printStream, tickSimulator), 0.0)
        tickSimulator.execute()

        assertEquals(
            """
            Tick at 0.0
            Tick at 1.0
            Tick at 2.0
            Tick at 3.0
            Tick at 4.0
            Tick at 5.0
            Tick at 6.0
            Tick at 7.0
            Tick at 8.0
            Tick at 9.0

            """.trimIndent(),
            outputStream.toString(),
        )
    }
}
