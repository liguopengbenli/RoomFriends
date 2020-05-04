package com.example.friendsjournal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.friendsjournal.menu.GoodFriends
import kotlinx.coroutines.launch


class WordViewModel(application: Application) : AndroidViewModel(application) {


    private val repository: WordRepository
    // LiveData gives us updated words when they change.

    val familyWords: LiveData<List<Friend>>
    val bestFriendsWords: LiveData<List<Friend>>
    val goodFriendsWords: LiveData<List<Friend>>
    val normalFriendsWords: LiveData<List<Friend>>
    val acquaintances: LiveData<List<Friend>>

    init {
        val wordsDao = WordRoomDatabase.getDatabase(application, viewModelScope).wordDao()
        repository = WordRepository(wordsDao)
        familyWords = repository.familyWords
        bestFriendsWords = repository.bestFriendsWords
        goodFriendsWords = repository.goodFriendsWords
        normalFriendsWords = repository.normalFriendsWords
        acquaintances = repository.acquaintancesWords
    }

    /**
     * The implementation of insert() in the database is completely hidden from the UI.
     * Room ensures that you're not doing any long running operations on
     * the main thread, blocking the UI, so we don't need to handle changing Dispatchers.
     * ViewModels have a coroutine scope based on their lifecycle called
     * viewModelScope which we can use here.
     */
    fun insert(word: Friend) = viewModelScope.launch {
        repository.insert(word)
    }

    fun update(name: String, newName: String, newRank : String) {
        repository.update(name, newName, newRank)
    }



}