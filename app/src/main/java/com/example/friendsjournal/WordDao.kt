package com.example.friendsjournal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {

    @Query("SELECT * from friends_table ORDER BY friendName ASC")
    fun getAlphabetizedWords(): LiveData<List<Friend>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(friend: Friend)

    @Query("DELETE FROM friends_table")
    suspend fun deleteAll()

}