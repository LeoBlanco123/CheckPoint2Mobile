package com.example.cadastroapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), CadastroListener {

    private val listaDePessoas = mutableListOf<Pessoa>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPessoaCadastrada(pessoa: Pessoa) {
        listaDePessoas.add(pessoa)
    }

    fun getPessoas(): List<Pessoa> {
        return listaDePessoas
    }
}
