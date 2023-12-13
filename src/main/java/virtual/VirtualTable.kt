package virtual

class VirtualTable (
        private val types : MutableMap<String, List<String>?> = mutableMapOf(),
        private val inheritances : MutableMap<String, String?> = mutableMapOf()
) {
    private enum class LOGS {
        EXISTING_CLASS { override fun log (type: String) { println("La clase $type ya existe") } },
        NOT_EXISTING_CLASS { override fun log (type: String) { println("La clase $type no existe") } },
        NOT_EXISTING_SUPER_CLASS { override fun log (type: String) { println("La clase super $type no existe") } },
        INVALID_METHOD_NAMES { override fun log (type: String) { println("Los métodos del $type no son válidos") } },
        CIRCULAR_DEPENDENCIES { override fun log (type: String) {
            println("La clase $type introduce un ciclo en la jerarquía de herencia") }
        },
        UNKNOWN_COMMAND { override fun log (type: String) { println("Instruccion desconocida") } };

        abstract fun log(type: String)
    }

    fun existsType (type: String) : Boolean { return types.containsKey(type) || inheritances.containsKey(type) }
    fun validMethodNames (methods: List<String>) : Boolean { return methods.size == methods.toSet().size }

    // Is this possible? I think not. Presumably always false
    fun existsCircularInheritance (type: String, visited: MutableList<String>) : Boolean {
        if (inheritances.containsKey(type) && inheritances[type] == null) return false
        if (visited.contains(type)) return true
        visited.add(type)
        return existsCircularInheritance(inheritances[type]!!, visited)
    }

    fun define(type : String, methods : List<String>, sup : String?) {
        if (existsType(type)) { LOGS.EXISTING_CLASS.log(type); return }
        if (sup != null && !existsType(sup)) { LOGS.NOT_EXISTING_SUPER_CLASS.log(sup); return }
        if (!validMethodNames(methods)) { LOGS.INVALID_METHOD_NAMES.log(type); return }
        if (type == sup) { LOGS.CIRCULAR_DEPENDENCIES.log(type); return }
        if (sup != null && existsCircularInheritance(sup, mutableListOf())) {
            LOGS.CIRCULAR_DEPENDENCIES.log(type)
            return
        }
        
        types[type] = methods
        inheritances[type] = sup
    }

    fun describe(type : String?, visited : MutableList<String>) {
        if (type == null) return
        if (!existsType(type)) { LOGS.NOT_EXISTING_CLASS.log(type); return }

        for (method in types[type]!!) {
            if (visited.contains(method)) continue

            visited.add(method)
            println("$method -> $type :: $method")
        }
        describe(inheritances[type], visited)
    }

    fun start() {
        while (true) {
            val input = readln().split(" ")
            when (input[0]) {
                "CLASS" -> {
                    if (input.size < 3) { LOGS.UNKNOWN_COMMAND.log("") ; continue }
                    val type = input[1]

                    var sup : String? = null
                    var methodsIndex : Int
                    if (":" in input) {
                        if (input.size < 5) { LOGS.UNKNOWN_COMMAND.log("") ; continue }
                        sup = input[3]
                        methodsIndex = 4
                    } else {
                        methodsIndex = 2
                    }

                    val methods : MutableList<String> = mutableListOf()
                    for (i in methodsIndex until input.size) { methods.add(input[i]) }

                    if (methods.isEmpty()) { LOGS.UNKNOWN_COMMAND.log("") ; continue }
                    define(type, methods, sup)
                }
                "DESCRIBIR" -> {
                    if (input.size != 2) { LOGS.UNKNOWN_COMMAND.log("") ; continue }
                    describe(input[1], mutableListOf())
                }
                "SALIR" -> return
                else -> { LOGS.UNKNOWN_COMMAND.log("") ; continue }
            }
        }
    }
}