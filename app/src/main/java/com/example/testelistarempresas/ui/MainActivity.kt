package com.example.testelistarempresas.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.testelistarempresas.adapter.EmpresaAdapter
import com.example.testelistarempresas.data.State
import com.example.testelistarempresas.data.domain.ListEmpresa
import com.example.testelistarempresas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val viewModel by viewModels<EmpresaStatementViewModel>()

    private var dataSet = mutableListOf<ListEmpresa>()

    private var originalDataSet = mutableListOf<ListEmpresa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val pesquisaEditText = binding.pesquisa.editText

        pesquisaEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.isNotEmpty() && s.toString().endsWith("\n")) {
                    applyFilter(s.toString().trim())
                }
            }
        })

        binding.rvMain.layoutManager = GridLayoutManager(this,2)
        binding.rvMain.adapter = EmpresaAdapter(dataSet) { position ->
            var empresa = dataSet[position]
            var intent = Intent(this@MainActivity, PerfilEmpresa::class.java)
            intent.putExtra("EMPRESA_ID", empresa.id)
            Log.d("EmpresaId", "ID da Empresa: ${empresa.id}")
            startActivity(intent)
        }

        binding.srlListEmpresa.setOnRefreshListener { applyFilter(binding.pesquisa.editText?.text.toString()) }
        buscar()

        findListEmpresa()
    }

    private fun findListEmpresa() {
        viewModel.findEmpresaStatement(binding.rvMain.id).observe(this) { state ->
            when (state) {
                is State.Success -> {
                    dataSet.clear()
                    state.data?.let {
                        originalDataSet.clear()
                        originalDataSet.addAll(it)
                    }
                    applyFilter(binding.pesquisa.editText?.text.toString())

                    binding.rvMain.adapter?.notifyDataSetChanged()
                    // binding.rvMain.scrollToPosition(0)
                    binding.srlListEmpresa.isRefreshing = false

                }
                else -> {}
            }
        }
    }
    private fun applyFilter(query: String) {
        binding.srlListEmpresa.isRefreshing = false
        val filteredList = originalDataSet
            .filter { it.nome.contains(query, ignoreCase = true) }
        dataSet.clear()
        dataSet.addAll(filteredList)
        binding.rvMain.adapter?.notifyDataSetChanged()
    }

    private fun buscar() {
        binding.button.setOnClickListener {
            applyFilter(binding.pesquisa.editText?.text.toString())
        }
    }
}