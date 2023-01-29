package plugins.essentialX.event

abstract class BaseEvent<ArgType : Any>(
    private val sender: (Event) -> Unit, private val type: EventType,
    private val args: ArgType, private val path: String
) :
    Event {
    override fun recall(event: Event) {
        sender(event)
    }

    override fun getType(): EventType {
        return type
    }

    override fun getArgs(): Any {
        return args
    }

    override fun getPath(): String {
        return path
    }
}