package plugins

import boardx.BoardXPlugin
import boardx.BoardXPluginType
import boardx.MinPriority
import plugins.essentialX.EssentialXPlugin
import plugins.essentialX.event.Event
import plugins.essentialX.event.rootPath

/**
 * 该插件为查找查找不到时，空插件
 */
object EmptyPlugin : BoardXPlugin, EssentialXPlugin(rootPath) {
    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.SystemPlug
    }

    override fun getPluginPriority(): Int {
        return MinPriority
    }

    override fun onEvent(event: Event) {
    }
}