package com.example.testelistarempresas.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.testelistarempresas.data.EmpresaRepository
import com.example.testelistarempresas.data.State
import com.example.testelistarempresas.databinding.ActivityPerfilEmpresaBinding

class PerfilEmpresa : AppCompatActivity() {

    private val binding by lazy { ActivityPerfilEmpresaBinding.inflate(layoutInflater) }

    val empresaId = intent.getIntExtra("EMPRESA_ID", -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        filtroList()

    }

    private fun filtroList() {
        val empresaLiveData = EmpresaRepository.findEmpresas(empresaId)
        empresaLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Success -> {
                    val empresasList = state.data
                    if (empresasList != null && empresasList.isNotEmpty()) {
                        val empresaData = empresasList.firstOrNull { it.id == empresaId }
                        if (empresaData != null) {

                            Glide.with(binding.root.context)
                                .load(empresaData.image)
                                .centerCrop()
                                .into(binding.imageView)

                            binding.nomeEmpresa.text = empresaData.nome
                            binding.descricao.text = empresaData.descricao
                            binding.contato.text = empresaData.contato


                        } else {

                        }
                    } else {

                    }
                }
                is State.Error -> {

                    state.message?.let {

                    }
                }
                is State.Wait -> {

                }
            }
        })
    }
}