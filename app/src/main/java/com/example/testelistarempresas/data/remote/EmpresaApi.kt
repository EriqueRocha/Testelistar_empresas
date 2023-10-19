package com.example.testelistarempresas.data.remote

import com.example.testelistarempresas.data.domain.ListEmpresa
import com.example.testelistarempresas.data.domain.PerfilEmpresaData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface EmpresaApi {

    @GET("imagens-perfil.json")
    suspend fun findEmpresaStatement(@Query("id") empresaHolderId: Int): List<ListEmpresa>

    @GET("perfil/{id}.json")
    suspend fun findEmpresa(@Query("id") empresaId: Int): PerfilEmpresaData

}