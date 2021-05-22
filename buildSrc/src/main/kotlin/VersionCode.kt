val versionCode by lazy {
    var currentBranch = execute("git", "branch", "--show-current")
    if (currentBranch != "main" && currentBranch != "develop")
        currentBranch = "develop"

    val commits = execute("git", "rev-list", "--count", "origin/$currentBranch")
    if (commits.isBlank()) throw IllegalArgumentException("Could not determine commit count")
    commits.toInt()
}

private fun execute(vararg cmd: String): String {
    var output: String
    val process = ProcessBuilder(*cmd).start()
    process.inputStream.reader(Charsets.UTF_8).use {
        output = it.readText()
    }
    process.waitFor()
    return output.trim()
}
