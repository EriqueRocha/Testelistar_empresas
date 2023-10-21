package com.example.testelistarempresas.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testelistarempresas.adapter.EmpresaAdapter
import com.example.testelistarempresas.data.State
import com.example.testelistarempresas.data.domain.ListEmpresa
import com.example.testelistarempresas.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<EmpresaStatementViewModel>()

    private val dataSet = mutableListOf<ListEmpresa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rvMain.layoutManager = GridLayoutManager(this,2)

        binding.rvMain.adapter = EmpresaAdapter(dataSet) { position ->
            val empresa = dataSet[position]
            val intent = Intent(this@MainActivity, PerfilEmpresa::class.java)
            intent.putExtra("EMPRESA_ID", empresa.id)
            Log.d("APIResponse", "Response: ${empresa}")
            startActivity(intent)
        }

        binding.srlListEmpresa.setOnRefreshListener { findListEmpresa() }

        findListEmpresa()

    }

    private fun findListEmpresa() {

        viewModel.findEmpresaStatement(binding.rvMain.id).observe(this){state ->
            when(state){
                is State.Error -> {
                    state.message?.let { Snackbar.make(binding.rvMain, it, Snackbar.LENGTH_LONG).show() }
                    binding.srlListEmpresa.isRefreshing = false
                }
                is State.Success -> {

                    dataSet.clear() //limpa antes de adicionar novamente para evitar repetição

                    state.data?.let { dataSet.addAll(it)}

                    binding.rvMain.adapter?.notifyDataSetChanged()

                    binding.srlListEmpresa.isRefreshing = false

                }

                State.Wait -> binding.srlListEmpresa.isRefreshing = true
            }

        }
    }
}