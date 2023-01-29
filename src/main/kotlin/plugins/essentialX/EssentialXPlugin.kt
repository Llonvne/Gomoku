package plugins.essentialX

import plugins.Essentials
import plugins.essentialX.event.Event
import plugins.essentialX.event.rootPath
import plugins.essentialX.path.URL
import plugins.essentialX.path.URLImpl


enum class EssentialXPluginType {
    Filter, Plugin
}

abstract class EssentialXPlugin(listeningPath: String = rootPath) {
    lateinit var essentials: Essentials
    private val listeningURL = URLImpl(listeningPath)

    fun getListeningUrl(): URL {
        return listeningURL
    }


    abstract fun init()

    open fun getEssentialXPluginType(): EssentialXPluginType {
        return EssentialXPluginType.Plugin
    }

    abstract fun onEvent(event: Event)
}