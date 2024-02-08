package simulation

import java.io.PrintStream

class ToyEvent(val printStream: PrintStream): Event {
    override fun invoke() {
        printStream.println("A toy event occurred.")
    }

}

class ToySimulator(): Simulator() {
    override fun shouldTerminate(): Boolean = false

}