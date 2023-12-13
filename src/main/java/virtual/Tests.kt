package virtual

import org.testng.annotations.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Tests {

    @Test
    fun `test existing class`() {
        val virtualTable = VirtualTable(mutableMapOf("A" to listOf("f", "g")), mutableMapOf())
        assertTrue { virtualTable.existsType("A") }
    }

    @Test
    fun `test non-existing class`() {
        val virtualTable = VirtualTable(mutableMapOf(), mutableMapOf())
        assertFalse { virtualTable.existsType("A") }
    }

    @Test
    fun `test valid method names`() {
        val virtualTable = VirtualTable(mutableMapOf(), mutableMapOf())
        assertTrue { virtualTable.validMethodNames(mutableListOf("a", "b", "c")) }
    }

    @Test
    fun `test invalid method names`() {
        val virtualTable = VirtualTable(mutableMapOf(), mutableMapOf())
        assertFalse { virtualTable.validMethodNames(mutableListOf("a", "b", "a")) }
    }

    @Test
    fun `test exists circular dependencies`() {
        val virtualTable = VirtualTable(mutableMapOf("A" to listOf("f")), mutableMapOf("A" to "A"))
        assertTrue { virtualTable.existsCircularInheritance("A", mutableListOf()) }
    }

    @Test
    fun `test not exists circular dependencies`() {
        val virtualTable = VirtualTable(mutableMapOf("A" to listOf("f")), mutableMapOf("A" to null))
        assertFalse { virtualTable.existsCircularInheritance("A", mutableListOf()) }
    }

    @Test
    fun `test define`() {
        val virtualTable = VirtualTable(mutableMapOf(), mutableMapOf())
        virtualTable.define("A",  listOf("f", "g"), null)
        assertTrue(virtualTable.existsType("A"))
    }

    @Test
    fun `test define super not exists`() {
        val virtualTable = VirtualTable(mutableMapOf(), mutableMapOf())
        virtualTable.define("A",  listOf("f", "g"), "X")
        assertFalse(virtualTable.existsType("A"))
    }

    @Test
    fun `test define super exists`() {
        val virtualTable = VirtualTable(mutableMapOf("X" to mutableListOf()), mutableMapOf("X" to null))
        virtualTable.define("A",  listOf("f", "g"), "X")
        assertTrue(virtualTable.existsType("A"))
    }
}