package com.hank.dev

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.hank.dev.databinding.RowTextViewBinding
import com.hank.dev.databinding.RowWordBinding

class WordAdapter(var words: List<Word>) : Adapter<WordAdapter.WordViewHolder>() {
    class WordViewHolder(val view: RowWordBinding) : ViewHolder(view.root) {
        init {
            val nameText = view.name
            val diffText = view.diff
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        return WordViewHolder(
            RowWordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = words.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val word = words.get(position)
        holder.view.name.text = word.name
        holder.view.diff.text = word.difficulty.toString()

    }


}