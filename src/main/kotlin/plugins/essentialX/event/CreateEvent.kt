package plugins.essentialX.event

import boardx.BoardX

class CreateEventArgs(
    val board: BoardX
)

class CreateEvent(args: CreateEventArgs, sender: (Event) -> Unit) : BaseEvent<CreateEventArgs>(
    sender, EventType.CreateEvent, args,
    CreateEventPath
)