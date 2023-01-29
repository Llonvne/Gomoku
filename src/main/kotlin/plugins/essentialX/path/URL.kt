package plugins.essentialX.path

import plugins.essentialX.EssentialXPlugin
import plugins.essentialX.event.pathSpliterator
import plugins.essentialX.event.rootPath
import plugins.essentialX.observerPattern.Observable
import plugins.essentialX.observerPattern.Transfer

interface URL {
    fun getURLStr(): String

    fun isFolder(): Boolean

    fun isFile(): Boolean

    fun isRoot(): Boolean

    fun getPrev(): URL

    fun levels(): Int

    fun getLastName(): String

    fun getPrefixes(): List<String>
}

class URLImpl(private val str: String) : URL {


    override fun getPrefixes(): List<String> {
        return prefixes
    }

    private val prefixes = str.split(pathSpliterator).filter { it != "" }

    override fun getPrev(): URL {
        return URLImpl(
            pathSpliterator + prefixes.subList(0, prefixes.size - 1).joinToString(pathSpliterator) + pathSpliterator
        )
    }

    override fun levels(): Int {
        return prefixes.size
    }

    override fun getURLStr(): String {
        return str.trim()
    }

    override fun isFolder(): Boolean {
        return getURLStr().last().toString() == pathSpliterator
    }

    override fun isFile(): Boolean {
        return !isFolder()
    }

    override fun isRoot(): Boolean {
        return getURLStr() == rootPath
    }

    override fun getLastName(): String {
        return prefixes.last()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as URLImpl

        if (str != other.str) return false

        return true
    }

    override fun hashCode(): Int {
        return str.hashCode()
    }
}