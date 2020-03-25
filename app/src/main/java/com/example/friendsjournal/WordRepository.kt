package com.example.friendsjournal

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class WordRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allWords: LiveData<List<Friend>> = wordDao.getAlphabetizedWords()
    suspend fun insert(word: Friend) {
        wordDao.insert(word)
    }


}