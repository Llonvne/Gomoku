package plugins.essentialX

import plugins.Essentials
import plugins.essentialX.event.Event
import plugins.essentialX.event.rootPath
import java.nio.file.Path
import kotlin.io.path.Path


enum class EssentialXPluginType {
    Filter, Plugin
}

abstract class EssentialXPlugin(listeningPath: String = rootPath) {
    lateinit var essentials: Essentials

    private val path = Path(listeningPath)

    abstract fun init()

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