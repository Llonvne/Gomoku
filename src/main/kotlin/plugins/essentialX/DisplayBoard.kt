package plugins.essentialX

import Board
import EssentialXPlugin
import EssentialXPluginType
import PointType
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