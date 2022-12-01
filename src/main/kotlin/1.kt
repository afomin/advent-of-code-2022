fun main() {
    firstPart()
    secondPart()
}

private fun firstPart() {
    var currentSum = 0
    var maxSum = 0
    processInputLineByLine("1/input.txt") {
        if (it.isEmpty()) {
            maxSum = maxOf(maxSum, currentSum)
            currentSum = 0
        } else {
            currentSum += it.toInt()
        }
    }
    println(maxSum)
}

private fun secondPart() {
    var currentSum = 0
    var maxSums = listOf(0, 0, 0)
    processInputLineByLine("1/input.txt") {
        if (it.isEmpty()) {
            val minSum = (maxSums + currentSum).min()
            maxSums = (maxSums + currentSum) - minSum
            currentSum = 0
        } else {
            currentSum += it.toInt()
        }
    }
    println(maxSums)
    println(maxSums.sum())
}