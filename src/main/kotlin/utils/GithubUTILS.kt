package kr.apo2073.utils

import java.net.HttpURLConnection
import java.net.URL

object GithubUTILS {
    private const val GITHUB_API="https://api.github.com"

    private const val GITHUB_TOKEN="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"
    
    private const val KEY_ASSETS = "assets"
    private const val KEY_NAME = "name"
    private const val KEY_TAG_NAME = "tag_name"
    private const val KEY_USERS="users"
    private const val KEY_REPO="repos"
    private const val KEY_BROWSER_DOWNLOAD_URL = "browser_download_url"
    
    fun getReposURL(user:String):String {
        return "$GITHUB_API/$KEY_USERS/$user/$KEY_REPO"
    }
    fun getLatestRELEASE(user: String, repo:String):String {
        return "$GITHUB_API/$KEY_REPO/$user/$repo/releases/latest"
    }
    fun getLatestReleaseTag(user: String, repo:String): String {
        val json=JsonGenerator()
            .ToJsonObject(fetchData(getLatestRELEASE(user, repo)))
        return json.get(KEY_TAG_NAME).asString
    }

    fun fetchData(url: String): String {
        return URL(url).httpRequest {
            requestMethod = "GET"
            setRequestProperty("Accept", "application/vnd.github.v3+json")
            setRequestProperty("Authorization", "Bearer $GITHUB_TOKEN")
            inputStream.bufferedReader().use { it.readText() }
        }
    }

    fun <T> URL.httpRequest(requester: (HttpURLConnection.() -> T)): T {
        return with(openConnection() as HttpURLConnection) { requester.invoke(this) }
    }
}