package boardx

import board.PlayerType

enum class BoardXPluginType {
    RuntimePlug, CreatePlug, EndPlug, SystemPlug
}

const val MaxPriority = 100000
const val MinPriority = 0
const val NormalPriority = (MaxPriority + MinPriority) / 2

interface BoardXPlugin {
    fun getPluginType(): BoardXPluginType
    fun getPluginPriority(): Int
    fun init(board: BoardX) {}
    fun onGet(x: Int, y: Int, board: BoardX) {}
    fun onSet(x: Int, y: Int, player: PlayerType, board: BoardX) {}
    fun onCreate(board: BoardX) {}
    fun onOver(winner: PlayerType, board: BoardX) {}
    fun beforeSetting(x: Int, y: Int, player: PlayerType, board: BoardX) {}
}
