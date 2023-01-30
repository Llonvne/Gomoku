package plugins.essentialX.event

import board.PlayerType
import boardx.BoardX

data class OverEventArg(
    val winner: PlayerType,
) {
    override fun toString(): String {
        return "OverEventArg(winner=$winner)"
    }
}

class OverEvent(args: OverEventArg) :
    BaseEvent<OverEventArg>(EventType.OverEvent, args, OverEventPath)