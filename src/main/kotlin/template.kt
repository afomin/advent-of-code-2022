private fun getAnswer1(lines: List<String>) =
    lines.count()

private fun getAnswer2(lines: List<String>) =
    lines.count()

fun main() {
    val taskNum = 0
    val expectedTestResultPart1 = 0
    val expectedTestResultPart2 = 0

    val testInput = readInput("${taskNum}_test")
    val testResultPart1 = getAnswer1(testInput)
    assert(testResultPart1 == expectedTestResultPart1)
    val realInput = readInput("$taskNum")
    val realResultPart1 = getAnswer1(realInput)
    println("task $taskNum part 1 answer = $realResultPart1")
    val testResultPart2 = getAnswer2(testInput)
    assert(testResultPart2 == expectedTestResultPart2)
    val realResultPart2 = getAnswer2(realInput)
    println("task $taskNum part 2 answer = $realResultPart2")
}