package plugins

import board.PlayerType
import boardx.BoardX
import boardx.BoardXPlugin
import boardx.BoardXPluginType
import boardx.NormalPriority
import plugins.essentialX.DisplayBoard
import plugins.essentialX.EssentialXPlugin
import plugins.essentialX.event.*
import plugins.essentialX.observerPattern.Observable
import plugins.essentialX.path.getRoot
import plugins.essentialX.path.parser

class Essentials : BoardXPlugin {

    private var essentialsPlugins: MutableList<EssentialXPlugin> = mutableListOf(
        DisplayBoard()
    )

    private var observable: Observable<Event> = Observable()

    private var sender = observable::notifyObservers

    private val rootFolder = getRoot()

    override fun getPluginType(): BoardXPluginType {
        return BoardXPluginType.SystemPlug
    }

    override fun getPluginPriority(): Int {
        return NormalPriority
    }

    override fun init(board: BoardX) {
        essentialsPlugins.forEach { it.init() }

        essentialsPlugins.forEach {
            parser(it.getListeningUrl(), rootFolder)
        }
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