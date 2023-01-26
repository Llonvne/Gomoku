package plugins

import BoardXPlugin
import BoardXPluginType
import EssentialXPlugin
import MinPriority
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject

/**
 * 该插件为查找查找不到时，空插件
 */
object EmptyPlugin : BoardXPlugin, EssentialXPlugin(buildJsonObject { }) {

    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.SystemPlug
    }

    override fun getPluginPriority(): Int {
        return MinPriority
    }
}