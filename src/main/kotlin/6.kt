private fun getAnswer1(line: String): Int {
    val requiredPatternSize = 4
    return findPatternPosition(line, requiredPatternSize)
}

private fun getAnswer2(line: String): Int {
    val requiredPatternSize = 14
    return findPatternPosition(line, requiredPatternSize)
}

private fun findPatternPosition(line: String, requiredPatternSize: Int): Int {
    val currentPatternLetters = mutableSetOf<Char>()
    var currentPatternPosition = 0
    var currentPatternSize = 0
    line.forEach {
        if (currentPatternLetters.contains(it)) {
            while (line[currentPatternPosition] != it) {
                currentPatternLetters.remove(line[currentPatternPosition])
                currentPatternPosition++
                currentPatternSize--
            }
            // skipping the first occurrence of the symbol - no changes in size here
            currentPatternPosition++
        } else {
            currentPatternLetters.add(it)
            currentPatternSize++
            if (currentPatternSize == requiredPatternSize) {
                return currentPatternPosition + requiredPatternSize
            }
        }
    }
    throw UnknownError("wrong input")
}


fun main() {
    val taskNum = 6
    val expectedTestResultPart1 = 10
    val expectedTestResultPart2 = 29

    val testInput = readInputOneLine("${taskNum}_test")
    val testResultPart1 = getAnswer1(testInput)
    assert(testResultPart1 == expectedTestResultPart1)
    val realInput = readInputOneLine("$taskNum")
    val realResultPart1 = getAnswer1(realInput)
    println("task $taskNum part 1 answer = $realResultPart1")
    val testResultPart2 = getAnswer2(testInput)
    assert(testResultPart2 == expectedTestResultPart2)
    val realResultPart2 = getAnswer2(realInput)
    println("task $taskNum part 2 answer = $realResultPart2")
}