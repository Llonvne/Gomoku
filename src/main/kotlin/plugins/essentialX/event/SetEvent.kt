package plugins.essentialX.event

import board.PlayerType
import boardx.BoardX

class SetEventArgs(
    val x: Int,
    val y: Int,
    val playerType: PlayerType,
    val board: BoardX


) {
    override fun toString(): String {
        return "SetEventArgs(x=$x, y=$y, playerType=$playerType)"
    }
}

open class SetEvent(args: SetEventArgs, sender: (Event) -> Unit) :
    BaseEvent<SetEventArgs>(sender, EventType.SetEvent, args, SetEventPath) {
}

class BeforeSetEvent(args: SetEventArgs, sender: (Event) -> Unit) : SetEvent(args, sender) {
    override fun getType(): EventType {
        return EventType.BeforeSetEvent
    }

    override fun getPath(): String {
        return BeforeSetEventPath
    }
}