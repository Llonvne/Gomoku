package boardx

import board.Board
import board.BoardImpl
import board.PlayerType
import board.PointType
import board.PointType.*
import plugins.load

class BoardX(
    boardSize: Int
) : Board<PointType> {

    var pluginList: MutableList<BoardXPlugin>
    private val board: Board<PointType>

    init {
        board = BoardImpl(boardSize)
        pluginList = load()
        pluginList.forEach { it.init(this) }
    }

    override fun get(x: Int, y: Int): PointType {
        pluginList.forEach { it.onGet(x, y, this) }
        return board[x, y]
    }

    override fun set(x: Int, y: Int, pointType: PointType) {
        pluginList.forEach { it.beforeSetting(x, y, toPlayerType(pointType), this) }
        board[x, y] = pointType
        pluginList.forEach { it.onSet(x, y, toPlayerType(pointType), this) }
    }

    override fun direct(): MutableList<MutableList<PointType>> {
        return board.direct()
    }

    private fun toPlayerType(pointType: PointType): PlayerType {
        return when (pointType) {
            Black -> PlayerType.Black
            White -> PlayerType.White
            Empty -> throw IllegalArgumentException("无法将 board.PointType.Empty 转换为 board.PlayerType")
        }
    }
}