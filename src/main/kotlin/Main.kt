package kr.apo2073

import kr.apo2073.utils.GithubUTILS
import kr.apo2073.utils.GithubUTILS.getLatestReleaseTag
import java.util.Scanner

fun main() {
    val scanner=Scanner(System.`in`)
    
    println("Enter the github USER name")
    val owner = scanner.next()

    println("Enter what to do")
    println("1. userRepos 2. repoInfo")
    val num = scanner.nextInt()

    if (num == 2) {
        println("Enter repo name")
        val repo= scanner.next()
        println(getLatestReleaseTag(owner, repo)) // line 20
    }
}