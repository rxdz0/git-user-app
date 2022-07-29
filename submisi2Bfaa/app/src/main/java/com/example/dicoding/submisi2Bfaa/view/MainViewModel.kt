package com.example.dicoding.submisi2Bfaa.view

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dicoding.submisi2Bfaa.view.adapter.EventHandler
import com.example.dicoding.submisi2Bfaa.model.api.ApiConfig
import com.example.dicoding.submisi2Bfaa.model.Users
import com.example.dicoding.submisi2Bfaa.model.gitResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<Users>>()
    val listGithubUser: LiveData<List<Users>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastText = MutableLiveData<EventHandler<String>>()
    val toastText: LiveData<EventHandler<String>> = _toastText

    fun getUser(query: String?) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUser(query)
        client.enqueue(object : Callback<gitResponse> {
            override fun onResponse(
                call: Call<gitResponse>,
                response: Response<gitResponse>
            ) {
                _isLoading.value = false
                val listUser = response.body()?.items
                if (response.isSuccessful) {
                    if (listUser.isNullOrEmpty()) {
                        _toastText.value = EventHandler("404 User not Found")
                    } else {
                        _listUser.value = listUser
                    }
                } else {
                    _toastText.value = EventHandler(response.message())
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<gitResponse>, t: Throwable) {
                _isLoading.value = false
                _toastText.value = EventHandler("connection Problem")
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "MainViewModel"
    }
}