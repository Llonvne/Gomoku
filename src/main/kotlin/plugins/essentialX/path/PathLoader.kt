package plugins.essentialX.path

import java.io.File
import kotlinx.serialization.json.*
import kotlin.io.path.Path
import kotlin.io.path.name

const val pathJson = "./config/path.json"

fun load(): JsonObject {
    val w = File(pathJson).readText()
    return Json.parseToJsonElement(w).jsonObject
}

fun next(obj: JsonObject, key: String): JsonObject {
    return obj.jsonObject[key]?.jsonObject!!
}
