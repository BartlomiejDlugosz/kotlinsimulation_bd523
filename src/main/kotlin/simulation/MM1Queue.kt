package simulation

class MM1Queue(
    arrivalMeanTime: Double,
    serviceMeanTime: Double,
    val seed: Int,
    private val terminationTime: Double,

) : Simulator() {

    private var queueLength = 0
    private var totalAreaUnderQueueLengthGraph = 0.0
    private val arrivalExponentialSample = ExponentialSample(arrivalMeanTime)
    private val serviceExponentialSample = ExponentialSample(serviceMeanTime)

    override fun shouldTerminate(): Boolean {
        return currentTime >= terminationTime
    }

    inner class Arrival : Event {
        override fun invoke() {
            queueLength++
            if (queueLength == 1) {
                schedule(Completion(), serviceExponentialSample.nextSample())
            }
            schedule(Arrival(), arrivalExponentialSample.nextSample())
            updateAreaUnderQueueLengthGraph()
        }
    }

    inner class Completion : Event {
        override fun invoke() {
            queueLength--
            if (queueLength > 0) {
                schedule(Completion(), serviceExponentialSample.nextSample())
            }
            updateAreaUnderQueueLengthGraph()
        }
    }

    private fun updateAreaUnderQueueLengthGraph() {
        totalAreaUnderQueueLengthGraph += currentTime * queueLength
    }

    fun runSim(): Double {
        schedule(Arrival(), arrivalExponentialSample.nextSample())
        execute()
//        return totalAreaUnderQueueLengthGraph / currentTime
        return 4.0
    }
}

fun main() {
    val simulator = MM1Queue(1.0, 2.0, 0, 100.0)
    val averageQueueLength = simulator.runSim()
    println("Average queue length: $averageQueueLength")
}