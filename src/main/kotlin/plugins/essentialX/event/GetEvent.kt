package plugins.essentialX.event

import BoardX

class GetEventArgs(
    val x: Int,
    val y: Int,
    val board: BoardX
)


class GetEvent(args: GetEventArgs, sender: (Event) -> Unit) :
    BaseEvent<GetEventArgs>(sender, EventType.GetEvent, args, GetEventPath) {
}