package plugins.essentialX.path

import plugins.essentialX.event.pathSpliterator
import plugins.essentialX.event.rootPath

interface Folder {
    fun getContext(): List<URL>

    fun createFolder(name: String): Folder
}

open class FolderImpl(url: URL) : Folder, URL by url {
    private val context: MutableList<URL> = mutableListOf()

    override fun getContext(): List<URL> {
        return context
    }

    override fun createFolder(name: String): Folder {
        val f = FolderImpl(URLImpl(getURLStr() + name + pathSpliterator))
        context.add(f)
        return f
    }
}

fun getRoot(): Folder {
    return object : FolderImpl(URLImpl(rootPath)){
        override fun getPrev(): URL {
            throw RuntimeException("不能对 root file 执行 getPrev")
        }
    }
}