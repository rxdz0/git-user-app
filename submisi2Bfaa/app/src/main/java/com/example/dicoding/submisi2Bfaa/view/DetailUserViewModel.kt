package com.example.dicoding.submisi2Bfaa.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicoding.submisi2Bfaa.model.FollowResponse
import com.example.dicoding.submisi2Bfaa.model.Users
import com.example.dicoding.submisi2Bfaa.view.adapter.EventHandler
import com.example.dicoding.submisi2Bfaa.model.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel : ViewModel() {

    private val _listUser = MutableLiveData<ArrayList<FollowResponse>>()
    val listGithubUser: LiveData<ArrayList<FollowResponse>> = _listUser

    private val _userData = MutableLiveData<Users>()
    val userData: LiveData<Users> = _userData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorText = MutableLiveData<EventHandler<String>>()
    val errorText: LiveData<EventHandler<String>> = _errorText

    fun getDetailUser(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<Users> {
            override fun onResponse(
                call: Call<Users>,
                response: Response<Users>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userData.postValue(response.body())
                } else {
                    _errorText.value = EventHandler("404 User not found")
                    _errorText.value = EventHandler(response.toString())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                _isLoading.value = false
                _errorText.value = EventHandler("No internet connection")
                _errorText.value = EventHandler(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowers(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<ArrayList<FollowResponse>> {
            override fun onResponse(
                call: Call<ArrayList<FollowResponse>>,
                response: Response<ArrayList<FollowResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()
                } else {
                    _errorText.value = EventHandler(response.toString())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<FollowResponse>>, t: Throwable) {
                _isLoading.value = false
                _errorText.value = EventHandler(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getFollowing(username: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowing(username)
        client.enqueue(object : Callback<ArrayList<FollowResponse>> {
            override fun onResponse(
                call: Call<ArrayList<FollowResponse>>,
                response: Response<ArrayList<FollowResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUser.value = response.body()
                } else {
                    _errorText.value = EventHandler(response.toString())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArrayList<FollowResponse>>, t: Throwable) {
                _isLoading.value = false
                _errorText.value = EventHandler(t.message.toString())
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "UserDetailViewModel"
    }
}