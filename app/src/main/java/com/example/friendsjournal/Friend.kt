package com.example.friendsjournal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends_table")
data class Friend(
    @PrimaryKey
    @ColumnInfo(name = "friendName") var friendName: String,
    @ColumnInfo(name = "rank") var rank: String,
    @ColumnInfo(name = "phoneNumber") var phoneNumber: String = "",
    @ColumnInfo(name = "birthday") var birthday: String = "",
    @ColumnInfo(name = "address") var address: String = "",
    @ColumnInfo(name = "weChat") var weChat: String = "",
    @ColumnInfo(name = "fb") var fb: String = "",
    @ColumnInfo(name = "note") var note: String = ""
    )

{


}