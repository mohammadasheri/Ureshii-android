package ai.hara.bnvt.util.network

import ai.hara.bnvt.util.AppExecutors
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData

abstract class NetworkBoundResource<ResultType, RequestType>
@MainThread constructor(private val appExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<RequestType>>()

    init {
        result.postValue(Resource.loading(null))
        appExecutors.diskIO().execute {
            fetchData()
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<RequestType>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchData() {
        val dbSource = MutableLiveData<RequestType>()
        val apiResponse = createCall()
        appExecutors.mainThread().execute {
            result.addSource(dbSource) { newData ->
                setValue(Resource.loading(newData))
            }
            result.addSource(apiResponse) { response ->
                result.removeSource(apiResponse)
                result.removeSource(dbSource)
                when (response) {
                    is ApiSuccessResponse -> {
                        result.postValue(Resource.success(response.body))
                    }

                    is ApiNullResponse -> {
                        result.postValue(Resource.success(null))
                    }

                    is ApiErrorResponse -> {
                        result.postValue(Resource.error(response.error, null))
                    }
                }
            }
        }
    }

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>
}