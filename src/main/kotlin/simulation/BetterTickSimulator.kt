package simulation

import java.io.PrintStream
import java.util.Random

class BetterTickSimulator(private val printStream: PrintStream, private val stoppingTime: Double, val rnd: Random? = null) : Simulator() {
    inner class TickEvent : Event {
        private fun getNext(): Double = rnd?.nextDouble() ?: 1.0

        override fun invoke() {
            printStream.println("Tick at $currentTime")
            schedule(this, getNext())
        }
    }

    override fun shouldTerminate(): Boolean = currentTime >= stoppingTime
}
