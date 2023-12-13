package concurrencia

import kotlinx.coroutines.*
import kotlin.random.Random

class Vector (private val cap : Int) {
    val v1 : IntArray = IntArray(cap) { Random.nextInt(0, 1000000) }
    val v2 : IntArray = IntArray(cap) { Random.nextInt(0, 1000000) }

    suspend fun punto() : Int = coroutineScope {
        val promesas : List<Deferred<Int>> = v1.indices.map { i -> async { v1[i] * v2[i] } }
        var sum : Int = 0
        promesas.forEach{ sum += it.await() }
        sum
    }

    fun puntoNoConcur() : Int {
        var sum : Int = 0
        for (i in v1.indices) {
            val mul : Int = v1[i] * v2[i]
            sum += mul
        }
        return sum
    }
}