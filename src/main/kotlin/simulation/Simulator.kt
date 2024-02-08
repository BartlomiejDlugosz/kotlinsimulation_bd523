package simulation

import java.util.LinkedList
import java.util.PriorityQueue

abstract class Simulator: Clock, Scheduler {
    protected var currentTime: Double = 0.0
    protected val eventsList: PriorityQueue<Pair<Event, Double>> = PriorityQueue(compareBy{it.second})
    override fun currentTime(): Double = currentTime

    override fun schedule(event: Event, dt: Double) {
        eventsList.add(event to (currentTime + dt))
    }

    fun execute() {
        var next = eventsList.poll()
        while (next != null) {
            currentTime = next.second
            if (shouldTerminate()) break
            next.first.invoke()
            next = eventsList.poll()
        }
    }

    abstract fun shouldTerminate(): Boolean

}
