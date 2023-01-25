package plugins.essentialX

import BoardX
import BoardXPlugin
import BoardXPluginType
import EssentialXPlugin
import NormalPriority
import PlayerType
import PointType
import kotlinx.serialization.json.JsonObject
import java.lang.Exception

class SettingChecker(args: JsonObject) : EssentialXPlugin(args) {
    override fun getPluginPriority(): Int {
        return NormalPriority
    }

    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.RuntimePlug
    }

    override fun beforeSetting(x: Int, y: Int, player: PlayerType, board: BoardX): Boolean {
        return try {
            if (board.direct()[x][y] == PointType.Empty) {
                true
            } else {
                println("[Warning] $x,$y 处已有 ${player.name} 子")
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}