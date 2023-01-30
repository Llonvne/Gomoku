package plugins.essentialX.event

import boardx.BoardX

class CreateEventArgs(

) {
    override fun toString(): String {
        return "CreateEventArgs()"
    }
}

class CreateEvent(args: CreateEventArgs) : BaseEvent<CreateEventArgs>(
    EventType.CreateEvent, args, CreateEventPath
)