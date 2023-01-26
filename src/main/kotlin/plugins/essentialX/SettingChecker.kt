package plugins.essentialX

import BoardX
import BoardXPlugin
import BoardXPluginType
import EssentialXPlugin
import NormalPriority
import PlayerType
import PointType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.modules.EmptySerializersModule
import plugins.EmptyPlugin
import java.lang.Exception

class SettingChecker(args: JsonObject) : EssentialXPlugin(args) {
    private var roundController: RoundController? = null
    override fun init(board: BoardX) {
        // 尝试获得 RoundController 插件
        if (essentials.essentialXPluginNameList().contains("RoundController")) {
            roundController = essentials.getEssentialXPlugin("RoundController") as RoundController
        } else {
            println("You do not have EssentialX.RoundController installed, the section on turn control will not be available")
            roundController = null
        }
    }

    override fun getPluginPriority(): Int {
        return NormalPriority
    }

    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.RuntimePlug
    }

    override fun beforeSetting(x: Int, y: Int, player: PlayerType, board: BoardX): Boolean {
        return try {

            if (roundController?.nowplayer != null && roundController?.nowplayer == player) {
                println("Not your turn $player")
                return false
            }
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