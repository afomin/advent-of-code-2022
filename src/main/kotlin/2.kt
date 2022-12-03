import Result.*
import Selection.*

enum class Selection {
    ROCK,
    PAPER,
    SCISSORS;
}

val betterMap = mapOf(
    ROCK to SCISSORS,
    PAPER to ROCK,
    SCISSORS to PAPER
)

fun Selection.doesntLooseTo(other: Selection) =
    betterMap[other] != this


val valuesMap = mapOf(
    "A" to ROCK,
    "B" to PAPER,
    "C" to SCISSORS,
    "X" to ROCK,
    "Y" to PAPER,
    "Z" to SCISSORS
)

fun getScoreForWin(
    opponent: Selection,
    you: Selection,
    looseScore: Int = 0,
    drawScore: Int = 3,
    winScore: Int = 6
): Int {
    if (opponent == you) return drawScore
    return if (opponent.doesntLooseTo(you)) looseScore else winScore
}

val scoreForValue = mapOf(
    ROCK to 1,
    PAPER to 2,
    SCISSORS to 3
)

fun getTotalScoreForPartOne(lines: List<String>) =
    lines.map { it.split(" ").map { sel -> valuesMap[sel]!! } }
        .sumOf { (opponent, you) -> getScoreForWin(opponent, you) + scoreForValue[you]!! }

enum class Result(val score: Int) {
    WIN(6), LOOSE(0), DRAW(3)
}

val toResultMap = mapOf(
    "X" to LOOSE,
    "Y" to DRAW,
    "Z" to WIN
)

fun chooseSelection(opponent: Selection, result: Result): Selection =
    when (result) {
        DRAW -> opponent
        WIN -> {
            when(opponent) {
                ROCK -> PAPER
                PAPER -> SCISSORS
                SCISSORS -> ROCK
            }
        }
        LOOSE -> {
            when (opponent) {
                ROCK -> SCISSORS
                PAPER -> ROCK
                SCISSORS -> PAPER
            }
        }
    }
fun getTotalScoreForPartTwo(lines: List<String>) =
    lines.map { it.split(" ") }
        .map { (opponentRaw, resultRaw) -> (valuesMap[opponentRaw]!! to toResultMap[resultRaw]!!) }
        .map { (opponent, result) -> chooseSelection(opponent, result) to result }
        .sumOf { (you, result) -> scoreForValue[you]!! + result.score }

fun main() {
    val testInput = readInput("2_test")
    val testResult = getTotalScoreForPartOne(testInput)
    assert(testResult == 15)

    val realInput = readInput("2")
    println("answer 1 is ${getTotalScoreForPartOne(realInput)}")

    val testResultPartTwo = getTotalScoreForPartOne(testInput)
    assert(testResultPartTwo == 12)
    println("answer 2 is ${getTotalScoreForPartTwo(realInput)}")
}