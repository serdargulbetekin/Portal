package com.example.portal.modules.base


import android.accounts.NetworkErrorException
import android.util.Log
import retrofit2.Response
import java.net.UnknownHostException
import java.net.UnknownServiceException


open class BaseRepository {

    private val TAG = this.javaClass.simpleName

    suspend fun <T> safeRequest(call: suspend () -> Response<T>): Result<T?> {

        return try {

            val response = call.invoke()
            val responseData = response.body()

            if (response.isSuccessful || responseData != null) {
                Result.success(responseData)
            } else {
                Log.i(TAG, response.errorBody().toString())
                Result.error(message = response.errorBody().toString(), data = null)
            }
        } catch (exception: Exception) {
            val error = when (exception) {
                is NetworkErrorException -> {
                    "Network Error Exception"
                }
                is UnknownHostException -> {
                    "Unknown Host Exception"
                }

                is UnknownServiceException -> {
                    "Unknown Service Exception"
                }

                else -> {
                    exception.message.toString()
                }
            }
            Log.e(TAG, "-----ERROR START-----\n")
            Log.e(TAG, error)
            Log.e(TAG, "-----ERROR END-----\n")
            Result.error(message = error, data = null)
        }
    }

}