package plugins.essentialX

import board.PlayerType
import boardx.NormalPriority
import plugins.essentialX.event.Event
import plugins.essentialX.event.EventType
import plugins.essentialX.event.ListenerAllPath
import plugins.essentialX.observerPattern.Observer
import java.nio.file.Path
import kotlin.io.path.pathString

class EventPrinter : EssentialXPlugin(ListenerAllPath) {

    private lateinit var p: Path
    override fun init(addObserver: (Observer<Event>) -> Unit, observerPath: Path) {
        p = observerPath
        addObserver(object : Observer<Event> {
            override fun update(value: Event) {
                println("12345")
            }
        })
    }

    override fun onEvent(event: Event) {
        println(event)
    }
}