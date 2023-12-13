package grafos

import secuencias.Cola
import secuencias.Pila
import secuencias.Secuencia

sealed class Busqueda<T> (
        private val grafo : Grafo<T>,
        private val secuencia : Secuencia<T>
) {
    fun buscar (d : T, h : T) : Int {
        val visitados : MutableList<T> = mutableListOf()
        secuencia.agregar(d)

        while(!secuencia.vacio()) {
            val v : T = secuencia.remover()
            visitados.add(v)

            if (v == h) break

            for (i in grafo.obtenerNodosAdjacentes(v)) {
                if (!visitados.contains(i)) secuencia.agregar(i)
            }
        }

        return if (visitados.contains(h)) visitados.size else -1
    }
}

class DFS<T> (grafo: Grafo<T>) : Busqueda<T>(grafo, Pila())
class BFS<T> (grafo: Grafo<T>) : Busqueda<T>(grafo, Cola())