package plugins.essentialX.event

import board.PlayerType
import boardx.BoardX

data class OverEventArg(
    val winner: PlayerType,
    val board: BoardX

) {
    override fun toString(): String {
        return "OverEventArg(winner=$winner)"
    }
}

class OverEvent(args: OverEventArg, sender: (Event) -> Unit) :
    BaseEvent<OverEventArg>(sender, EventType.OverEvent, args, "") {
}