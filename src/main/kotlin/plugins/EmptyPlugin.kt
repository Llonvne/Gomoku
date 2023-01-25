package plugins

import BoardXPlugin
import BoardXPluginType
import MinPriority

object EmptyPlugin : BoardXPlugin() {
    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.SystemPlug
    }

    override fun getPluginPriority(): Int {
        return MinPriority
    }
}