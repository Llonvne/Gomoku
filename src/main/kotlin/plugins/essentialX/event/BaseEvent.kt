package plugins.essentialX.event

import boardx.NormalPriority

abstract class BaseEvent<ArgType : Any>(
    private val type: EventType,
    private val args: ArgType, private val path: String
) :
    Event {

    override fun getType(): EventType {
        return type
    }

    override fun getArgs(): Any {
        return args
    }

    override fun getPath(): String {
        return path
    }

    override fun toString(): String {
        return "${this.javaClass.simpleName}(type=$type, args=$args, path='$path')"
    }

    override fun getPriority(): Int {
        return NormalPriority
    }
}