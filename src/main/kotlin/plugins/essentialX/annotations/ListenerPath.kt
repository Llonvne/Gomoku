package plugins.essentialX.annotations

@Target(AnnotationTarget.CLASS)
annotation class ListenerPath(
    val value: String = "",
    val list: Array<String> = []
)

fun extractPathFromListenerPathAnnotation(annotation: ListenerPath): List<String> {
    val paths = mutableListOf<String>()
    if (annotation.value != "") {
        paths.add(annotation.value)
    }
    if (annotation.list.isNotEmpty()) {
        paths.addAll(annotation.list)
    }
    return paths
}
