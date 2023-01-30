import board.PointType
import boardx.BoardX
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

fun main(args: Array<String>) {
    val b = BoardX(
        5,
        mutableListOf()
    )

    b[0, 0] = PointType.White
    b[0, 1] = PointType.White

}