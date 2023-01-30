package plugins

import board.PlayerType
import boardx.BoardX
import boardx.BoardXPlugin
import boardx.BoardXPluginType
import boardx.NormalPriority
import plugins.essentialX.DisplayBoard
import plugins.essentialX.EssentialXPlugin
import plugins.essentialX.event.*
import plugins.essentialX.observerPattern.Observable
import plugins.essentialX.observerPattern.Observer
import java.nio.file.Path
import kotlin.io.path.Path

class Essentials : BoardXPlugin {

    private var essentialsPlugins: MutableList<EssentialXPlugin> = mutableListOf(
        DisplayBoard()
    )

    private val map: MutableMap<Path, Observable<Event>> = mutableMapOf()

    private var sender = this::sendEvent

    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.SystemPlug
    }

    override fun getPluginPriority(): Int {
        return NormalPriority
    }

    override fun init(board: BoardX) {

        val pathMap = plugins.essentialX.path.load()

        essentialsPlugins.forEach {}

        map[Path(rootPath)] = Observable()

        map[Path(rootPath)]?.addObserver(
            object :Observer<Event>{
                override fun update(value: Event) {
                    println("Event ${value.getType()}")
                }
            }
        )
    }

    private fun sendEvent(event: Event) {
//        if (map[Path(event.getPath())] == null){
//            println("${event.getPath()} 不存在，事件发送失败")
//        }
//        else {
//            println("事件发送成功")
//            map[Path(event.getPath())]?.notifyObservers(event)
//        }
    }

    override fun onGet(x: Int, y: Int, board: BoardX) {
        sendEvent(
            GetEvent(
                GetEventArgs(x, y, board), sender
            )
        )
    }

    override fun onSet(x: Int, y: Int, player: PlayerType, board: BoardX) {
        sendEvent(
            SetEvent(
                SetEventArgs(x, y, player, board), sender
            )
        )
    }

    override fun onCreate(board: BoardX) {
        sendEvent(
            CreateEvent(CreateEventArgs(board), sender)
        )
    }

    override fun onOver(winner: PlayerType, board: BoardX) {
        sendEvent(
            OverEvent(
                OverEventArg(winner, board), sender
            )
        )
    }

    override fun beforeSetting(x: Int, y: Int, player: PlayerType, board: BoardX) {
        sendEvent(
            BeforeSetEvent(
                SetEventArgs(x, y, player, board), sender
            )
        )
    }
}