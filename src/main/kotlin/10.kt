private fun getAnswer1(lines: List<String>): Int {
    var currentCycle = 1
    var expectedCycle = 20
    val diffWithNextExpectedCycle = 40
    val maxExpectedCycle = 220
    var currentValue = 1
    var result = 0
    lines.forEach { line ->
        if (currentCycle == expectedCycle ||
            (currentCycle == expectedCycle - 1 && line.startsWith("addx"))
        ) {
            result += currentValue * expectedCycle
            expectedCycle += diffWithNextExpectedCycle
            if (expectedCycle > maxExpectedCycle) {
                return result
            }
        }
        if (line == "noop") {
            currentCycle++
        } else {
            currentValue += line.split(" ")[1].toInt()
            currentCycle += 2
        }
    }
    return -1
}

private fun getAnswer2(lines: List<String>) {
    var currentCycle = 0
    var currentX = 1
    val lineSize = 40

    fun printSymbol(cycleNum: Int, x: Int, lineSize: Int) {
        val position = cycleNum % lineSize
        if (position in x - 1..x + 1) {
            print("#")
        } else {
            print(".")
        }
        if ((cycleNum + 1) % lineSize == 0) {
            println()
        }
    }

    lines.forEach { line ->
        printSymbol(currentCycle, currentX, lineSize)
        if (line == "noop") {
            currentCycle++
        } else {
            currentCycle++
            printSymbol(currentCycle, currentX, lineSize)
            currentCycle++
            currentX += line.split(" ")[1].toInt()
        }
    }
}

fun main() {
    val taskNum = 10
    val expectedTestResultPart1 = 13140

    val testInput = readInput("${taskNum}_test")
    val testResultPart1 = getAnswer1(testInput)
    val realInput = readInput("$taskNum")
    val realResultPart1 = getAnswer1(realInput)
    println("task $taskNum part 1 answer = $realResultPart1")
    println("Part 2 test:")
    getAnswer2(testInput)
    println("Part 2 real:")
    getAnswer2(realInput)


    assert(testResultPart1 == expectedTestResultPart1) {
        "part 1. got result $testResultPart1, expected $expectedTestResultPart1"
    }

}