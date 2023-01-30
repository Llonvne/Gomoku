package plugins.essentialX

import plugins.essentialX.event.Event
import plugins.essentialX.event.ListenerAllPath
import plugins.essentialX.event.ListenerFailurePath
import plugins.essentialX.event.ListenerSuccessPath
import plugins.essentialX.observerPattern.Observable
import java.nio.file.Path
import java.util.concurrent.PriorityBlockingQueue
import kotlin.io.path.Path

class EventQueue(private val map: MutableMap<Path, Observable<Event>>) {
    private val queue = PriorityBlockingQueue<Event>(
        1000
    ) { o1, o2 -> return@PriorityBlockingQueue o1.getPriority().compareTo(o2.getPriority()) }

    private val processThread = Thread {
        while (true) {
            if (queue.isNotEmpty()) {
                val event = queue.poll()
                val target = map[Path(event.getPath())]

                // 通知 All
                map[Path(ListenerAllPath)]!!.notifyObservers(event)

                if (target != null) {
                    // 通知 success
                    map[Path(ListenerSuccessPath)]!!.notifyObservers(event)
                    // 通知对应路径 Path
                    map[Path(event.getPath())]?.notifyObservers(event)
                } else {
                    map[Path(ListenerFailurePath)]!!.notifyObservers(event)
                }
            }
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