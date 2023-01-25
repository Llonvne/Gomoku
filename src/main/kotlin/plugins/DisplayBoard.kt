package plugins

import Board
import BoardX
import BoardXPlugin
import BoardXPluginType
import NormalPriority
import PlayerType
import PointType

class DisplayBoard : BoardXPlugin() {
    override fun getPluginPriority(): Int {
        return NormalPriority
    }

    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.RuntimePlug
    }

    override fun onCreate(board: BoardX) {
        display(board)
    }

    override fun onSet(x: Int, y: Int, player: PlayerType, board: BoardX) {
        display(board)
    }

    private fun display(board: Board<PointType>) {
        board.direct().forEach { pointTypes ->
            pointTypes.forEach { print(it.ordinal) }
            println()
        }
    }
}