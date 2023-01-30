package plugins

import board.PlayerType
import boardx.BoardX
import boardx.BoardXPlugin
import boardx.BoardXPluginType
import boardx.NormalPriority
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import plugins.essentialX.EssentialXPlugin
import plugins.essentialX.event.EventQueue
import plugins.essentialX.event.*
import plugins.essentialX.observerPattern.Observable
import plugins.essentialX.observerPattern.Observer
import plugins.essentialX.path.next
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.pathString

class Essentials : BoardXPlugin {

    private val essentialsPlugins: MutableList<EssentialXPlugin> = mutableListOf()

    private val map: MutableMap<Path, Observable<Event>> = mutableMapOf()

    private var sender: EventPriorityQueue = EventQueue(map)

    private fun sendEvent(event: Event) {
        sender.enQueueFun(event)
    }

    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.SystemPlug
    }

    override fun getPluginPriority(): Int {
        return NormalPriority
    }

    private fun buildPathTree(obj: JsonObject?, map: MutableMap<Path, Observable<Event>>, fatherPath: Path) {
        if (obj != null) {
            for (key in obj.keys) {
                val newPath = "${fatherPath.pathString}/$key"
                map[Path(newPath)] = Observable()
                val value: JsonObject? = obj[key]?.jsonObject
                if (value != null) {
                    buildPathTree(value, map, Path(newPath))
                }
            }
        }
    }

    override fun init(board: BoardX) {

        this.essentialsPlugins.addAll(loadEssentialX())

        var pathMap: JsonObject? = plugins.essentialX.path.load()

        pathMap = next(pathMap!!, "PathStructure")

        buildPathTree(pathMap, map, Path(rootPath))


        essentialsPlugins.forEach {
            val pluginPath = Path(PluginBasePath + it.javaClass.simpleName)
            val ob = Observable<Event>()
            map[pluginPath] = ob
            it.init(ob::addObserver, pluginPath)
            for (p in it.registerCustomerPath()) {
                map[Path(pluginPath.pathString + pathSpliterator + p)] = Observable()
            }
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

    override fun onGet(x: Int, y: Int, board: BoardX) {
        sendEvent(
            GetEvent(
                GetEventArgs(x, y, board), this::sendEvent
            )
        )
    }

    override fun onSet(x: Int, y: Int, player: PlayerType, board: BoardX) {
        sendEvent(
            SetEvent(
                SetEventArgs(x, y, player, board), this::sendEvent
            )
        )
    }

    override fun onCreate(board: BoardX) {
        sendEvent(
            CreateEvent(CreateEventArgs(board), this::sendEvent)
        )
    }

    override fun onOver(winner: PlayerType, board: BoardX) {
        sendEvent(
            OverEvent(
                OverEventArg(winner, board), this::sendEvent
            )
        )
    }

    override fun beforeSetting(x: Int, y: Int, player: PlayerType, board: BoardX) {
        sendEvent(
            BeforeSetEvent(
                SetEventArgs(x, y, player, board), this::sendEvent
            )
        )
    }
}