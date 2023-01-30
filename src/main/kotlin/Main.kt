import board.PointType
import boardx.BoardX

fun main() {
    val b = BoardX(
        5,
        mutableListOf()
    )

    b[0, 0] = PointType.White
    b[0, 1] = PointType.White
}