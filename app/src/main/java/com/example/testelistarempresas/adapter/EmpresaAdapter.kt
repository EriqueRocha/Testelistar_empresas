package com.example.testelistarempresas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testelistarempresas.data.domain.ListEmpresa
import com.example.testelistarempresas.databinding.MainItemBinding

class EmpresaAdapter(private val dataSet: List<ListEmpresa>, val onClickListener: (Int) -> Unit = {}) : RecyclerView.Adapter<EmpresaAdapter.ViewHolder>()  {

    class ViewHolder(private val binding: MainItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: ListEmpresa, position: Int) = with(binding){

            Glide.with(root.context)
                .load(item.image)
                .centerCrop()
                .into(imgThumb)
            nomeEmpresa.text = item.nome
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MainItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = dataSet[position] //posição do item
        val adjustedId = item.id - 1
        viewHolder.bind(item, position) //passo a posição do item para o ViewHolder

        //val card: LinearLayout = viewHolder.itemView.findViewById(R.id.item_color_container)

        viewHolder.itemView.setOnClickListener{
            onClickListener(adjustedId)
        }


    }

    override fun getItemCount()= dataSet.size

}
