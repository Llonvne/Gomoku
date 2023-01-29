package plugins.essentialX

import board.Board
import board.PointType
import plugins.essentialX.event.Event

class DisplayBoard() : EssentialXPlugin("") {
    override fun init() {
    }

    private fun display(board: Board<PointType>) {
        board.direct().forEach { pointTypes ->
            pointTypes.forEach { print(it.ordinal) }
            println()
        }
    }

    override fun onEvent(event: Event) {
    }
}