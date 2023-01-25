package plugins

import BoardX
import BoardXPlugin
import BoardXPluginType
import NormalPriority

class Essentials : BoardXPlugin() {
    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.SystemPlug
    }

    override fun getPluginPriority(): Int {
        return NormalPriority
    }

    override fun init(board: BoardX) {
    }
}