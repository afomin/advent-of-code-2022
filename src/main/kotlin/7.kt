data class SourceTreeNode(
    val name: String,
    var children: HashMap<String, SourceTreeNode>?,
    val parent: SourceTreeNode?,
    var size: Int?,
    val isDir: Boolean
) {
    override fun toString() = "[${this.size}]${this.name}"
}

private fun readSourceTree(lines: List<String>): SourceTreeNode {
    val root = SourceTreeNode(
        name = "/",
        children = hashMapOf(),
        parent = null,
        size = null,
        isDir = true
    )
    var currentDir = root
    lines.forEach { commandLine ->
        if (!commandLine.startsWith("$")) {
            val (typeOrSize, name) = commandLine.split(" ")
            if (typeOrSize == "dir") {
                // dir a
                currentDir.children!![name] =
                    SourceTreeNode(
                        name = name,
                        children = hashMapOf(),
                        parent = currentDir,
                        size = null,
                        isDir = true
                    )
            } else {
                // 14848514 b.txt
                currentDir.children!![name] =
                    SourceTreeNode(
                        name = name,
                        children = null,
                        parent = currentDir,
                        size = typeOrSize.toInt(),
                        isDir = false
                    )
            }
        } else {
            // parsing command starting with $
            // ls blabla - do nothing
            // cd
            if (commandLine.startsWith("$ cd ")) {
                val name = commandLine.substring("$ cd ".length)
                currentDir = when (name) {
                    "/" -> root
                    ".." -> currentDir.parent!!
                    else -> currentDir.children!![name]!!
                }
            }
        }
    }
    return root
}

private fun fillDirSizes(dir: SourceTreeNode, action: (SourceTreeNode) -> Unit = {}): Int {
    dir.size = dir.children!!.map { it.value }.sumOf { if (it.isDir) fillDirSizes(it, action) else it.size!! }
    action(dir)
    return dir.size!!
}

private fun getAnswer1(lines: List<String>) : Int {
    var sizeSum = 0
    val root = readSourceTree(lines)
    fillDirSizes(root) {
        if (it.name != "/" && it.size!! <= 100000 ) {
            sizeSum += it.size!!
        }
    }
    return sizeSum
}

private fun getAnswer2(lines: List<String>, minSizeRequired: Int): Int {
    var bestMatchDirSize = 70000000
    fillDirSizes(readSourceTree(lines)) {
        if (it.name != "/" && it.size!! >= minSizeRequired && it.size!! < bestMatchDirSize ) {
            bestMatchDirSize =  it.size!!
        }
    }
    return bestMatchDirSize
}

fun main() {
    val taskNum = 7
    val expectedTestResultPart1 = 95437
    val expectedTestResultPart2 = 24933642

    val testInput = readInput("${taskNum}_test")
    val testResultPart1 = getAnswer1(testInput)

    val realInput = readInput("$taskNum")
    val realResultPart1 = getAnswer1(realInput)
    println("task $taskNum part 1 answer = $realResultPart1")
    val testResultPart2 = getAnswer2(testInput, 8381165)

    val realResultPart2 = getAnswer2(realInput, 5174025
    )
    println("task $taskNum part 2 answer = $realResultPart2")

    assert(testResultPart1 == expectedTestResultPart1) {
        "part 1. got result $testResultPart1, expected $expectedTestResultPart1"
    }
    assert(testResultPart2 == expectedTestResultPart2) {
        "part 2. got result $testResultPart2, expected $expectedTestResultPart2"
    }
}