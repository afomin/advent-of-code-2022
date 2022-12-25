private data class Element(
    val value: Long,
    var prev: Element?,
    var next: Element?,
    var originalNext: Element?
)

private fun Element.move(offset : Long) {
    if (offset == 0L) {
        return
    }
    var newPrev = this.prev!!
    this.prev!!.next = this.next
    this.next!!.prev = this.prev
    if (this.value > 0) {
        for (i in 1 .. offset) {
            newPrev = newPrev.next!!
        }
    } else {
         /* this.value < 0 */
        for (i in -1 downTo offset) {
            newPrev = newPrev.prev!!
        }
    }
    this.next = newPrev.next!!
    this.prev = newPrev
    newPrev.next = this
    this.next!!.prev = this
}

private fun Element.getElementWithOffset(offset : Int) : Element {
    var element = this
    for (i in 0 until offset) {
        element = element.next!!
    }
    return element
}

private data class CyclicList(val first: Element, val zero: Element, val size: Int)
private fun readCyclicList(lines: List<Long>) : CyclicList {
    val zeroElement = Element(0, null, null, null)
    val allElements = lines.map {
        if (it == 0L) {
            zeroElement
        } else {
            Element(it, null, null, null)
        }
    }
    val first = allElements[0]
    val last = allElements.last()
    allElements.reduce{acc, element ->
        acc.next = element
        acc.originalNext = element
        element.prev = acc
        element
    }
    first.prev = last
    last.next = first
    return CyclicList(first, zeroElement, allElements.size)
}
private fun getAnswer1(lines: List<String>): Long {
    val numbers = lines.map { it.toLong() }
    val cyclicList = readCyclicList(numbers)
    val zeroElement = cyclicList.zero
    var element : Element? = cyclicList.first
    while (element != null) {
        element.move(element.value % (cyclicList.size - 1))
        element = element.originalNext
    }
    val el1000 = zeroElement.getElementWithOffset(1000)
    val el2000 = el1000.getElementWithOffset(1000)
    val el3000 = el2000.getElementWithOffset(1000)
    return el1000.value + el2000.value + el3000.value
}

@Suppress("SameParameterValue")
private fun getAnswer2(lines: List<String>, decryptionKey: Long) : Long {
    val numbers = lines.map { it.toLong() * decryptionKey }
    val cyclicList = readCyclicList(numbers)
    val zeroElement = cyclicList.zero
    repeat(10) {
        var element : Element? = cyclicList.first
        while (element != null) {
            element.move(element.value % (cyclicList.size - 1))
            element = element.originalNext
        }
    }
    val el1000 = zeroElement.getElementWithOffset(1000)
    val el2000 = el1000.getElementWithOffset(1000)
    val el3000 = el2000.getElementWithOffset(1000)
    return el1000.value + el2000.value + el3000.value
}

fun main() {
    val taskNum = 20
    val expectedTestResultPart1 = 3L
    val expectedTestResultPart2 = 1623178306L
    val decryptionKey = 811589153L

//    val myTestInput = readInput("${taskNum}_my_test")
//    getAnswer1(myTestInput)
    println()
    val testInput = readInput("${taskNum}_test")
    val testResultPart1 = getAnswer1(testInput)
    val realInput = readInput("$taskNum")
    val realResultPart1 = getAnswer1(realInput)
    println("task $taskNum part 1 answer = $realResultPart1")
    val testResultPart2 = getAnswer2(testInput, decryptionKey)
    val realResultPart2 = getAnswer2(realInput, decryptionKey)
    println("task $taskNum part 2 answer = $realResultPart2")


    assert(testResultPart1 == expectedTestResultPart1) {
        "part 1. got result $testResultPart1, expected $expectedTestResultPart1"
    }
    assert(testResultPart2 == expectedTestResultPart2) {
        "part 2. got result $testResultPart2, expected $expectedTestResultPart2"
    }
}