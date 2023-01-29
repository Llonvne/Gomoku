package plugins.essentialX

import plugins.Essentials
import plugins.essentialX.event.Event
import plugins.essentialX.event.rootPath


enum class EssentialXPluginType {
    Filter, Plugin
}

abstract class EssentialXPlugin(listeningPath: String = rootPath) {
    lateinit var essentials: Essentials

    abstract fun init()

    open fun getEssentialXPluginType(): EssentialXPluginType {
        return EssentialXPluginType.Plugin
    }

    abstract fun onEvent(event: Event)
}