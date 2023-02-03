package plugins.essentialX

import board.Board
import board.PointType
import boardx.BoardX
import plugins.essentialX.annotations.ListenerPath
import plugins.essentialX.event.Event
import plugins.essentialX.event.SetEvent
import plugins.essentialX.event.SetEventPath
import plugins.essentialX.observerPattern.Observer
import java.nio.file.Path

@ListenerPath(SetEventPath)
class DisplayBoard : EssentialXPlugin() {

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