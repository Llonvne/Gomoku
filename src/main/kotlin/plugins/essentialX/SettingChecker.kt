package plugins.essentialX

import BoardX
import BoardXPlugin
import BoardXPluginType
import EssentialXPlugin
import NormalPriority
import PlayerType
import PointType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.modules.EmptySerializersModule
import plugins.EmptyPlugin
import plugins.event.BeforeSetEventPath
import plugins.event.Event
import java.lang.Exception

class SettingChecker : EssentialXPlugin(BeforeSetEventPath) {

    override fun init() {
        TODO("Not yet implemented")
    }

    override fun onEvent(event: Event) {
        TODO("Not yet implemented")
    }
}