private fun readMap(lines: List<String>): Pair<Int, MutableList<MutableList<Char>>> {
//    var minx = lines.map { it.split(" -> ") }.flatten().map { it.split(",")[0].toInt() }.min()
//    var maxx = lines.map { it.split(" -> ") }.flatten().map { it.split(",")[0].toInt() }.max()
//    var miny = lines.map { it.split(" -> ") }.flatten().map { it.split(",")[1].toInt() }.min()
    val maxy = lines.map { it.split(" -> ") }.flatten().map { it.split(",")[1].toInt() }.max()
    val minx = 500 - maxy - 1
    val maxx = 500 + maxy + 1
    val map = MutableList(maxy + 2) { MutableList(maxx - minx + 1) { '.' } }
    lines.forEach { line ->
        line.split(" -> ").reduce { firstPoint, secondPoint ->
            val (x0, y0) = firstPoint.split(",").map { it.toInt() }
            val (x1, y1) = secondPoint.split(",").map { it.toInt() }
            if (x0 == x1) {
                val x = x0
                val yFirst = minOf(y0, y1)
                val yLast = maxOf(y0, y1)
                for (y in yFirst..yLast) {
                    map[y][x - minx] = '#'
                }
            } else {
                val y = y0
                val xFirst = minOf(x0, x1)
                val xLast = maxOf(x0, x1)
                for (x in xFirst..xLast) {
                    map[y][x - minx] = '#'
                }
            }
            secondPoint
        }
    }

    return minx to map
}

fun getAfterFallPosition(x0: Int, map: List<List<Char>>): Pair<Int, Int>? {
    var x = x0
    var y = 0

    while (y < map.size - 1) {
        if (map[y + 1][x] == '.') {
            y++
        } else if (x == 0) {
            return null
        } else if (map[y + 1][x - 1] == '.') {
            y++
            x--
        } else if (x == map[0].size - 1) {
            return null
        } else if (map[y + 1][x + 1] == '.') {
            y++
            x++
        } else {
            return x to y
        }
    }
    return null
}

fun getAfterFallPositionLastLineOk(x0: Int, map: List<List<Char>>): Pair<Int, Int>? {
    var x = x0
    var y = 0

    while (y < map.size - 1) {
        if (map[y + 1][x] == '.') {
            y++
        } else if (x == 0) {
            return null
        } else if (map[y + 1][x - 1] == '.') {
            y++
            x--
        } else if (x == map[0].size - 1) {
            return null
        } else if (map[y + 1][x + 1] == '.') {
            y++
            x++
        } else {
            return x to y
        }
    }
    return x to y
}

fun printMap(map: List<List<Char>>) {
    map.map { it.joinToString("") }.forEach { println(it) }
}

private fun getAnswer1(lines: List<String>): Int {
    val (xDelta, map) = readMap(lines)
    var totalParticles = 0
    while (true) {
        val afterFallPosition = getAfterFallPosition(500 - xDelta, map)
        if (afterFallPosition == null) {
            printMap(map)
            return totalParticles
        } else {
            totalParticles++
            val (x, y) = afterFallPosition
            map[y][x] = 'O'
        }
    }
}

private fun getAnswer2(lines: List<String>): Int {
    val (xDelta, map) = readMap(lines)
    var totalParticles = 0
    while (true) {
        val afterFallPosition = getAfterFallPositionLastLineOk(500 - xDelta, map)
        if (afterFallPosition == null) {
            println()
            println()
            println()
            println()
            printMap(map)
            throw RuntimeException("error in algorithm")
        } else {
            totalParticles++
            val (x, y) = afterFallPosition
            map[y][x] = 'O'
            if (y == 0) {
                printMap(map)
                return totalParticles
            }
        }
    }
}

fun main() {
    val taskNum = 14
    val expectedTestResultPart1 = 24
    val expectedTestResultPart2 = 93

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