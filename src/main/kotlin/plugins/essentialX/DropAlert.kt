package plugins.essentialX

import board.PlayerType
import plugins.essentialX.event.Event
import plugins.essentialX.event.SetEvent
import plugins.essentialX.event.SetEventArgs
import plugins.essentialX.event.SetEventPath

class DropAlert : EssentialXPlugin(SetEventPath) {

    override fun init() {
    }

    override fun onEvent(event: Event) {
        if (event is SetEvent) {
            val arg = event.getArgs() as SetEventArgs
            alert(arg.x, arg.y, arg.playerType)
        }
    }

    private fun alert(x: Int, y: Int, player: PlayerType) {
        println("[Alert] The player ${player.name} drop on $x,$y")
    }
}