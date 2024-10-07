package com.example.cadastroapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ListagemFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PessoaAdapter
    private val listaDePessoas = mutableListOf<Pessoa>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_listagem, container, false)

        // Inicializar RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Obter a lista de pessoas da MainActivity
        val mainActivity = activity as MainActivity
        listaDePessoas.addAll(mainActivity.getPessoas())

        // Configurar o adapter
        adapter = PessoaAdapter(listaDePessoas, ::editarPessoa, ::deletarPessoa)
        recyclerView.adapter = adapter

        return view
    }

    private fun editarPessoa(posicao: Int) {
        val pessoaAtual = listaDePessoas[posicao]
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.editar))

        // Cria um layout para o diálogo
        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL

        // Campos de entrada
        val nomeInput = EditText(requireContext())
        nomeInput.setText(pessoaAtual.nome)
        nomeInput.hint = getString(R.string.hint_nome)

        val emailInput = EditText(requireContext())
        emailInput.setText(pessoaAtual.email)
        emailInput.hint = getString(R.string.hint_email)

        val telefoneInput = EditText(requireContext())
        telefoneInput.setText(pessoaAtual.telefone)
        telefoneInput.hint = getString(R.string.hint_telefone)

        val enderecoInput = EditText(requireContext())
        enderecoInput.setText(pessoaAtual.endereco)
        enderecoInput.hint = getString(R.string.hint_endereco)

        // Spinner para o gênero
        val opcoesGenero = resources.getStringArray(R.array.genero_array)
        val generoSpinner = Spinner(requireContext())
        val adapterGenero = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcoesGenero)
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        generoSpinner.adapter = adapterGenero

        // Seleciona o gênero atual
        val posicaoGenero = if (pessoaAtual.genero == getString(R.string.masculino)) 0 else 1
        generoSpinner.setSelection(posicaoGenero)


        // Adiciona os campos ao layout
        layout.addView(nomeInput)
        layout.addView(emailInput)
        layout.addView(telefoneInput)
        layout.addView(enderecoInput)
        layout.addView(generoSpinner)

        builder.setView(layout)

        builder.setPositiveButton(getString(R.string.salvar)) { dialog, _ ->
            val novoNome = nomeInput.text.toString()
            val novoEmail = emailInput.text.toString()
            val novoTelefone = telefoneInput.text.toString()
            val novoEndereco = enderecoInput.text.toString()
            val novoGenero = generoSpinner.selectedItem.toString()

            // Verifica se os campos não estão vazios
            if (novoNome.isNotEmpty() && novoEmail.isNotEmpty() && novoTelefone.isNotEmpty() &&
                novoEndereco.isNotEmpty()) {
                listaDePessoas[posicao] = pessoaAtual.copy(
                    nome = novoNome,
                    email = novoEmail,
                    telefone = novoTelefone,
                    endereco = novoEndereco,
                    genero = novoGenero
                ) // Atualiza todos os atributos
                adapter.notifyItemChanged(posicao)
                Toast.makeText(requireContext(), getString(R.string.pessoa_atualizada), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), getString(R.string.erro_campos_vazios), Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton(getString(R.string.cancelar)) { dialog, _ -> dialog.cancel() }
        builder.show()
    }



    private fun deletarPessoa(posicao: Int) {
        // Confirmar a exclusão
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Deletar Pessoa")
        builder.setMessage("Tem certeza que deseja deletar esta pessoa?")

        // Configurar botões
        builder.setPositiveButton("Sim") { dialog, _ ->
            listaDePessoas.removeAt(posicao)  // Remover a pessoa da lista
            adapter.notifyItemRemoved(posicao)  // Notificar a RecyclerView que o item foi removido
            Toast.makeText(requireContext(), "Pessoa deletada", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        builder.setNegativeButton("Não") { dialog, _ -> dialog.cancel() }
        builder.show()
    }
}
