package com.example.cadastroapp

import android.content.Context // Adicione esta importação
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class CadastroFragment : Fragment() {

    private lateinit var listener: CadastroListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Verifica se a atividade implementa a interface CadastroListener
        if (context is CadastroListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement CadastroListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cadastro, container, false)

        // Configurando o Spinner
        val spinner: Spinner = view.findViewById(R.id.spinner_genero)
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.genero_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        // Configurando o botão de cadastrar
        val buttonCadastrar: Button = view.findViewById(R.id.button_cadastrar)
        buttonCadastrar.setOnClickListener {
            val nome = view.findViewById<EditText>(R.id.editTextNome).text.toString()
            val email = view.findViewById<EditText>(R.id.editTextEmail).text.toString()
            val telefone = view.findViewById<EditText>(R.id.editTextTelefone).text.toString()
            val endereco = view.findViewById<EditText>(R.id.editTextEndereco).text.toString()
            val genero = spinner.selectedItem.toString()

            // Verifica se todos os campos foram preenchidos
            if (nome.isNotEmpty() && email.isNotEmpty() && telefone.isNotEmpty() && endereco.isNotEmpty()) {
                val pessoa = Pessoa(nome, email, telefone, endereco, genero)
                listener.onPessoaCadastrada(pessoa) // Adiciona a nova pessoa à lista na MainActivity
                view.findNavController().navigate(R.id.action_cadastroFragment_to_listagemFragment)
            } else {
                // Mostrar um alerta ou mensagem de erro
                Toast.makeText(requireContext(), "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }
}
