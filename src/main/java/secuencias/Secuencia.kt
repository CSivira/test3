package secuencias

interface Secuencia<T> {
    fun agregar (elem : T)
    fun remover () : T
    fun vacio () : Boolean
}