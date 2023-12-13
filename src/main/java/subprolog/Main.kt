package subprolog

import java.util.regex.Pattern

object Main {
    @JvmStatic
    fun main(args: Array<String>)  {
        // Fuck in(c(e,P), t(i, o(N))), I am to lasy for implementing a Prolog parser
        var rules : MutableList<String> = mutableListOf(
            "hola",
            "ci3641",
            "quickSort",
            "Hola",
            "CI3641",
            "hola chao",
            "QuickSort",
            "padre(juan, jose)",
            "padre(jose, pablo)",
            "padre(pablo, gaby)",
            "ancestro(X, Y) padre(X, Y)",
            "ancestro(X, Y) padre(X, Z) ancestro(Z, Y)",
            "f(x, y)",
            "quickSort(entrada, Salida)",
        )

        fun expressionMatcher(expression : String) : MutableList<String> {
            val atomRegex = "([a-z]\\w*)"
            val variableRegex = "([A-Z]\\w*)"
            val structureRegex = "([a-z]*\\w*\\(([a-z]*\\w*\\s*,\\s*)*[a-z]*\\w*\\))"

            val pattern = Pattern.compile("${structureRegex}|${variableRegex}|${atomRegex}")
            val matcher = pattern.matcher(expression)

            val matches = mutableListOf<String>()
            while (matcher.find()) {
                matches.add(matcher.group())
            }

            return matches
        }

        fun ruleGenerator(rules: MutableList<String>) = sequence {
            var matches : MutableList<String>
            for (rule in rules) {
                yield(expressionMatcher(rule))
            }
        }


        fun compare(rule: String, goal: String): Int {
            val ruleName = rule.substringBefore("(")
            val ruleArgs = rule.substringAfter("(").substringBefore(")").split(", ")

            val goalName = goal.substringBefore("(")
            val goalArgs = goal.substringAfter("(").substringBefore(")").split(", ")

            if (ruleName != goalName || ruleArgs.size != goalArgs.size) return -1
            for (i in ruleArgs.indices) {
                if (ruleArgs[i] != ruleArgs[i]) return 0
            }

            return 1
        }

        val goal = "padre(X, gaby)"
        fun search(goal : String) {
            for (rule in ruleGenerator(rules)) {
                when(compare(rule[0], goal)) {
                    -1 -> { continue }
                    1 -> { if (rule.size == 1) { println("FOUND TRUE: ${rule[0]} "); return } }
                    0 -> {
                        for (item in rule.subList(1, rule.size - 1)) {
                            search(item)
                        }
                    }

                }

                // println("$goal -- ${rule[0]} -- ${compare(rule[0], goal)}")
            }
        }

        search(goal)


    }
}