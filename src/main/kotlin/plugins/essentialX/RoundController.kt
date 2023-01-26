package plugins.essentialX

import BoardX
import BoardXPlugin
import BoardXPluginType
import EssentialXPlugin
import NormalPriority
import PlayerType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import str

class RoundController(args: JsonObject) : EssentialXPlugin(args) {
    var nowplayer: PlayerType

    init {
        nowplayer = PlayerType.valueOf(args.getValue("FirstPlayer").str())
    }

    override fun getPluginPriority(): Int {
        return NormalPriority
    }

    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.RuntimePlug
    }

    override fun onCreate(board: BoardX) {
        println("Now it's a ${nowplayer.name} word drop")
    }

    override fun onSet(x: Int, y: Int, player: PlayerType, board: BoardX) {
        next()
    }

    private fun next() {
        nowplayer = PlayerType.values()[1 - nowplayer.ordinal]
        println("Now it's a ${nowplayer.name} word drop")
    }
}