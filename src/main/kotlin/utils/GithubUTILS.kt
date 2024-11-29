package kr.apo2073.utils

import com.google.gson.JsonParser
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

object GithubUTILS {
    private const val GITHUB_API="https://api.github.com"

    private val GITHUB_TOKEN: String by lazy {
        object {}.javaClass.getResource("TOKEN")?.readText()?.trim() ?: ""
    }
    
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

    fun getUserRepos(user: String): List<String> {
        val url = "$GITHUB_API/$KEY_USERS/$user/$KEY_REPO"
        val response = fetchData(url)
        val jsonArray = JsonParser.parseString(response).asJsonArray
        val repoNames = mutableListOf<String>()

        for (jsonElement in jsonArray) {
            val repoObject = jsonElement.asJsonObject
            repoNames.add(repoObject["name"].asString)
        }

        return repoNames
    }
    
    fun getLatestReleaseTag(user: String, repo:String): String {
        val json=JsonGenerator()
            .ToJsonObject(fetchData(getLatestRELEASE(user, repo)))
        return json.get(KEY_TAG_NAME).asString
    }
    
    fun downloadLatest(user: String, repo: String, file:File) {
        val json=JsonGenerator()
            .ToJsonObject(fetchData(getLatestRELEASE(user, repo)))
        val downloadURL=URL(json.get(KEY_ASSETS)
            .asJsonObject[KEY_BROWSER_DOWNLOAD_URL].asString)
        
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