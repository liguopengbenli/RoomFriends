package com.example.friendsjournal

import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class WordRepository(private val wordDao: WordDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val familyWords: LiveData<List<Friend>>  = wordDao.getFamilyWords()
    val bestFriendsWords: LiveData<List<Friend>> = wordDao.getBestFriendsWords()
    val goodFriendsWords: LiveData<List<Friend>> = wordDao.getGoodFriendsWords()
    val normalFriendsWords: LiveData<List<Friend>> = wordDao.getNormalFriendsWords()
    val acquaintancesWords: LiveData<List<Friend>> = wordDao.getAcquaintancesWords()


    suspend fun insert(word: Friend) {
        wordDao.insert(word)
    }

    fun update(name: String, newName: String, newRank : String) {
        wordDao.updateFriend(name, newName, newRank)
    }


}