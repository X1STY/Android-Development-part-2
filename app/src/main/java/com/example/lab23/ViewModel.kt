package com.example.lab23

import GitHubResponse
import Repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab23.retrofitClient.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> get() = _repositories

    fun searchRepositories(query: String) {
        RetrofitClient.instance.searchRepositories(query).enqueue(object : Callback<GitHubResponse> {
            override fun onResponse(call: Call<GitHubResponse>, response: Response<GitHubResponse>) {
                if (response.isSuccessful) {
                    _repositories.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<GitHubResponse>, t: Throwable) {
            }
        })
    }
}
