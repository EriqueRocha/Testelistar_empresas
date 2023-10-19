package com.example.testelistarempresas.ui

import androidx.lifecycle.ViewModel
import com.example.testelistarempresas.data.EmpresaRepository

class EmpresaStatementViewModel: ViewModel(){

    fun findEmpresaStatement(empresaId: Int) = EmpresaRepository.findEmpresaStatement(empresaId)

    fun findEmpresa(empresaId: Int) = EmpresaRepository.findEmpresa(empresaId)

}