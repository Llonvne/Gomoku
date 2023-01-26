package plugins.event


interface Event {
    fun recall(event: Event)

    fun getType(): EventType

    fun getArgs(): Any

    fun getPath(): String
}

object EmptyArg {

}

enum class EventType {
    GetEvent, SetEvent, BeforeSetEvent, CreateEvent, OverEvent
}

const val rootPath = "/"
const val pathSpliterator = "/"

const val InGameEventPath = rootPath + "game" + pathSpliterator
const val GetEventPath = InGameEventPath + "GetEvent"
const val SetEventPath = InGameEventPath + "SetEvent"
const val BeforeSetEventPath = InGameEventPath + "BeforeSetEvent"
const val CreateEventPath = InGameEventPath + "CreateEvent"
const val OverEventPath = InGameEventPath + "OverEvent"