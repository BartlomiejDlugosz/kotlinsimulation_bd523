package simulation

import kotlin.math.ln

class UniformSample(val a: Double, val b: Double): TimeDelay {
    override fun nextSample(): Double {
        val u = Math.random()
        return (u * (b - a)) + a
    }

}

class ExponentialSample(val r: Double): TimeDelay {
    override fun nextSample(): Double {
        val u = Math.random()
        return -ln(u) / r
    }

}