package com.example.testelistarempresas.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.testelistarempresas.data.remote.EmpresaApi
import com.example.testelistarempresas.databinding.ActivityPerfilEmpresaBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PerfilEmpresa : AppCompatActivity() {
    private val binding by lazy { ActivityPerfilEmpresaBinding.inflate(layoutInflater) }
    private var empresaId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // Obter empresaId da Intent
        empresaId = intent.getIntExtra("EMPRESA_ID", -1)

        //empresaId -= 1

        if (empresaId != -1) {
            empresaInfo(empresaId)
        } else {
            // Lógica adicional, se necessário
        }
    }

    private fun empresaInfo(empresaId: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://eriquerocha.github.io/Lista/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(EmpresaApi::class.java)

        binding.progressBar.visibility = View.VISIBLE

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val empresa = service.findEmpresa(empresaId)

                withContext(Dispatchers.Main) {
                    Glide.with(this@PerfilEmpresa)
                        .load(empresa.image)
                        .into(binding.imageView)

                    binding.nomeEmpresa.text = empresa.nome
                    binding.descricao.text = empresa.descricao
                    binding.contato.text = empresa.contato

                    binding.progressBar.visibility = View.INVISIBLE
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.INVISIBLE
                    // Trate o erro aqui, se necessário
                }
            }
        }
    }
}


