package plugins.essentialX

import BoardX
import BoardXPlugin
import BoardXPluginType
import EssentialXPlugin
import NormalPriority
import PlayerType
import kotlinx.serialization.json.JsonObject

class DropAlert(args: JsonObject) : EssentialXPlugin(args) {
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