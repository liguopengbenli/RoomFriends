package com.example.friendsjournal

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WordDao {

    //-------------------------read data---------------------------------------//
    @Query("SELECT * from friends_table ORDER BY friendName ASC")
    fun getAlphabetizedWords(): LiveData<List<Friend>>

    @Query("SELECT * from friends_table WHERE rank='BestFriends' ORDER BY friendName ASC ")
    fun getBestFriendsWords(): LiveData<List<Friend>>

    @Query("SELECT * from friends_table WHERE rank='Family' ORDER BY friendName ASC ")
    fun getFamilyWords(): LiveData<List<Friend>>

    @Query("SELECT * from friends_table WHERE rank='GoodFriends' ORDER BY friendName ASC ")
    fun getGoodFriendsWords(): LiveData<List<Friend>>

    @Query("SELECT * from friends_table WHERE rank='NormalFriends' ORDER BY friendName ASC ")
    fun getNormalFriendsWords(): LiveData<List<Friend>>

    @Query("SELECT * from friends_table WHERE rank='Acquaintances' ORDER BY friendName ASC ")
    fun getAcquaintancesWords(): LiveData<List<Friend>>

    //-------------------------update data---------------------------------------//
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(friend: Friend)

    @Query("UPDATE friends_table SET friendName = :newName, rank = :newRank WHERE friendName = :name")
    fun updateFriend(name: String, newName: String, newRank: String)

    @Query("DELETE FROM friends_table")
    suspend fun deleteAll()

    // getFamilyWords
}