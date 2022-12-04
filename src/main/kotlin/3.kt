private fun getAnswer1(lines: List<String>) =
    lines.map { line ->
        line.substring(0, line.length / 2).toSet().toList() +
                line.substring(line.length / 2, line.length).toSet().toList()

    }.map { letters ->
        letters.groupingBy { it }
            .eachCount()
            .entries
            .find { (_, count) -> count > 1 }!!
    }.sumOf {
        val code = it.key.code
        if (code >= 'a'.code && code <= 'z'.code) {
            code - 'a'.code + 1
        } else {
            code - 'A'.code + 27
        }
    }

private fun getAnswer2(lines: List<String>) =
    lines.asSequence().map {
        it.toSet().toList()
    }.chunked(3).map { it.flatten() }
        .map { letters ->
            letters.groupingBy { it }
                .eachCount()
                .entries
                .find { (_, count) -> count > 2 }!!
        }.sumOf {
            val code = it.key.code
            if (code >= 'a'.code && code <= 'z'.code) {
                code - 'a'.code + 1
            } else {
                code - 'A'.code + 27
            }
        }

fun main() {
    val testInput = readInput("3_test")
    val testResultPart1 = getAnswer1(testInput)
    assert(testResultPart1 == 157)
    val realInput = readInput("3")
    val realResultPart1 = getAnswer1(realInput)
    println("part 1 answer = $realResultPart1")
    val testResultPart2 = getAnswer2(testInput)
    assert(testResultPart2 == 70)
    val realResultPart2 = getAnswer2(realInput)
    println("part 2 answer = $realResultPart2")
}