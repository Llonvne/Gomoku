package plugins.essentialX

import board.Board
import board.PointType
import boardx.NormalPriority
import plugins.essentialX.event.*

class DisplayBoard : EssentialXPlugin(SetEventPath) {
    override fun init() {
    }

    private fun display(board: Board<PointType>) {
        board.direct().forEach { pointTypes ->
            pointTypes.forEach { print(it.ordinal) }
            println()
        }
    }

    override fun onEvent(event: Event) {
        val e = event
        event.recall(
            object : Event {
                override fun recall(event: Event) {
                    e.recall(event)
                }

                override fun getType(): EventType {
                    return EventType.CustomerEvent
                }

                override fun getArgs(): Any {
                    return "Hello World"
                }

                override fun getPath(): String {
                    return ListenerAllPath
                }

                override fun toString(): String {
                    return getArgs().toString()
                }

                override fun getPriority(): Int {
                    return NormalPriority
                }
            }
        )
        (event.getArgs() as SetEventArgs).board.also {
            display(it)
        }
    }
}