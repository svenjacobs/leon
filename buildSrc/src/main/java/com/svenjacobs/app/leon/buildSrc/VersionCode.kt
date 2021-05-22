package com.svenjacobs.app.leon.buildSrc

val versionCode by lazy {
    val commits = execute("git", "rev-list", "--count", "origin/main")
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
