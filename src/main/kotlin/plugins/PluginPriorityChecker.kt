package plugins

import boardx.*

class PluginPriorityChecker : BoardXPlugin {
    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.SystemPlug
    }

    override fun getPluginPriority(): Int {
        return MaxPriority
    }

    override fun onCreate(board: BoardX) {
        board.pluginList = board.pluginList.filter {
            if (it.getPluginPriority() !in MinPriority..MaxPriority) {
                println("The ${it.javaClass.simpleName} plugin has an abnormal priority ${it.getPluginPriority()}, the plugin will not be enabled, the normal priority is between $MinPriority and $MaxPriority")
            }
            return@filter it.getPluginPriority() in MinPriority..MaxPriority
        }.toMutableList()

        board
    }
}