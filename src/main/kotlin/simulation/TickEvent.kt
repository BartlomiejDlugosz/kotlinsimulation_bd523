package simulation

import java.io.PrintStream

class TickEvent(private val printStream: PrintStream, private val simulator: Simulator): Event {
    override fun invoke() {
        printStream.println("Tick at ${simulator.currentTime()}")
        simulator.schedule(this, 1.0)
    }
}

class TickSimulator(val stoppingTime: Double): Simulator() {
    override fun shouldTerminate(): Boolean = currentTime >= stoppingTime

}