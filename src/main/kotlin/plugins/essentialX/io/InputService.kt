package plugins.essentialX.io

import board.PlayerType
import board.PointType
import boardx.BoardX
import boardx.BoardX.Companion.toPointType
import boardx.NormalPriority
import plugins.essentialX.EssentialXPlugin
import plugins.essentialX.event.*
import plugins.essentialX.observerPattern.Observer
import java.nio.file.Path

data class InputServiceArgs(
    val inputConnector: InputConnector
)

class InputService : EssentialXPlugin() {

    private lateinit var sender: (Event) -> Unit
    private lateinit var boardX: BoardX

    override fun init(
        addObserver: (Observer<Event>) -> Unit, observerPath: Path, sender: (Event) -> Unit, board: BoardX
    ) {
        this.sender = sender
        this.boardX = board
        getInputConnector()
    }

    override fun onEvent(event: Event) {
    }

    private fun getInputConnector() {
        sender(
            object : Event {
                override fun toString(): String {
                    return "InputConnector 生成完毕"
                }

                override fun getType(): EventType {
                    return EventType.ConnectorEvent
                }

                override fun getArgs(): Any {
                    return InputServiceArgs(object : InputConnector {
                        override fun start() {
                            boardX.direct().map {
                                it.map { PointType.Empty }
                            }
                        }

                        override fun set(x: Int, y: Int, player: PlayerType) {
                            boardX[x, y] = toPointType(player)
                        }

                        override fun admitDefault(player: PlayerType) {
                            sender(OverEvent(OverEventArg(player)))
                        }
                    })
                }

                override fun getPath(): String {
                    return ConnectorPath
                }

                override fun getPriority(): Int {
                    return NormalPriority
                }
            }
        )
    }
}