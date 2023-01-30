package plugins.essentialX

import board.Board
import board.PointType
import boardx.BoardX
import boardx.NormalPriority
import plugins.essentialX.event.*
import plugins.essentialX.observerPattern.Observable
import plugins.essentialX.observerPattern.Observer
import java.nio.file.Path

class DisplayBoard : EssentialXPlugin(listOf(SetEventPath)) {

    private lateinit var board: BoardX

    override fun init(
        addObserver: (Observer<Event>) -> Unit,
        observerPath: Path,
        sender: (Event) -> Unit,
        board: BoardX
    ) {
        this.board = board
    }

    private fun display(board: Board<PointType>) {
        board.direct().forEach { pointTypes ->
            pointTypes.forEach { print(it.ordinal) }
            println()
        }
    }

    override fun onEvent(event: Event) {
        if (event is SetEvent) {
            display(board)
        }
    }
}