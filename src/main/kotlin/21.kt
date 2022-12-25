import java.lang.NullPointerException

private data class Monkey(
    var value: Long?,
    val dep1: String? = null,
    val dep2: String? = null,
    val calculateValueFun: (Monkey) -> Long = { value!! }
)

private data class Monkey2(
    val value: Long?,
    val dep1: String? = null,
    val dep2: String? = null,
    val calculateValueFun: (Long, Long) -> Long = { _, _ -> value!! }
) {
    var calculatedValue = value
    fun calculateValue(monkeys: Map<String, Monkey2>): Long {
        if (value != null) {
            return value
        }
        if (calculatedValue == null) {
            val val1 = monkeys[dep1]!!.calculateValue(monkeys)
            val val2 = monkeys[dep2]!!.calculateValue(monkeys)
            calculatedValue = calculateValueFun(val1, val2)
        }
        return calculatedValue!!
    }
}


private fun getAnswer1(lines: List<String>): Long {
    val monkeys = mutableMapOf<String, Monkey>()
    lines.forEach {
        val (name, value) = it.split(": ")
        val rightParts = value.split(" ")
        val monkey = if (rightParts.size == 1) {
            Monkey(value.toLong())
        } else {
            val (dep1, operation, dep2) = rightParts

            when (operation) {
                "*" -> Monkey(null, dep1, dep2) { monkey ->
                    val monkey1 = monkeys[dep1]!!
                    val monkey2 = monkeys[dep2]!!
                    val val1 = monkey1.value ?: monkey1.calculateValueFun(monkey1)
                    val val2 = monkey2.value ?: monkey2.calculateValueFun(monkey2)
                    val result = val1 * val2
                    monkey.value = result
                    result
                }

                "/" -> Monkey(null, dep1, dep2) { monkey ->
                    val monkey1 = monkeys[dep1]!!
                    val monkey2 = monkeys[dep2]!!
                    val val1 = monkey1.value ?: monkey1.calculateValueFun(monkey1)
                    val val2 = monkey2.value ?: monkey2.calculateValueFun(monkey2)
                    val result = val1 / val2
                    monkey.value = result
                    result
                }

                "+" -> Monkey(null, dep1, dep2) { monkey ->
                    val monkey1 = monkeys[dep1]!!
                    val monkey2 = monkeys[dep2]!!
                    val val1 = monkey1.value ?: monkey1.calculateValueFun(monkey1)
                    val val2 = monkey2.value ?: monkey2.calculateValueFun(monkey2)
                    val result = val1 + val2
                    monkey.value = result
                    result
                }

                "-" -> Monkey(null, dep1, dep2) { monkey ->
                    val monkey1 = monkeys[dep1]!!
                    val monkey2 = monkeys[dep2]!!
                    val val1 = monkey1.value ?: monkey1.calculateValueFun(monkey1)
                    val val2 = monkey2.value ?: monkey2.calculateValueFun(monkey2)
                    val result = val1 - val2
                    monkey.value = result
                    result
                }

                else -> throw RuntimeException("not expected")
            }
        }
        monkeys[name] = monkey
    }
    val rootMonkey = monkeys["root"]!!
    return rootMonkey.calculateValueFun(rootMonkey)
}

private fun getAnswer2(lines: List<String>): Long {
    val reverseMonkeys = mutableMapOf<String, Monkey2>()
    val monkeys = mutableMapOf<String, Monkey2>()
    lines.forEach {
        val (name, value) = it.split(": ")
        val rightParts = value.split(" ")
        if (name == "humn" || name == "root") {
            return@forEach
        }
        if (rightParts.size == 1) {
            reverseMonkeys[name] = Monkey2(value.toLong())
            monkeys[name] = Monkey2(value.toLong())
            return@forEach
        }
        val (dep1, operation, dep2) = rightParts
        val divide = {a: Long, b: Long -> a / b}
        val divideRev = {a: Long, b: Long -> b / a}
        val multiply = {a: Long, b: Long -> a * b}
        val add = {a: Long, b: Long -> a + b}
        val subtract = { a: Long, b: Long -> a - b}
        val subtractRev = {a: Long, b: Long -> b - a}


        val (directFun, reverseFun1, reverseFun2) = when (operation) {
            "*" -> arrayOf(multiply, divide, divide)
            "/" -> arrayOf(divide, multiply, divideRev)
            "+" -> arrayOf(add, subtract, subtract)
            "-" -> arrayOf(subtract, add, subtractRev)
            else -> throw RuntimeException("not expected")
        }

        reverseMonkeys.putIfAbsent(dep1, Monkey2(null, name, dep2, reverseFun1))
        reverseMonkeys.putIfAbsent(dep2, Monkey2(null, name, dep1, reverseFun2))
        monkeys[name] = Monkey2(null, dep1, dep2, directFun)

    }
    val (_, firstMonkeyName, _, secondMonkeyName) = lines.find { it.startsWith("root: ") }!!.split(" ")
    try {
        val firstValue = monkeys[firstMonkeyName]!!.calculateValue(monkeys)
        reverseMonkeys[secondMonkeyName] = Monkey2(firstValue)
    } catch (e: NullPointerException) {
        // muahaha. worst code ever but who cares:)
        monkeys.values.forEach{ it.calculatedValue = null }
        val secondValue = monkeys[secondMonkeyName]!!.calculateValue(monkeys)
        reverseMonkeys[firstMonkeyName] = Monkey2(secondValue)
    }
    monkeys.forEach { (name, monkey) ->
        if (monkey.calculatedValue != null) {
            reverseMonkeys[name] = Monkey2(monkey.calculatedValue)
        }
    }
    return reverseMonkeys["humn"]!!.calculateValue(reverseMonkeys)
}

fun main() {
    val taskNum = 21
    val expectedTestResultPart1 = 152L
    val expectedTestResultPart2 = 301L

    val testInput = readInput("${taskNum}_test")
    val testResultPart1 = getAnswer1(testInput)
    val realInput = readInput("$taskNum")
    val realResultPart1 = getAnswer1(realInput)
    println("task $taskNum part 1 answer = $realResultPart1")
    val testResultPart2 = getAnswer2(testInput)
    val realResultPart2 = getAnswer2(realInput)
    println("task $taskNum part 2 answer = $realResultPart2")


    assert(testResultPart1 == expectedTestResultPart1) {
        "part 1. got result $testResultPart1, expected $expectedTestResultPart1"
    }
    assert(testResultPart2 == expectedTestResultPart2) {
        "part 2. got result $testResultPart2, expected $expectedTestResultPart2"
    }
}