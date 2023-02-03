package plugins.essentialX

import boardx.BoardX
import plugins.essentialX.annotations.ListenerPath
import plugins.essentialX.event.Event
import plugins.essentialX.event.ListenerAllPath
import plugins.essentialX.observerPattern.Observer
import java.nio.file.Path

@ListenerPath(ListenerAllPath)
class EventPrinter : EssentialXPlugin() {

    private lateinit var p: Path
    override fun init(
        addObserver: (Observer<Event>) -> Unit,
        observerPath: Path,
        sender: (Event) -> Unit,
        board: BoardX
    ) {
        p = observerPath
        addObserver(Observer { println("12345") })
    }

    override fun onEvent(event: Event) {
        println(event)
    }
}