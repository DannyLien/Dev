package com.hank.dev

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hank.dev.databinding.RowTextViewBinding


class NameAdapter(val names: List<String>) : RecyclerView.Adapter<NameAdapter.NameViewHolder>() {
    class NameViewHolder(var view: RowTextViewBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val binding = RowTextViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return NameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return names.size
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val name = names.get(position)
        holder.view.textView.text = name
    }


}