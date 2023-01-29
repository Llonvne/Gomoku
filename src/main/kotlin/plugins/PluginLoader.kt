package plugins

import boardx.BoardXPlugin
import kotlinx.serialization.json.*
import plugins.essentialX.EssentialXPlugin
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

const val PluginConfigPath = "./config/plugins.json"

fun load(): MutableList<BoardXPlugin> {
    return try {
        Json.parseToJsonElement(readFileToString(PluginConfigPath))
            .jsonObject["BoardXPlugins"]!!.jsonArray.map { it.str() }.map { clsName ->
            val cls = getKClassFromName(clsName)
            if (!getSuperTypesName(cls).contains("boardx.BoardXPlugin")) {
                throw RuntimeException("./config/plugins.json 包含非 boardx.BoardXPlugin 插件 : $clsName")
            }
            return@map cls.primaryConstructor?.call() as BoardXPlugin
        }.toMutableList()
    } catch (e: Exception) {
        throw e
    }
}

fun Essentials.loadEssentialX(): MutableList<EssentialXPlugin> {
    return try {

        Json.parseToJsonElement(readFileToString(PluginConfigPath))
            .jsonObject["EssentialXPlugins"]!!.jsonArray.map { it.jsonObject }
            .map {
                val clsName = it["PluginName"]?.str()!!
                val cls = getKClassFromName(clsName)
                val superTypesString = getSuperTypesName(cls)
                if (!superTypesString.contains(EssentialXPlugin::class.simpleName)) {
                    throw RuntimeException("./config/plugins.json 包含非 ${EssentialXPlugin::class.simpleName} 插件 : $clsName")
                }
                val obj = cls.primaryConstructor?.call(
                    it["Args"]?.jsonObject
                ) as EssentialXPlugin
                obj.essentials = this
                obj
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

fun BoardXPlugin.simpleName(): String {
    return this::class.java.simpleName
}

fun BoardXPlugin.qualifiedName(): String {
    return this::class.qualifiedName!!
}

fun getKClassFromName(name: String): KClass<out Any> {
    return try {
        Class.forName(name).kotlin
    } catch (e: Exception) {
        throw RuntimeException("./config/plugins.json 包含未知类: $name")
    }
}

fun readFileToString(path: String): String {
    return File(path).readText()
}

fun getSuperTypesName(cls: KClass<out Any>): Collection<String> {
    return cls.supertypes.map { it.toString() }
}