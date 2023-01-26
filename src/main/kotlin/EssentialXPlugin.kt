import kotlinx.serialization.json.JsonObject
import plugins.Essentials
import kotlin.reflect.jvm.internal.impl.types.model.ArgumentList

abstract class EssentialXPlugin(args: JsonObject) : BoardXPlugin {
    lateinit var essentials: Essentials
    fun initByEssential(board: BoardX) {
        println("[Plugin Loader] ${this.javaClass.simpleName} loading ...")
        init(board)
    }
}