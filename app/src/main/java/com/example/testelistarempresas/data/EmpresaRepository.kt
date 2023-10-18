package com.example.testelistarempresas.data

import android.util.Log
import androidx.lifecycle.liveData
import com.example.testelistarempresas.data.domain.PerfilEmpresaData
import com.example.testelistarempresas.data.remote.EmpresaApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object EmpresaRepository {

    private val TAG = javaClass.simpleName

    private val restApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://eriquerocha.github.io/Lista/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmpresaApi::class.java)

    }

    fun findEmpresaStatement(empresaId: Int) = liveData{
        emit(State.Wait)
        try {
            emit(State.Success(data = restApi.findEmpresaStatement(empresaId)))
        }catch (e: Exception){
            Log.e(TAG, e.message, e)
            emit(State.Error(e.message))
        }
    }

    fun findEmpresas(empresaId: Int) = liveData{
        emit(State.Wait)
        try {
            emit(State.Success(data = restApi.findEmpresas(empresaId)))
        }catch (e: Exception){
            Log.e(TAG, e.message, e)
            emit(State.Error(e.message))
        }
    }
}