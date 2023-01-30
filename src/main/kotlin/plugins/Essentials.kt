package plugins

import board.PlayerType
import boardx.BoardX
import boardx.BoardXPlugin
import boardx.BoardXPluginType
import boardx.NormalPriority
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import plugins.essentialX.DisplayBoard
import plugins.essentialX.EssentialXPlugin
import plugins.essentialX.event.*
import plugins.essentialX.observerPattern.Observable
import plugins.essentialX.path.next
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

    fun buildTree(obj: JsonObject?, map: MutableMap<Path, Observable<Event>>, fatherPath: Path) {
        if (obj != null) {
            for (key in obj.keys) {
                val newPath = "${fatherPath.toUri()}/$key"
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


        essentialsPlugins.forEach {}

        map[Path(rootPath)] = Observable()

    }

    private fun sendEvent(event: Event) {
        if (map[Path(event.getPath())] == null) {
            println("${event.getPath()} 不存在，事件发送失败")
        } else {
            println("事件发送成功")
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