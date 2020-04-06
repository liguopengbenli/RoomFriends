package com.example.friendsjournal.menu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.friendsjournal.R
import com.example.friendsjournal.WordListAdapter
import com.example.friendsjournal.WordViewModel


private const val LOG_TAG = "BestFriends"


class BestFriends : AppCompatActivity() {

    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bestfriends)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview1)
        val adapter = WordListAdapter(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        wordViewModel.allWords.observe(this, Observer { words ->
            // Update the cached copy of the words in the adapter.
            words?.let {
                adapter.setWords(it)
                Log.d(LOG_TAG,"Set word UI = $it")
            }
        })

    }

}
