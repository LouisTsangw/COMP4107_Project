interface Observer {
    fun update(successfulDodge: Boolean)
}

interface Subject {
    fun registerObserver(observer: Observer)
    fun notifyObservers(successfulDodge: Boolean)
}