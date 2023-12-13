package grafos

data class Grafo<T>(
        private val grafo : MutableMap<T, MutableList<T>> = mutableMapOf()
) {
    fun agregarNodo (nodo : T) {
        if (grafo[nodo] != null) return
        grafo[nodo] = mutableListOf()
    }

    fun agregarLado (izquierda : T, derecha : T) {
        if (!grafo.containsKey(izquierda)) return
        grafo[izquierda]?.add(derecha)
    }

    fun obtenerNodosAdjacentes(nodo : T) : MutableList<T> {
        return grafo[nodo] ?: mutableListOf()
    }
}
