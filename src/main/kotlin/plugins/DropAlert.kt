package plugins

import BoardX
import BoardXPlugin
import BoardXPluginType
import NormalPriority
import PlayerType

class DropAlert : BoardXPlugin() {
    override fun getPluginPriority(): Int {
        return NormalPriority
    }

    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.RuntimePlug
    }

    override fun onSet(x: Int, y: Int, player: PlayerType, board: BoardX) {
        println("[Alert] The player ${player.name} drop on $x,$y")
    }
}