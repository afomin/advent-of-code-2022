private fun String.snafuToDec() =
    this.toCharArray().map { when(it) {
        '0' -> 0L
        '1' -> 1L
        '2' -> 2L
        '-' -> -1L
        '=' -> -2L
        else -> throw RuntimeException("Not expected")
    } }.reduce { currentNumber, nextDigit ->
        currentNumber * 5 + nextDigit
    }

private fun Long.decToFive() : List<Int> {
    var mutableNumber = this
    return sequence<Int> {
        while (mutableNumber > 0) {
            yield((mutableNumber % 5).toInt())
            mutableNumber /= 5
        }
    }.toList()
}

private fun List<Int>.fiveToSnafu(): String {
    val (lastAddition, resValue) = this.fold(0 to "") { (additionalNum, resultStr), nextDigit ->
        when (val newNum = additionalNum + nextDigit) {
            0,1,2 -> 0 to "$newNum$resultStr"
            3 -> 1 to "=$resultStr"
            4 -> 1 to "-$resultStr"
            5 -> 1 to "0$resultStr"
            else -> throw RuntimeException("Not expected")
        }
    }
    return if (lastAddition != 0) {
        "1$resValue"
    } else {
        resValue
    }
}

private fun getAnswer1(lines: List<String>) =
    lines.sumOf { it.snafuToDec() }.decToFive().fiveToSnafu()


private fun getAnswer2(lines: List<String>) =
    lines.count()

fun main() {
    val taskNum = 25
    val expectedTestResultPart1 = "2=-1=0"
    val expectedTestResultPart2 = 0

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