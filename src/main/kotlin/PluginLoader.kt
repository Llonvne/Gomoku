import kotlinx.serialization.json.*
import java.io.File
import java.lang.RuntimeException
import kotlin.reflect.full.primaryConstructor

private val json = Json {
    prettyPrint = true;
    encodeDefaults = true;
}

fun load(): MutableList<BoardXPlugin> {
    return try {
        val l =
            Json.parseToJsonElement(File("./config/plugins.json").readText()).jsonObject["BoardXPlugins"]!!.jsonArray.map {
                it.str()
            }

        l.map {

            val cls = try {
                Class.forName(it).kotlin
            } catch (e: Exception) {
                throw RuntimeException("./config/plugins.json 包含未知类: $it")
            }

            val superTypesString = cls.supertypes.map { kType -> kType.toString() }

            if (!superTypesString.contains("BoardXPlugin")) {
                throw RuntimeException("./config/plugins.json 包含非 BoardXPlugin 插件 : $it")
            }
            return@map cls.primaryConstructor?.call() as BoardXPlugin
        }.toMutableList()

    } catch (e: Exception) {
        throw e
    }
}

inline fun loadEssentialX(): MutableList<EssentialXPlugin> {
    return try {
        val l =
            Json.parseToJsonElement(File("./config/plugins.json").readText()).jsonObject["EssentialXPlugins"]!!.jsonArray.map { it.jsonObject }

        l.map {
            val clsName = it["PluginName"]?.jsonPrimitive?.content
            val cls = Class.forName(clsName).kotlin
            val superTypesString = cls.supertypes.map { kType -> kType.toString() }
            if (!superTypesString.contains(EssentialXPlugin::class.simpleName)) {
                throw RuntimeException("./config/plugins.json 包含非 ${EssentialXPlugin::class.simpleName} 插件 : $clsName")
            }
            cls.primaryConstructor?.call(
                it["Args"]?.jsonObject
            ) as EssentialXPlugin
        }.toMutableList()

    } catch (e: Exception) {
        throw e
    }
}

fun JsonObject.str(): String {
    return try {
        this.jsonPrimitive.content
    } catch (e: Exception) {
        throw e
    }
}

fun JsonElement.str(): String {
    return try {
        this.jsonPrimitive.content
    } catch (e: Exception) {
        throw e
    }
}


//    private val configObject = buildJsonObject {
//        putJsonArray("defaultPlugins") {
//            for (i in listOf("plugins.PluginPriorityChecker", "plugins.Essentials")) {
//                add(i)
//            }
//        }
//        put("123", "123")
//    }
//
//    fun save() {
//        val f = File("./config/plugins.json")
//        f.createNewFile();
//
//        f.writeText(json.encodeToString(configObject))
//    }

fun BoardXPlugin.simpleName(): String {
    return this::class.java.simpleName
}

fun BoardXPlugin.name(): String {
    return this::class.qualifiedName!!
}