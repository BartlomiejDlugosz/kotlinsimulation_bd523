package simulation

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals

class BetterTickSimulatorTest {

    @Test
    fun `test better tick simulator`() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val betterTickSimulator = BetterTickSimulator(printStream, 10.0)
        betterTickSimulator.schedule(betterTickSimulator.TickEvent(), 0.5)
        betterTickSimulator.execute()

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
    fun `test simulator with different tick intervals`() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val betterTickSimulator = BetterTickSimulator(printStream, 12.0)
        betterTickSimulator.schedule(betterTickSimulator.TickEvent(), 1.0)
        betterTickSimulator.execute()

        assertEquals(
            """
                Tick at 1.0
                Tick at 2.0
                Tick at 3.0
                Tick at 4.0
                Tick at 5.0
                Tick at 6.0
                Tick at 7.0
                Tick at 8.0
                Tick at 9.0
                Tick at 10.0
                Tick at 11.0

            """.trimIndent(),
            outputStream.toString(),
        )
    }

    @Test
    fun `test simulator with no ticks scheduled`() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val betterTickSimulator = BetterTickSimulator(printStream, 10.0)
        betterTickSimulator.execute()

        assertEquals(
            "",
            outputStream.toString(),
        )
    }
}