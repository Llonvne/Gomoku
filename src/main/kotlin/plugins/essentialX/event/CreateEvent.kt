package plugins.essentialX.event

import boardx.BoardX

class CreateEventArgs(
    val board: BoardX

) {
    override fun toString(): String {
        return "CreateEventArgs()"
    }
}

class CreateEvent(args: CreateEventArgs, sender: (Event) -> Unit) : BaseEvent<CreateEventArgs>(
    sender, EventType.CreateEvent, args,
    CreateEventPath
)