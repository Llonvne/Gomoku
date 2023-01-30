package plugins.essentialX.io

import board.PlayerType
import boardx.BoardX
import plugins.essentialX.EssentialXPlugin
import plugins.essentialX.event.AllInGameEventList
import plugins.essentialX.event.Event
import plugins.essentialX.observerPattern.Observer
import java.nio.file.Path

class InputService() : EssentialXPlugin(AllInGameEventList) {

    private lateinit var sender: (Event) -> Unit;

    override fun init(
        addObserver: (Observer<Event>) -> Unit,
        observerPath: Path,
        sender: (Event) -> Unit,
        board: BoardX
    ) {
        this.sender = sender
    }

    override fun onEvent(event: Event) {
    }

    fun getInputConnector(): InputConnector {
        return object : InputConnector{
            override fun start() {
//                sender(
//                    CreateEvent(
//                        CreateEventArgs()
//                    )
//                )
            }

            override fun set(x: Int, y: Int, player: PlayerType) {
                TODO("Not yet implemented")
            }

            override fun admitDefault(player: PlayerType) {
                TODO("Not yet implemented")
            }
        }
    }
}