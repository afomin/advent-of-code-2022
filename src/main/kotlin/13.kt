

private fun String.digitsStartingFrom(position: Int): String {
    var positionAfterDigits = position
    while (positionAfterDigits < this.length && this[positionAfterDigits].isDigit()) {
        positionAfterDigits++
    }
    return this.substring(position until positionAfterDigits)
}

private fun String.substringTillCorrespondingBracket(position: Int): String {
    var closeBracketPosition = position + 1 // skipping first [
    var extraBrackets = 0
    while (this[closeBracketPosition] != ']' || extraBrackets > 0) {
        when (this[closeBracketPosition++]) {
            '[' -> extraBrackets++
            ']' -> extraBrackets--
        }
    }
    return this.substring(position..closeBracketPosition)
}
private fun compare(first: String, second: String): Int {
    var firstPosition = 0
    var secondPosition = 0

    println("Comparing $first & $second")
    while (firstPosition < first.length && secondPosition < second.length) {
        if (first[firstPosition] != '[' && second[secondPosition] != '[') {
            val firstValueRaw = first.digitsStartingFrom(firstPosition)
            firstPosition += firstValueRaw.length + 1
            val secondValueRaw = second.digitsStartingFrom(secondPosition)
            secondPosition += secondValueRaw.length + 1
            println("   Comparing $firstValueRaw & $secondValueRaw")
            if (firstValueRaw.toInt() > secondValueRaw.toInt()) {
                println("   contract broke")
                return -1
            } else if (firstValueRaw.toInt() < secondValueRaw.toInt()) {
                println("   contract fulfilled")
                return 1
            }
        } else {
            // comparing lists now
            val firstList =
                if (first[firstPosition] != '[') {
                    val rawValue = first.digitsStartingFrom(firstPosition)
                    firstPosition += rawValue.length + 1
                    rawValue
                } else {
                    val rawValue = first.substringTillCorrespondingBracket(firstPosition)
                    firstPosition += rawValue.length + 1
                    rawValue.substring(1 until rawValue.length-1)
                }

            val secondList =
                if (second[secondPosition] != '[') {
                    val rawValue = second.digitsStartingFrom(secondPosition)
                    secondPosition += rawValue.length + 1
                    rawValue
                } else {
                    val rawValue = second.substringTillCorrespondingBracket(secondPosition)
                    secondPosition += rawValue.length + 1
                    rawValue.substring(1 until rawValue.length-1)
                }
            val comparisonResult = compare(firstList, secondList)
            if (comparisonResult != 0) {
                return comparisonResult
            }
        }
    }

    if (firstPosition < first.length) {
        println("   Elements left in first - contract broke")
        return -1
    }

    if (secondPosition < second.length) {
        println("   Elements left in second - contract fulfilled")
        return 1
    }
    return 0
}


private fun getAnswer1(lines: List<List<String>>) =
    lines.mapIndexed() { index, linesPair ->
        println("")
        val firstArray = linesPair[0].substring(1 until linesPair[0].length - 1)
        val secondArray = linesPair[1].substring(1 until linesPair[1].length - 1)
        println()
        if (compare(firstArray, secondArray) > 0) {
            println("-------------------- got ${index + 1}")
            index + 1
        } else {
            0
        }
    }.sum()

private fun getPositionInSorted(lines: List<String>, element: String) =
    lines.filter { it.isNotEmpty() }.count {
        val firstArray = it.substring(1 until it.length - 1)
        val secondArray = element.substring(1 until element.length - 1)
        compare(firstArray, secondArray) > 0
    }

private fun getAnswer2(lines: List<List<String>>): Int {
    val flatLines = lines.flatten()
    return (getPositionInSorted(flatLines, "[[2]]") + 1) *
            (getPositionInSorted(flatLines, "[[6]]") + 2)
}
fun main() {
    val taskNum = 13
    val expectedTestResultPart1 = 13
    val expectedTestResultPart2 = 140

    val testInput = readInputOneLine("${taskNum}_test").split("\n\n").map { it.split("\n") }

    val testResultPart1 = getAnswer1(testInput)
    val realInput = readInputOneLine("$taskNum").split("\n\n").map { it.split("\n") }
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