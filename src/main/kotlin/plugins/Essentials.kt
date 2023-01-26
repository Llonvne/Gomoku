package plugins

import BoardX
import BoardXPlugin
import BoardXPluginType
import EssentialXPlugin
import NormalPriority
import PlayerType
import loadEssentialX
import qualifiedName
import simpleName

class Essentials : BoardXPlugin {

    private var essentialsPlugins: MutableList<EssentialXPlugin> = mutableListOf()

    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.SystemPlug
    }

    override fun getPluginPriority(): Int {
        return NormalPriority
    }

    override fun init(board: BoardX) {
        println("EssentialX plugins is hot loading some plugins please wait ...")
        essentialsPlugins = this.loadEssentialX()

        essentialsPlugins.forEach { it.initByEssential(board) }

        println("EssentialX completed hot-loading part of the plugin")
    }

    fun getEssentialXPlugin(clsName: String): EssentialXPlugin? {
        return essentialsPlugins.find {
            (it.simpleName() == clsName) || (it.qualifiedName() == clsName)
        }
    }

    fun essentialXPluginNameList(): List<String> {
        return essentialsPlugins.map { it.simpleName() }
    }

    override fun onGet(x: Int, y: Int, board: BoardX) {
        essentialsPlugins.forEach { it.onGet(x, y, board) }
    }

    override fun onSet(x: Int, y: Int, player: PlayerType, board: BoardX) {
        essentialsPlugins.forEach { it.onSet(x, y, player, board) }
    }

    override fun onCreate(board: BoardX) {
        essentialsPlugins.forEach { it.onCreate(board) }
    }

    override fun onOver(winner: PlayerType, board: BoardX) {
        essentialsPlugins.forEach { it.onOver(winner, board) }
    }

    override fun beforeSetting(x: Int, y: Int, player: PlayerType, board: BoardX): Boolean {
        return essentialsPlugins.all { it.beforeSetting(x, y, player, board) }
    }
}