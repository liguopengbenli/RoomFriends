package com.example.friendsjournal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends_table")
data class Friend(
    @PrimaryKey
    @ColumnInfo(name = "friendName") var friendName: String,
    @ColumnInfo(name = "rank") var rank: String
)

{


}