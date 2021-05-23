/*
 * Léon - The URL Cleaner
 * Copyright (C) 2021 Sven Jacobs
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
