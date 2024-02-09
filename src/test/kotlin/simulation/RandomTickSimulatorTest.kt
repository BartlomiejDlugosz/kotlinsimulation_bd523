package simulation

import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RandomTickSimulatorTest {
    @Test
    fun `test random tick simulator`() {
        // Repeat the simulation several times
        for (repeat in 0..20) {
            val outputStream = ByteArrayOutputStream()
            val printStream = PrintStream(outputStream)
            val randomTickSimulator = RandomTickSimulator(printStream, 10.0, Pair(1.0, 2.0))
            randomTickSimulator.schedule(randomTickSimulator.TickEvent(), 0.5)
            randomTickSimulator.execute()

            // We cannot know exactly what the simulation should produce,
            // but we can check that the first time is 0.5, that the times
            // then increase by at least 1.0 and at most 2.0, and that no
            // time exceeds 10.0.
            analyzeOutput(outputStream)
        }
    }

    @Test
    fun `test random tick simulator with different time intervals`() {
        // Repeat simulation with different time intervals
        for (repeat in 0..20) {
            val outputStream = ByteArrayOutputStream()
            val printStream = PrintStream(outputStream)
            val randomTickSimulator = RandomTickSimulator(printStream, 10.0, Pair(2.0, 3.0))
            randomTickSimulator.schedule(randomTickSimulator.TickEvent(), 0.5)
            randomTickSimulator.execute()

            // Times should increase by at least 2.0 and at most 3.0
            analyzeOutput(outputStream, firstTime = 0.5, minIncrement = 2.0, maxIncrement = 3.0)
        }
    }

    @Test
    fun `test random tick simulator with random initial time`() {
        for (repeat in 0..20) {
            val initialTime = Random.nextDouble(0.5, 1.0)
            val outputStream = ByteArrayOutputStream()
            val printStream = PrintStream(outputStream)
            val randomTickSimulator = RandomTickSimulator(printStream, 10.0, Pair(1.0, 2.0))
            randomTickSimulator.schedule(randomTickSimulator.TickEvent(), initialTime)
            randomTickSimulator.execute()

            // First time should be equal to initialTime
            analyzeOutput(outputStream, firstTime = initialTime)
        }
    }

    private fun analyzeOutput(outputStream: ByteArrayOutputStream, firstTime: Double = 0.5, minIncrement: Double = 1.0, maxIncrement: Double = 2.0) {
        val components = outputStream.toString().split("\n")
        val regex = """Tick at (\d+\.\d+)""".toRegex()
        var foundLastLine = false
        var lastTime: Double? = null
        for (component in components) {
            assertFalse(foundLastLine)
            if (component == "") {
                foundLastLine = true
            } else {
                assertTrue(regex.containsMatchIn(component))
                val matchResult = regex.find(component)
                val (matchString) = matchResult!!.destructured
                val time = matchString.toDouble()
                assertTrue(time <= 10.0)
                if (lastTime == null) {
                    assertEquals(firstTime, time)
                } else {
                    assertTrue(time >= lastTime + minIncrement)
                    assertTrue(time <= lastTime + maxIncrement)
                }
                lastTime = time
            }
        }
    }
}