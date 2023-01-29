package plugins.essentialX.observerPattern

interface Observer<E> {
    fun getName(): String
    fun update(value: E)
}

open class Observable<E>(val name: String) {
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

class Transfer<E>(name: String) : Observable<E>(name) {
    private val observer = object : Observer<E> {
        override fun getName(): String {
            return name
        }

        override fun update(value: E) {
            notifyObservers(value)
        }
    }

    fun getObserver(): Observer<E> {
        return observer
    }
}