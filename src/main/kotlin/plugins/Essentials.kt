package plugins

import board.PlayerType
import boardx.BoardX
import boardx.BoardXPlugin
import boardx.BoardXPluginType
import boardx.NormalPriority
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import plugins.essentialX.EssentialXPlugin
import plugins.essentialX.event.EventPriorityQueueImpl
import plugins.essentialX.event.*
import plugins.essentialX.observerPattern.Observable
import plugins.essentialX.observerPattern.Observer
import plugins.essentialX.path.next
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.pathString

class Essentials : BoardXPlugin {

    private val essentialsPlugins: MutableList<EssentialXPlugin> = mutableListOf()

    private val m: MutableMap<Path, Observable<Event>> = mutableMapOf()

    private var sender: EventPriorityQueue = EventPriorityQueueImpl(m)

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

        buildPathTree(pathMap, m, Path(rootPath))


        essentialsPlugins.forEach {
            val pluginPath = Path(PluginBasePath + it.javaClass.simpleName)
            val ob = Observable<Event>()
            m[pluginPath] = ob
            it.init(ob::addObserver, pluginPath, this::sendEvent, board)
            for (p in it.registerCustomerPath()) {
                m[Path(pluginPath.pathString + pathSpliterator + p)] = Observable()
            }

            for (path: String in it.getPath()) {
                val observable = m[Path(path)]
                if (observable == null) {
                    println("${it.javaClass.simpleName} 插件的路径 ${path} 并不存在")
                }
                observable?.addObserver(
                    object : Observer<Event> {
                        override fun update(value: Event) {
                            it.onEvent(value)
                        }
                    }
                )
            }
        }

        m[Path(rootPath)] = Observable()
    }

    override fun onGet(x: Int, y: Int, board: BoardX) {
        sendEvent(
            GetEvent(
                GetEventArgs(x, y)
            )
        )
    }

    override fun onSet(x: Int, y: Int, player: PlayerType, board: BoardX) {
        sendEvent(
            SetEvent(
                SetEventArgs(x, y, player)
            )
        )
    }

    override fun onCreate(board: BoardX) {
        sendEvent(
            CreateEvent(CreateEventArgs())
        )
    }

    override fun onOver(winner: PlayerType, board: BoardX) {
        sendEvent(
            OverEvent(
                OverEventArg(winner)
            )
        )
    }

    override fun beforeSetting(x: Int, y: Int, player: PlayerType, board: BoardX) {
        sendEvent(
            BeforeSetEvent(
                SetEventArgs(x, y, player)
            )
        )
    }
}