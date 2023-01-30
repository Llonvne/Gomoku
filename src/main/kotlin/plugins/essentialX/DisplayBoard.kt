package plugins.essentialX

import board.Board
import board.PointType
import boardx.NormalPriority
import plugins.essentialX.event.*
import plugins.essentialX.observerPattern.Observable

class DisplayBoard : EssentialXPlugin(listOf(SetEventPath)) {

    private fun display(board: Board<PointType>) {
        board.direct().forEach { pointTypes ->
            pointTypes.forEach { print(it.ordinal) }
            println()
        }
    }

    override fun onEvent(event: Event) {
        val e = event
        (event.getArgs() as SetEventArgs).board.also {
            display(it)
        }
    }
}