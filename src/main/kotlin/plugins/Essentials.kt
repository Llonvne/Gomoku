package plugins

import BoardX
import BoardXPlugin
import BoardXPluginType
import EssentialXPlugin
import NormalPriority
import PlayerType
import loadEssentialX
import plugins.essentialX.event.*
import plugins.essentialX.observerPattern.Observable
import plugins.event.*


class Essentials : BoardXPlugin {

    private var essentialsPlugins: MutableList<EssentialXPlugin> = mutableListOf()

    private var observable: Observable<Event> = Observable()

    private var sender = observable::notifyObservers

    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.SystemPlug
    }

    override fun getPluginPriority(): Int {
        return NormalPriority
    }

    override fun init(board: BoardX) {
        println("EssentialX plugins is hot loading some plugins please wait ...")
//        essentialsPlugins = this.loadEssentialX()

        essentialsPlugins.forEach { it.init() }
        println("EssentialX completed hot-loading part of the plugin")
    }

    private fun sendEvent(event: Event) {
        println("[Event] ${event.getType().name}")
        sender(event)
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