import com.google.gson.annotations.SerializedName

data class GitHubResponse(
    @SerializedName("items") val items: List<Repository>
)

data class Repository(
    @SerializedName("name") val name: String,
    @SerializedName("html_url") val htmlUrl: String,
    @SerializedName("description") val description: String?,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("language") val language: String
)

data class Owner(
    @SerializedName("login") val login: String,
    @SerializedName("html_url") val htmlUrl: String
)

