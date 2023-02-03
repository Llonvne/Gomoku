package plugins.essentialX

import boardx.BoardX
import plugins.essentialX.event.Event
import plugins.essentialX.observerPattern.Observer
import java.nio.file.Path


enum class EssentialXPluginType {
    Filter, Plugin
}

abstract class EssentialXPlugin() {
    open fun init(addObserver: (Observer<Event>) -> Unit, observerPath: Path, sender: (Event) -> Unit, board: BoardX) {
    }

    abstract fun onEvent(event: Event)

    open fun registerCustomerPath(): List<String> {
        return listOf()
    }

    open fun getEssentialXPluginType(): EssentialXPluginType {
        return EssentialXPluginType.Plugin
    }
}