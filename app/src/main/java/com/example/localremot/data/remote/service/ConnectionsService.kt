package com.example.localremot.data.remote.service

import com.example.localremot.data.remote.model.ConnectionDTO
import retrofit2.Response
import retrofit2.http.GET

interface ConnectionsService {

    @GET("1775d634-92dc-4c32-ae71-1707b8cfee41")
    suspend fun getConnections() : Response<List<ConnectionDTO>>
}