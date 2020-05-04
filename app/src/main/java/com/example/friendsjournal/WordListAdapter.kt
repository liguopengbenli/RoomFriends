package com.example.friendsjournal

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

private const val LOG_TAG = "WordListAdapter"


class WordListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<Friend>()
    private var mPosition:Int = 0

    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val wordItemView: TextView = itemView.findViewById(R.id.textView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
            val current = words[position]
            holder.wordItemView.text = current.friendName

        if (mPosition == position) {
            Log.d(LOG_TAG,"User selects + ${current.friendName}")


        } else {

        }
        holder.wordItemView.setOnClickListener {
            mPosition = position
            notifyDataSetChanged()
        }

    }

    internal fun setWords(words: List<Friend>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size
}