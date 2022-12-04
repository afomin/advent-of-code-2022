fun String.toRange(): IntRange {
    val (begin, end) = this.split("-").map { it.toInt() }
    return begin..end
}
private fun getAnswer1(lines: List<String>) =
    lines.map{ line -> line.split(",").map { it.toRange() } }
        .count {(first, second) ->
            (first.first in second && first.last in second) ||
                    (second.first in first && second.last in first)
        }

private fun getAnswer2(lines: List<String>) =
    lines.map{ line -> line.split(",").map { it.toRange() } }
        .count {(first, second) ->
            (first.first in second || first.last in second) ||
                    (second.first in first || second.last in first)
        }

fun main() {
    val taskNum = 4
    val expectedTestResultPart1 = 2
    val expectedTestResultPart2 = 4

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