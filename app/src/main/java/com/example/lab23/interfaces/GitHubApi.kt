import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {
    @GET("search/repositories")
    fun searchRepositories(@Query("q") query: String): Call<GitHubResponse>
}