private fun getAnswer1(lines: List<String>): Int {
    val treesMap = lines.map { line -> line.toCharArray().map { digit -> digit.digitToInt() }.toList() }
    val height = treesMap.size
    val width = treesMap[0].size

    val visitedMap = MutableList(height) { MutableList(width) { false } }
    treesMap.forEachIndexed { lineNum, line ->
        var maxLeft = -1
        for (i in 0..line.lastIndex) {
            if (line[i] > maxLeft) {
                visitedMap[lineNum][i] = true
                maxLeft = line[i]
            }
        }
        var maxRight = -1
        for (i in line.lastIndex downTo 0) {
            if (line[i] > maxRight) {
                visitedMap[lineNum][i] = true
                maxRight = line[i]
            }
        }
    }
    for (rowNum in 0 until width) {
        var maxTop = -1
        for (i in 0 until height) {
            if (treesMap[i][rowNum] > maxTop) {
                visitedMap[i][rowNum] = true
                maxTop = treesMap[i][rowNum]
            }
        }

        var maxBottom = -1
        for (i in height - 1 downTo 0) {
            if (treesMap[i][rowNum] > maxBottom) {
                visitedMap[i][rowNum] = true
                maxBottom = treesMap[i][rowNum]
            }
        }
    }
    return visitedMap.flatten().count { it }
}

private fun getAnswer2(lines: List<String>): Int {
    val treesMap = lines.map { line -> line.toCharArray().map { digit -> digit.digitToInt() }.toList() }
    val height = treesMap.size
    val width = treesMap[0].size

    fun calculateScenicScore(x: Int, y: Int): Int {
        if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
            return 0
        }
        val currentTree = treesMap[y][x]
        var visibleLeft = 0
        var i = x - 1
        while (i >= 0) {
            visibleLeft++
            if (treesMap[y][i] >= currentTree) {
                break
            }
            i--
        }

        var visibleRight = 0
        i = x + 1
        while (i < width) {
            visibleRight++
            if (treesMap[y][i] >= currentTree) {
                break
            }
            i++
        }

        var visibleUp = 0
        i = y - 1
        while (i >= 0) {
            visibleUp++
            if (treesMap[i][x] >= currentTree) {
                break
            }
            i--
        }

        var visibleDown = 0
        i = y + 1
        while (i < height) {
            visibleDown++
            if (treesMap[i][x] >= currentTree) {
                break
            }
            i++
        }

        return visibleLeft * visibleRight * visibleUp * visibleDown
    }

    var maxScenicScore = 0
    for (y in 0 until height) {
        for (x in 0 until width) {
            val scenicScore = calculateScenicScore(x, y)
            if (scenicScore > maxScenicScore) {
                maxScenicScore = scenicScore
            }
        }
    }
    return maxScenicScore
}

fun main() {
    val taskNum = 8
    val expectedTestResultPart1 = 21
    val expectedTestResultPart2 = 8

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