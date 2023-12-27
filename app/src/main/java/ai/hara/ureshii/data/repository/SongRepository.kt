package ai.hara.ureshii.data.repository


import ai.hara.ureshii.data.model.Song
import ai.hara.ureshii.data.service.SongService
import ai.hara.ureshii.util.network.NetworkHelper
import ai.hara.ureshii.util.network.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SongRepository(
    private val service: SongService,
    private val networkHelper: NetworkHelper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
//
//    fun likeSong(songId: Int): LiveData<Resource<SearchResult>> {
//        return object : NetworkBoundResource<SearchResult, SearchResult>(executor) {
//            override fun createCall(): LiveData<ApiResponse<SearchResult>> {
//                return service.likeSong(
//                    getToken(),
//                    songId,
//                    Any()
//                )
//            }
//        }.asLiveData()
//    }
//
//    fun dislikeSong(songId: Int): LiveData<Resource<SearchResult>> {
//        return object : NetworkBoundResource<SearchResult, SearchResult>(executor) {
//            override fun createCall(): LiveData<ApiResponse<SearchResult>> {
//                return service.dislikeSong(getToken(), getFCMToken(), getUniqueDeviceId(), songId)
//            }
//        }.asLiveData()
//    }
//
//    fun getPublicSongs(userId: Int): LiveData<Resource<List<Song>>> {
//        return object : NetworkBoundResource<List<Song>, List<Song>>(executor) {
//            override fun createCall(): LiveData<ApiResponse<List<Song>>> {
//                return service.getPublicSongs(
//                    getToken(),
//                    getFCMToken(),
//                    getUniqueDeviceId(),
//                    userId
//                )
//            }
//        }.asLiveData()
//    }

    suspend fun getSongs(): ResultWrapper<List<Song>> {
        return networkHelper.safeApiCall(dispatcher) {
            service.getSongs()
        }
    }
}
