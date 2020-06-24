package com.example.friendsjournal

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kotlin.Unit

private const val LOG_TAG = "WordListAdapter"
const val CLICKNAME = "name"
const val RANK = "rank"

class WordListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<WordListAdapter.WordViewHolder>(){
    private val adapterContext = context
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
            mapRankColor(current.rank, holder)
            holder.wordItemView.text = current.friendName

        if (mPosition == position) {
            Log.d(LOG_TAG,"User selects + ${current.friendName}")
        }

        holder.wordItemView.setOnClickListener {
            mPosition = position
            val intent = Intent(adapterContext, UnitActivity::class.java)
            intent.putExtra(CLICKNAME, current.friendName)
            intent.putExtra(RANK, current.rank)
            startActivity(adapterContext, intent,null)
            notifyDataSetChanged()
        }
    }

    internal fun setWords(words: List<Friend>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size

    private fun mapRankColor(rank: String, holder: WordViewHolder){
        when (rank){
            Rank.Acquaintances.toString() -> holder.wordItemView.setBackgroundColor(adapterContext.resources.getColor(R.color.Acquaintances, null))
            Rank.NormalFriends.toString() -> holder.wordItemView.setBackgroundColor(adapterContext.resources.getColor(R.color.NormalFriends, null))
            Rank.GoodFriends.toString()   -> holder.wordItemView.setBackgroundColor(adapterContext.resources.getColor(R.color.GoodFriends, null))
            Rank.BestFriends.toString()   -> holder.wordItemView.setBackgroundColor(adapterContext.resources.getColor(R.color.BestFriends, null))
            Rank.Family.toString()        -> holder.wordItemView.setBackgroundColor(adapterContext.resources.getColor(R.color.Family, null))
        }
    }
}