import plugins.Essentials
import plugins.PluginPriorityChecker
import kotlin.reflect.KClass

enum class BoardXPluginType {
    RuntimePlug, CreatePlug, EndPlug, SystemPlug
}

const val MaxPriority = 100000
const val MinPriority = 0
const val NormalPriority = (MaxPriority + MinPriority) / 2

//val defaultPlugins: List<KClass<out BoardXPlugin>> = listOf(PluginPriorityChecker::class, Essentials::class)
val plugins = listOf(
    PluginPriorityChecker::class.qualifiedName, Essentials::class.qualifiedName
)

abstract class BoardXPlugin {

    abstract fun getPluginType(): BoardXPluginType

    abstract fun getPluginPriority(): Int

    fun initByBoardX(board: BoardX) {
        println("[Plugin Loader] ${this.javaClass.simpleName} loading ...")
        init(board)
    }

    open fun init(board: BoardX) {}

    open fun onGet(x: Int, y: Int, board: BoardX) {}

    open fun onSet(x: Int, y: Int, player: PlayerType, board: BoardX) {
    }

    open fun onCreate(board: BoardX) {}

    open fun onOver(winner: PlayerType, board: BoardX) {
    }

    open fun beforeSetting(x: Int, y: Int, player: PlayerType, board: BoardX): Boolean {
        return true
    }
}
