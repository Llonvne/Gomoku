package plugins

import BoardX
import BoardXPlugin
import BoardXPluginType
import EssentialXPlugin
import NormalPriority
import load
import loadEssentialX
import plugins.essentialX.DisplayBoard
import plugins.essentialX.DropAlert
import plugins.essentialX.RoundController
import plugins.essentialX.SettingChecker

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
        essentialsPlugins = loadEssentialX()
    }
}