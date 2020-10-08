package com.example.friendsjournal

import android.util.Log
import androidx.lifecycle.LiveData

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
private const val LOG_TAG = "WordRepository"

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

    suspend fun updateName(name: String, newName: String) {
        Log.d(LOG_TAG,"update Friend $newName")
        wordDao.updateName(name, newName)
    }

    suspend fun updateContact(name: String, newPhone: String, newAddress: String, newWechat: String, newFB: String) {
        wordDao.updateContact(name, newPhone, newAddress, newWechat, newFB)
    }

    suspend fun updateInfo(name: String, newBirthday: String, newNote: String) {
        wordDao.updateInfo(name, newBirthday, newNote)
    }

    suspend fun updateRank(name: String, newRank: String){
        wordDao.updateRank(name, newRank)
    }

    suspend fun get(name: String): Friend{
        return wordDao.getWord(name)
    }

    suspend fun deleteUnit(name: String){
        return wordDao.deleteByName(name)
    }


}