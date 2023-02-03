package plugins

import board.PlayerType
import boardx.BoardX
import boardx.BoardXPlugin
import boardx.BoardXPluginType
import boardx.NormalPriority
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import plugins.essentialX.EssentialXPlugin
import plugins.essentialX.annotations.ListenerPath
import plugins.essentialX.annotations.extractPathFromListenerPathAnnotation
import plugins.essentialX.event.*
import plugins.essentialX.observerPattern.Observable
import plugins.essentialX.path.next
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.pathString
import kotlin.reflect.KAnnotatedElement
import kotlin.reflect.full.findAnnotations

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

        m[Path(rootPath)] = Observable()

        this.essentialsPlugins.addAll(loadEssentialX())

        var pathMap: JsonObject? = plugins.essentialX.path.load()

        pathMap = next(pathMap!!, "PathStructure")

        buildPathTree(pathMap, m, Path(rootPath))


        essentialsPlugins.forEach { plugin ->

            // 注册 PluginPath
            val pluginPath = Path(PluginBasePath + plugin.javaClass.simpleName)
            val ob = Observable<Event>()
            m[pluginPath] = ob

            // 初始化插件
            plugin.init(ob::addObserver, pluginPath, this::sendEvent, board)

            // 注册插件自定义路径
            for (p in plugin.registerCustomerPath()) {
                m[Path(pluginPath.pathString + pathSpliterator + p)] = Observable()
            }

            val cls = plugin::class
            val target = cls as KAnnotatedElement
            target.findAnnotations<ListenerPath>().map { listenerPath ->
                extractPathFromListenerPathAnnotation(listenerPath)
            }.forEach { paths ->
                paths.forEach { path ->
                    addListenerToPath(plugin, path, m)
                }
            }
        }
    }

    private fun addListenerToPath(plugin: EssentialXPlugin, path: String, m: MutableMap<Path, Observable<Event>>) {
        val observable = m[Path(path)]
        if (observable == null) {
            println("${plugin.javaClass.simpleName} 插件的路径 $path 并不存在")
            return
        }
        println("Plugin ${plugin::class.simpleName} 监听路径 $path")
        observable.addObserver { value -> plugin.onEvent(value) }
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