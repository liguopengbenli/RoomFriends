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

    @Query("SELECT * from friends_table WHERE friendName= :name")
    suspend fun getWord(name:String): Friend

    //-------------------------update data---------------------------------------//
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(friend: Friend)

    @Query("UPDATE friends_table SET friendName = :newName WHERE friendName = :name")
    suspend fun updateName(name: String, newName: String)

    @Query("UPDATE friends_table SET phoneNumber = :newPhone, address = :newAddress, weChat = :newWechat, fb = :newFB WHERE friendName = :name")
    suspend fun updateContact(name: String, newPhone: String, newAddress: String, newWechat: String, newFB: String)

    @Query("UPDATE friends_table SET birthday = :newBirthday, note = :newNote WHERE friendName = :name")
    suspend fun updateInfo(name: String, newBirthday: String, newNote: String)

    @Query("UPDATE friends_table SET rank = :newRank WHERE friendName = :name")
    suspend fun updateRank(name: String, newRank: String)

    @Query("DELETE FROM friends_table")
    suspend fun deleteAll()

    // getFamilyWords
}