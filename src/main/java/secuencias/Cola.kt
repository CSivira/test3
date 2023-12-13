package secuencias

class Cola<T> : Secuencia<T> {
    private var cola : MutableList<T?> = MutableList(0) { null }

    override fun agregar(elem: T) { cola.add(elem) }

    override fun remover(): T {
        if (cola.isEmpty()) throw IllegalStateException("La pila está vacía")
        return cola.removeAt(0)!!
    }

    override fun vacio(): Boolean { return cola.size == 0 }

    override fun toString(): String { return "Cola ->[ ${cola.joinToString(", ")} ]" }
}