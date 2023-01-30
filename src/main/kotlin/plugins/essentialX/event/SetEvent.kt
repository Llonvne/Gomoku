package plugins.essentialX.event

import board.PlayerType
import boardx.BoardX

class SetEventArgs(
    val x: Int,
    val y: Int,
    val playerType: PlayerType,
) {
    override fun toString(): String {
        return "SetEventArgs(x=$x, y=$y, playerType=$playerType)"
    }
}

open class SetEvent(args: SetEventArgs) :
    BaseEvent<SetEventArgs>(EventType.SetEvent, args, SetEventPath)

class BeforeSetEvent(args: SetEventArgs) : SetEvent(args) {
    override fun getType(): EventType {
        return EventType.BeforeSetEvent
    }

    override fun getPath(): String {
        return BeforeSetEventPath
    }
}