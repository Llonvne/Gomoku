package plugins.essentialX.path

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import java.io.File

const val pathJson = "./config/path.json"

fun load(): JsonObject {
    val w = File(pathJson).readText()
    return Json.parseToJsonElement(w).jsonObject
}

fun next(obj: JsonObject, key: String): JsonObject? {
    return obj.jsonObject[key]?.jsonObject
}
