package plugins.essentialX.event

import board.PlayerType
import boardx.BoardX

data class OverEventArg(
    val winner: PlayerType,
    val board: BoardX
)

class OverEvent(args: OverEventArg, sender: (Event) -> Unit) :
    BaseEvent<OverEventArg>(sender, EventType.OverEvent, args, "") {
}