package kr.apo2073

import kr.apo2073.utils.GithubUTILS
import kr.apo2073.utils.GithubUTILS.getLatestReleaseTag
import java.io.File
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)

    println("Enter the GitHub username:")
    val owner = scanner.next()

    println("Choose an option:")
    println("1. List user repositories\n2. Get latest release info\n3. Download latest release asset")
    val num = scanner.nextInt()

    when (num) {
        1 -> {
            val repos = GithubUTILS.getUserRepos(owner)
            println("Repositories:")
            repos.forEach { println(it) }
        }
        2 -> {
            println("Enter repository name:")
            val repo = scanner.next()
            println("Latest release tag: ${getLatestReleaseTag(owner, repo)}")
        }
        3 -> {
            println("Enter repository name:")
            val repo = scanner.next()
            println("Enter the destination file path:")
            val filePath = scanner.next()
            val file = File(filePath)
            GithubUTILS.downloadLatest(owner, repo, file)
            println("File downloaded to: ${file.absolutePath}")
        }
        else -> println("Invalid option.")
    }
}