package com.example.localremot.data.remote.service

import com.example.localremot.data.remote.model.ConnectionDTO
import retrofit2.Response
import retrofit2.http.GET

interface ConnectionsService {

    @GET("df8d4951-2757-45aa-8f60-bf1592a090ce")
    suspend fun getConnections() : Response<List<ConnectionDTO>>
}