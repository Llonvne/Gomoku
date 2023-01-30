package plugins.essentialX

import plugins.essentialX.event.Event
import java.util.concurrent.LinkedBlockingDeque
import java.util.concurrent.PriorityBlockingQueue

class EventQueue(private val sender: (Event) -> Unit) {
    private val queue = PriorityBlockingQueue<Event>(
        1000
    ) { o1, o2 -> return@PriorityBlockingQueue o1.getPriority().compareTo(o2.getPriority()) }

    private val processThread = Thread {
        while (queue.isNotEmpty()) {
            sender(queue.poll())
        }
    }

    init {
        processThread.isDaemon = true
        processThread.start()
    }

    fun inQueueFunc(): (Event) -> Unit {
        return queue::put
    }
}