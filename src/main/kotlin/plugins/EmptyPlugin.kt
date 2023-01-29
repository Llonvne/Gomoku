package plugins

import BoardXPlugin
import BoardXPluginType
import EssentialXPlugin
import EssentialXPluginType
import MinPriority
import kotlinx.serialization.json.buildJsonObject
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

    override fun init() {
    }

    override fun onEvent(event: Event) {
    }
}