package simulation

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.Test
import kotlin.test.assertEquals

class ToySimulatorTest {

    private fun getToySimulatorOutput(toySimulator: Simulator, printStream: PrintStream, numOfToys: Int) {
        for (i in 1..numOfToys) {
            toySimulator.schedule(ToyEvent(printStream), i.toDouble())
        }
        toySimulator.execute()
    }

    @Test
    fun `test toy simulator with 10 toys`() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val toySimulator: Simulator = ToySimulator()

        getToySimulatorOutput(toySimulator, printStream, 10)

        assertEquals(
            """
                A toy event occurred.
                A toy event occurred.
                A toy event occurred.
                A toy event occurred.
                A toy event occurred.
                A toy event occurred.
                A toy event occurred.
                A toy event occurred.
                A toy event occurred.
                A toy event occurred.

            """.trimIndent(),
            outputStream.toString(),
        )
    }

    @Test
    fun `test toy simulator with no toys`() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val toySimulator: Simulator = ToySimulator()

        getToySimulatorOutput(toySimulator, printStream, 0)

        assertEquals(
            "",
            outputStream.toString()
        )
    }

    @Test
    fun `test toy simulator with 1 toy`() {
        val outputStream = ByteArrayOutputStream()
        val printStream = PrintStream(outputStream)
        val toySimulator: Simulator = ToySimulator()

        getToySimulatorOutput(toySimulator, printStream, 1)

        assertEquals(
            """
                A toy event occurred.

            """.trimIndent(),
            outputStream.toString()
        )
    }
}