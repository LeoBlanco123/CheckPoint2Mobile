package com.example.cadastroapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PessoaAdapter(
    private val listaDePessoas: List<Pessoa>,
    private val onEdit: (Int) -> Unit,
    private val onDelete: (Int) -> Unit
) : RecyclerView.Adapter<PessoaAdapter.PessoaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PessoaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pessoa, parent, false)
        return PessoaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PessoaViewHolder, position: Int) {
        val pessoa = listaDePessoas[position]
        holder.nomeTextView.text = pessoa.nome
        holder.emailTextView.text = pessoa.email
        holder.telefoneTextView.text = pessoa.telefone
        holder.enderecoTextView.text = pessoa.endereco
        holder.generoTextView.text = pessoa.genero

        holder.itemView.findViewById<Button>(R.id.btn_editar).setOnClickListener {
            onEdit(position)
        }

        holder.itemView.findViewById<Button>(R.id.btn_deletar).setOnClickListener {
            onDelete(position)
        }
    }

    override fun getItemCount(): Int = listaDePessoas.size

    class PessoaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nomeTextView: TextView = itemView.findViewById(R.id.nomeTextView)
        val emailTextView: TextView = itemView.findViewById(R.id.emailTextView)
        val telefoneTextView: TextView = itemView.findViewById(R.id.telefoneTextView)
        val enderecoTextView: TextView = itemView.findViewById(R.id.enderecoTextView)
        val generoTextView: TextView = itemView.findViewById(R.id.generoTextView)
    }
}
