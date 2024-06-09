package pl.put.brew

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface ApiCallback<T> {
    fun onSuccess(result: T)
    fun onError(t: Throwable)
}

class ApiService<T> {
    fun makeApiCall(call: Call<T>, callback: ApiCallback<T>) {
        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.v("retrofit", "call failed")
                t.printStackTrace()
                callback.onError(t)
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        callback.onSuccess(it)
                    } ?: run {
                        callback.onError(Throwable("Response body is null"))
                    }
                } else {
                    callback.onError(Throwable("Http Code: ${response.code()}"))
                }
            }
        })
    }
}
