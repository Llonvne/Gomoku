package plugins.essentialX

import BoardX
import BoardXPlugin
import BoardXPluginType
import EssentialXPlugin
import NormalPriority
import PlayerType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive

class RoundController(args: JsonObject) : EssentialXPlugin(args) {
    private var firstPlayer: PlayerType

    init {
        firstPlayer = PlayerType.valueOf(args.getValue("FirstPlayer").jsonPrimitive.content)
    }

    override fun getPluginPriority(): Int {
        return NormalPriority
    }

    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.RuntimePlug
    }

    override fun init(board: BoardX) {

    }
}