package plugins.essentialX

import plugins.essentialX.event.Event
import plugins.essentialX.event.rootPath
import plugins.essentialX.observerPattern.Observer
import java.nio.file.Path
import kotlin.io.path.Path


enum class EssentialXPluginType {
    Filter, Plugin
}

abstract class EssentialXPlugin(listeningPath: String = rootPath) {

    private val path = Path(listeningPath)

    open fun init(addObserver: (Observer<Event>) -> Unit, observerPath: Path) {
    }

    abstract fun onEvent(event: Event)

    open fun registerCustomerPath(): List<String> {
        return listOf()
    }

    open fun getEssentialXPluginType(): EssentialXPluginType {
        return EssentialXPluginType.Plugin
    }

    fun getPath(): Path {
        return path
    }

}