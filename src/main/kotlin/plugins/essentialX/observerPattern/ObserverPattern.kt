package plugins.essentialX.observerPattern

fun interface Observer<E> {
    fun update(value: E)
}

open class Observable<E> {
    private val observers: MutableList<Observer<E>> = mutableListOf()

    fun notifyObservers(value: E) {
        observers.forEach { it.update(value) }
    }

    fun addObserver(observer: Observer<E>) {
        this.observers.add(observer)
    }

    fun deleteObserver(observer: Observer<E>) {
        this.observers.remove(observer)
    }

    fun clearObservers() {
        this.observers.clear()
    }
}
