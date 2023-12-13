package virtual

import kotlin.test.assertTrue

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val virtualTable = VirtualTable()


        virtualTable.define("A", listOf("f", "g"), null)
        print(virtualTable.existsCircularInheritance("A", mutableListOf()))

        //virtualTable.start()
    }
}