package plugins.essentialX.event

import boardx.BoardX
import board.PlayerType

data class OverEventArg(
    val winner: PlayerType,
    val board: BoardX
)

class OverEvent(args: OverEventArg, sender: (Event) -> Unit) :
    BaseEvent<OverEventArg>(sender, EventType.OverEvent, args, "") {
}