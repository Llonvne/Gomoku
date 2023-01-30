package plugins.essentialX

import plugins.essentialX.event.Event
import plugins.essentialX.event.ListenerAllPath

class EventPrinter : EssentialXPlugin(ListenerAllPath) {
    override fun init() {
    }

    override fun onEvent(event: Event) {
        println(event)
    }
}