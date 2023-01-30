package plugins.essentialX.io

import board.PlayerType
import boardx.BoardX
import plugins.essentialX.EssentialXPlugin
import plugins.essentialX.event.ConnectorPath
import plugins.essentialX.event.Event
import plugins.essentialX.event.EventType
import plugins.essentialX.observerPattern.Observer
import java.nio.file.Path

class CommandLineConnector : EssentialXPlugin(ConnectorPath) {

    private lateinit var inputConnector: InputConnector

    private val inputThread = Thread {
        println("开始监听命令行输入 ...")
        val x = readln().split(' ')
        inputConnector.set(x[0].toInt(), x[1].toInt(), PlayerType.White)
    }

    override fun init(
        addObserver: (Observer<Event>) -> Unit,
        observerPath: Path,
        sender: (Event) -> Unit,
        board: BoardX
    ) {

    }

    override fun onEvent(event: Event) {
        if (event.getType() == EventType.ConnectorEvent) {
            (event.getArgs() as InputServiceArgs).also {
                this.inputConnector = it.inputConnector
                println("CommandLineConnector 成功获取 InputConnector")
                inputThread.start()
            }
        }
    }
}