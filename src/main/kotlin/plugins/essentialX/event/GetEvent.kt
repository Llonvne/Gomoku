package plugins.essentialX.event

import boardx.BoardX

class GetEventArgs(
    val x: Int,
    val y: Int,
) {
    override fun toString(): String {
        return "GetEventArgs(x=$x, y=$y)"
    }
}


class GetEvent(args: GetEventArgs) :
    BaseEvent<GetEventArgs>(EventType.GetEvent, args, GetEventPath)