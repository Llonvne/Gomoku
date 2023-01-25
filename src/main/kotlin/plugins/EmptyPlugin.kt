package plugins

import BoardXPlugin
import BoardXPluginType
import MinPriority

/**
 * 该插件为查找查找不到时，空插件
 */
object EmptyPlugin : BoardXPlugin {
    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.SystemPlug
    }

    override fun getPluginPriority(): Int {
        return MinPriority
    }
}