package plugins.essentialX.event

import boardx.BoardX

class GetEventArgs(
    val x: Int,
    val y: Int,
    val board: BoardX

) {
    override fun toString(): String {
        return "GetEventArgs(x=$x, y=$y)"
    }
}


class GetEvent(args: GetEventArgs, sender: (Event) -> Unit) :
    BaseEvent<GetEventArgs>(sender, EventType.GetEvent, args, GetEventPath)