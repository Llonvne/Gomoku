package plugins

import board.PlayerType
import boardx.BoardX
import boardx.BoardXPlugin
import boardx.BoardXPluginType
import boardx.NormalPriority
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import plugins.essentialX.DisplayBoard
import plugins.essentialX.DropAlert
import plugins.essentialX.EssentialXPlugin
import plugins.essentialX.event.*
import plugins.essentialX.observerPattern.Observable
import plugins.essentialX.observerPattern.Observer
import plugins.essentialX.path.next
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.pathString

class Essentials : BoardXPlugin {

    private var essentialsPlugins: MutableList<EssentialXPlugin> = mutableListOf(
        DisplayBoard(), DropAlert()
    )

    private val map: MutableMap<Path, Observable<Event>> = mutableMapOf()

    private var sender = this::sendEvent

    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.SystemPlug
    }

    override fun getPluginPriority(): Int {
        return NormalPriority
    }

    private fun buildTree(obj: JsonObject?, map: MutableMap<Path, Observable<Event>>, fatherPath: Path) {
        if (obj != null) {
            for (key in obj.keys) {
                val newPath = "${fatherPath.pathString}/$key"
                map[Path(newPath)] = Observable()
                val value: JsonObject? = obj[key]?.jsonObject
                if (value != null) {
                    buildTree(value, map, Path(newPath))
                }
            }
        }
    }

    override fun init(board: BoardX) {

        var pathMap: JsonObject? = plugins.essentialX.path.load()

        pathMap = next(pathMap!!, "PathStructure")

        buildTree(pathMap, map, Path(rootPath))


        essentialsPlugins.forEach {
            val observable = map[it.getPath()]
            if (observable == null) {
                println("${it.javaClass.simpleName} 插件的路径 ${it.getPath()} 并不存在")
            }
            observable?.addObserver(
                object : Observer<Event> {
                    override fun update(value: Event) {
                        it.onEvent(value)
                    }
                }
            )
        }

        map[Path(rootPath)] = Observable()

    }

    private fun sendEvent(event: Event) {
        if (map[Path(event.getPath())] == null) {
            println("${event.getPath()} 不存在，事件发送失败")
        } else {
            println("${event.getType()} 发送成功，路径为 ${event.getPath()}")
            map[Path(event.getPath())]?.notifyObservers(event)
        }
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