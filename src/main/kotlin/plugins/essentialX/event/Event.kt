package plugins.essentialX.event


interface Event {
    fun recall(event: Event)

    fun getType(): EventType

    fun getArgs(): Any

    fun getPath(): String

    fun getPriority(): Int
}

enum class EventType {
    GetEvent, SetEvent, BeforeSetEvent, CreateEvent, OverEvent, CustomerEvent
}

const val rootPath = "/"
const val pathSpliterator = "/"

const val InGameEventPath = rootPath + "Game" + pathSpliterator
const val PluginBasePath = rootPath + "Plugin" + pathSpliterator
const val ListenerPath = rootPath + "Listener" + pathSpliterator
const val ListenerAllPath = ListenerPath + "All" + pathSpliterator
const val ListenerSuccessPath = ListenerPath + "Success" + pathSpliterator
const val ListenerFailurePath = ListenerPath + "Failure" + pathSpliterator

const val GetEventPath = InGameEventPath + "GetEvent"
const val SetEventPath = InGameEventPath + "SetEvent"
const val BeforeSetEventPath = InGameEventPath + "BeforeSetEvent"
const val CreateEventPath = InGameEventPath + "CreateEvent"
const val OverEventPath = InGameEventPath + "OverEvent"
