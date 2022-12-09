import java.util.*

private fun buildInitialPosition(rawInput: String): List<Stack<Char>> {
    val lines = rawInput.split("\n").reversed()
    val size = lines.first().length / 4 + 1
    val initialPosition = List<Stack<Char>>(size) { Stack() }
    lines.drop(1).map { line ->
        line
            .toList()
            .chunked(4)
            .map { rawCubeRecord -> rawCubeRecord[1] } // "[?] " -> '?'
            .forEachIndexed { index, c ->
                if (c != ' ')
                    initialPosition[index].push(c)
            }
    }
    return initialPosition
}
private fun getPositionAndCommands(rawInput: String): Pair<List<Stack<Char>>, List<String>> {
    val (initialPositionInput, commandsInput) = rawInput.split("\n\n")
    val initialPosition = buildInitialPosition(initialPositionInput)
    val commands = commandsInput.split("\n")
    return initialPosition to commands
}
private fun getAnswer1(rawInput: String): String {
    val (position, commands) = getPositionAndCommands(rawInput)
    commands.forEach { command ->
        // move 3 from 1 to 3
        val pattern = "move ([0-9]+) from ([0-9]+) to ([0-9]+)".toRegex()
        val (what, from, to) = pattern.find(command)!!.groupValues.drop(1).map { it.toInt() }
        repeat(what) {
            position[to - 1].push(position[from - 1].pop())
        }
    }
    return position.joinToString("") { it.pop()?.toString() ?: "" }
}

private fun getAnswer2(rawInput: String): String {
    val (position, commands) = getPositionAndCommands(rawInput)
    commands.forEach { command ->
        // move 3 from 1 to 3
        val pattern = "move ([0-9]+) from ([0-9]+) to ([0-9]+)".toRegex()
        val (what, from, to) = pattern.find(command)!!.groupValues.drop(1).map { it.toInt() }
        val extraBucket = Stack<Char>()
        repeat(what) {
            extraBucket.push(position[from - 1].pop())
        }
        repeat(what) {
            position[to - 1].push(extraBucket.pop())
        }
    }
    return position.joinToString("") { it.pop()?.toString() ?: "" }
}

fun main() {
    val taskNum = 5
    val expectedTestResultPart1 = "CMZ"
    val expectedTestResultPart2 = "MCD"

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