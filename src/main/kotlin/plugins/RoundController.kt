package plugins

import BoardX
import BoardXPlugin
import BoardXPluginType
import NormalPriority
import PlayerType

class RoundController(firstPlayer:PlayerType) : BoardXPlugin() {
    override fun getPluginPriority(): Int {
        return NormalPriority
    }

    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.RuntimePlug
    }

    override fun init(board: BoardX) {

    }
}