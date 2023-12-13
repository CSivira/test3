package secuencias

class Pila<T> : Secuencia<T> {
    private var pila : MutableList<T?> = MutableList(0) { null }

    override fun agregar(elem: T) { pila.add(elem) }

    override fun remover(): T {
        if (pila.isEmpty()) throw IllegalStateException("La pila está vacía")
        return pila.removeAt(pila.size - 1)!!
    }

    override fun vacio(): Boolean { return pila.size == 0 }

    override fun toString(): String { return "Pila ->[ ${pila.joinToString(", ")} ]" }
}