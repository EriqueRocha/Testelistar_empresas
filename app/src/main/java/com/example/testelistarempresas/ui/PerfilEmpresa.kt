package com.example.testelistarempresas.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.testelistarempresas.data.EmpresaRepository
import com.example.testelistarempresas.data.State
import com.example.testelistarempresas.databinding.ActivityPerfilEmpresaBinding
import com.google.android.material.snackbar.Snackbar

class PerfilEmpresa : AppCompatActivity() {

    private val binding by lazy { ActivityPerfilEmpresaBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<EmpresaStatementViewModel>()

    val empresaId = intent.getIntExtra("EMPRESA_ID", -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (empresaId != -1) {
            empresaInfo()
        } else {

        }

    }

    private fun empresaInfo() {

        viewModel.findEmpresa(empresaId).observe(this){state ->
            when(state){
                is State.Error -> {
                    state.message?.let { Snackbar.make(binding.imageView, it, Snackbar.LENGTH_LONG).show() }
                }
                is State.Success -> {
                    val empresa = state.data
                    binding.nomeEmpresa.text = empresa?.nome
                    binding.descricao.text = empresa?.descricao
                    binding.contato.text = empresa?.contato
                    Glide.with(this).load(empresa?.image).into(binding.imageView)
                }
                else -> {}
            }
        }
    }
}
