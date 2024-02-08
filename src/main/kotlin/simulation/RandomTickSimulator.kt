package simulation

import java.io.PrintStream

class RandomTickSimulator(
    private val printStream: PrintStream,
    private val stoppingTime: Double,
    range: Pair<Double, Double>
): Simulator() {
    val uniformSample = UniformSample(range.first, range.second)

    init {
        if (!(range.first < range.second && range.first > 0)) throw IllegalArgumentException()
    }
    inner class TickEvent: Event {
        override fun invoke() {
            printStream.println("Tick at $currentTime")
            schedule(this, uniformSample.nextSample())
        }

    }

    override fun shouldTerminate(): Boolean = currentTime >= stoppingTime
}