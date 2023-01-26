package plugins.essentialX

import BoardX
import BoardXPlugin
import BoardXPluginType
import EssentialXPlugin
import NormalPriority
import PlayerType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import plugins.event.Event
import plugins.event.rootPath
import str

class RoundController : EssentialXPlugin(rootPath) {
    override fun init() {
    }

    override fun onEvent(event: Event) {
    }
}