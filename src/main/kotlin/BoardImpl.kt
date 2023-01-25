enum class PointType {
    Black, White, Empty
}

enum class PlayerType {
    Black, White
}

class BoardImpl(
    val boardSize: Int
) : Board<PointType> {

    private val board = MutableList(boardSize) {
        return@MutableList MutableList(boardSize) { PointType.Empty }
    }

    private fun at(x: Int, y: Int): PointType {
        if ((x in (0..boardSize)) && (y in (0..boardSize))) {
            return board[x][y]
        }
        throw IllegalArgumentException("非法的坐标 x:$x,y:$y,合法的坐标因处于 [0,$boardSize - 1]")
    }

    private fun place(x: Int, y: Int, pointType: PointType) {
        this.board[x][y] = pointType
    }

    override operator fun get(x: Int, y: Int): PointType {
        return at(x, y)
    }

    override operator fun set(x: Int, y: Int, pointType: PointType) {
        return place(x, y, pointType)
    }

    override fun direct(): MutableList<MutableList<PointType>> {
        return board
    }
}
