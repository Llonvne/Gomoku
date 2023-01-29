package plugins.essentialX

import BoardX
import BoardXPluginType
import EssentialXPlugin
import NormalPriority
import PlayerType
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

    fun alert(x: Int, y: Int, player: PlayerType) {
        println("[Alert] The player ${player.name} drop on $x,$y")
    }
}