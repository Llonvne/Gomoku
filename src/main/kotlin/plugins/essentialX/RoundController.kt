package plugins.essentialX

import BoardX
import BoardXPlugin
import BoardXPluginType
import EssentialXPlugin
import NormalPriority
import PlayerType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import plugins.essentialX.event.Event
import plugins.essentialX.event.rootPath
import str

class RoundController : EssentialXPlugin(rootPath) {
    override fun init() {
    }

    override fun onEvent(event: Event) {
    }
}