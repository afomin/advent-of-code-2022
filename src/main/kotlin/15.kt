import kotlin.math.abs

private data class Input(
    val sensorX: Int,
    val sensorY: Int,
    val beakonX: Int,
    val beakonY: Int,
    var radius: Int = 0
) {
    init {
        radius = abs(sensorX - beakonX) + abs(sensorY - beakonY)
    }
}

private fun getAnswer1(lines: List<String>, y: Int): Int {
    val pattern = "Sensor at x=(.*), y=(.*): closest beacon is at x=(.*), y=(.*)".toRegex()
    val ranges = lines.map { line ->
        val rawValues = pattern.find(line)!!.groupValues
        Input(
            sensorX = rawValues[1].toInt(),
            sensorY = rawValues[2].toInt(),
            beakonX = rawValues[3].toInt(),
            beakonY = rawValues[4].toInt()
        )
    }.mapNotNull {
        val radius = it.radius - abs(it.sensorY - y)
        if (radius < 0) {
            null
        } else {
            val res = ((it.sensorX - radius)..(it.sensorX + radius)).toMutableList()
            if (it.beakonY == y) {
                res -= it.beakonX
            }
            res
        }
    }
    return ranges.flatten().toSet().size
}

private fun findInSection(minX: Int, minY: Int, maxX: Int, maxY: Int, sensors: List<Input>): Pair<Int, Int>? {
    if (minX > maxX || minY > maxY) {
         return null
    }
    if (minX == maxX) {
        if (sensors.all { sensor ->
                (abs(minX - sensor.sensorX) + abs(minY - sensor.sensorY) > sensor.radius)
            }) {
            return minX to minY
        }
    }
    return if (sensors.any { sensor ->
            (abs(minX - sensor.sensorX) + abs(minY - sensor.sensorY) <= sensor.radius) &&
                    (abs(minX - sensor.sensorX) + abs(maxY - sensor.sensorY) <= sensor.radius) &&
                    (abs(maxX - sensor.sensorX) + abs(minY - sensor.sensorY) <= sensor.radius) &&
                    (abs(maxX - sensor.sensorX) + abs(maxY - sensor.sensorY) <= sensor.radius)
        }) {
        null
    } else {
        val midX = (minX + maxX) / 2
        val midY = (minY + maxY) / 2
        findInSection(minX, minY, midX, midY, sensors) ?: findInSection(midX + 1, minY, maxX, midY, sensors) ?:
        findInSection(minX, midY + 1, midX, maxY, sensors) ?:
        findInSection(midX + 1, midY + 1, maxX, maxY, sensors)
    }
}
private fun getAnswer2(lines: List<String>, max: Int): Long {
    val pattern = "Sensor at x=(.*), y=(.*): closest beacon is at x=(.*), y=(.*)".toRegex()
    val sensors = lines.map { line ->
        val rawValues = pattern.find(line)!!.groupValues
        Input(
            sensorX = rawValues[1].toInt(),
            sensorY = rawValues[2].toInt(),
            beakonX = rawValues[3].toInt(),
            beakonY = rawValues[4].toInt()
        )
    }
    val result = findInSection(0, 0, max, max, sensors) ?: throw RuntimeException("error in algorithm")
    return result.first * 4000000L + result.second

}


fun main() {
    val taskNum = 15
    val expectedTestResultPart1 = 26
    val expectedTestResultPart2 = 56000011L

    val testInput = readInput("${taskNum}_test")
    val testResultPart1 = getAnswer1(testInput, 10)
    val realInput = readInput("$taskNum")
    val realResultPart1 = getAnswer1(realInput, 2_000_000)
    println("task $taskNum part 1 answer = $realResultPart1")
    val testResultPart2 = getAnswer2(testInput, 20)
    val realResultPart2 = getAnswer2(realInput, 4000000)
    println("task $taskNum part 2 answer = $realResultPart2")


    assert(testResultPart1 == expectedTestResultPart1) {
        "part 1. got result $testResultPart1, expected $expectedTestResultPart1"
    }
    assert(testResultPart2 == expectedTestResultPart2) {
        "part 2. got result $testResultPart2, expected $expectedTestResultPart2"
    }
}