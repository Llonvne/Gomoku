package plugins.essentialX

import board.Board
import board.PointType
import plugins.essentialX.event.Event
import plugins.essentialX.event.SetEventArgs
import plugins.essentialX.event.SetEventPath

class DisplayBoard() : EssentialXPlugin(SetEventPath) {
    override fun init() {
    }

    private fun display(board: Board<PointType>) {
        board.direct().forEach { pointTypes ->
            pointTypes.forEach { print(it.ordinal) }
            println()
        }
    }

    override fun onEvent(event: Event) {
        (event.getArgs() as SetEventArgs).board.also { display(it) }
    }
}