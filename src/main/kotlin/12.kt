private fun getAnswer1(lines: List<String>): Int {
    var startX = 0
    var startY = 0
    var endX = 0
    var endY = 0
    val map = lines.mapIndexed { y, line ->
        var resLine = line
        val startPos = line.indexOf('S')
        if (startPos != -1) {
            startX = startPos
            startY = y
            resLine = resLine.replace('S', 'a')
        }
        val endPos = line.indexOf('E')
        if (endPos != -1) {
            endX = endPos
            endY = y
            resLine = resLine.replace('E', 'z')
        }
        resLine
    }

    val directionsMap = List(map.size) { MutableList(map[0].length) { '.' } }
    directionsMap[startY][startX] = '*'
    var stepNum = 0
    var currentLevel = setOf(startX to startY)
    while (!currentLevel.contains(endX to endY)) {
        stepNum++
        val newLevel = mutableSetOf<Pair<Int, Int>>()
        currentLevel.forEach { (x, y) ->
            if (x > 0) {
                val newX = x - 1
                val newY = y
                if (directionsMap[newY][newX] == '.' && (map[newY][newX].code - map[y][x].code) <= 1) {
                    directionsMap[newY][newX] = '>'
                    newLevel.add(newX to newY)
                }
            }
            if (x < map[y].length - 1) {
                val newX = x + 1
                val newY = y
                if (directionsMap[newY][newX] == '.' && (map[newY][newX].code - map[y][x].code) <= 1) {
                    directionsMap[newY][newX] = '<'
                    newLevel.add(newX to newY)
                }
            }
            if (y > 0) {
                val newX = x
                val newY = y - 1
                if (directionsMap[newY][newX] == '.' && (map[newY][newX].code - map[y][x].code) <= 1) {
                    directionsMap[newY][newX] = 'V'
                    newLevel.add(newX to newY)
                }
            }
            if (y < map.size - 1) {
                val newX = x
                val newY = y + 1
                if (directionsMap[newY][newX] == '.' && (map[newY][newX].code - map[y][x].code) <= 1) {
                    directionsMap[newY][newX] = '^'
                    newLevel.add(newX to newY)
                }
            }
        }
        currentLevel = newLevel
    }
    directionsMap.forEach { println(it.joinToString("")) }
    return stepNum
}

private fun getAnswer2(lines: List<String>): Int {
    val startX = 0
    val startY = 0
    var endX = 0
    var endY = 0
    var currentLevel = mutableSetOf<Pair<Int,Int>>()
    val map = lines.mapIndexed { y, line ->
        var resLine = line.replace('S', 'a')
        resLine.forEachIndexed { x, c ->
            if (c == 'S' || c == 'a') {
                currentLevel.add(x to y)
            }
        }
        val endPos = line.indexOf('E')
        if (endPos != -1) {
            endX = endPos
            endY = y
            resLine = resLine.replace('E', 'z')
        }
        resLine
    }

    val directionsMap = List(map.size) { MutableList(map[0].length) { '.' } }
    directionsMap[startY][startX] = '*'
    var stepNum = 0

    while (!currentLevel.contains(endX to endY)) {
        stepNum++
        val newLevel = mutableSetOf<Pair<Int, Int>>()
        currentLevel.forEach { (x, y) ->
            if (x > 0) {
                val newX = x - 1
                val newY = y
                if (directionsMap[newY][newX] == '.' && (map[newY][newX].code - map[y][x].code) <= 1) {
                    directionsMap[newY][newX] = '>'
                    newLevel.add(newX to newY)
                }
            }
            if (x < map[y].length - 1) {
                val newX = x + 1
                val newY = y
                if (directionsMap[newY][newX] == '.' && (map[newY][newX].code - map[y][x].code) <= 1) {
                    directionsMap[newY][newX] = '<'
                    newLevel.add(newX to newY)
                }
            }
            if (y > 0) {
                val newX = x
                val newY = y - 1
                if (directionsMap[newY][newX] == '.' && (map[newY][newX].code - map[y][x].code) <= 1) {
                    directionsMap[newY][newX] = 'V'
                    newLevel.add(newX to newY)
                }
            }
            if (y < map.size - 1) {
                val newX = x
                val newY = y + 1
                if (directionsMap[newY][newX] == '.' && (map[newY][newX].code - map[y][x].code) <= 1) {
                    directionsMap[newY][newX] = '^'
                    newLevel.add(newX to newY)
                }
            }
        }
        currentLevel = newLevel
    }
    directionsMap.forEach { println(it.joinToString("")) }
    return stepNum
}

fun main() {
    val taskNum = 12
    val expectedTestResultPart1 = 31
    val expectedTestResultPart2 = 29

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