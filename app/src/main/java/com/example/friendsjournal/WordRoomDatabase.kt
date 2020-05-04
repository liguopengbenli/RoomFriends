package com.example.friendsjournal

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Friend::class), version = 3, exportSchema = false)
public  abstract class WordRoomDatabase : RoomDatabase() {

    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    var wordDao = database.wordDao()
                    //Delete all content here.
                    //wordDao.deleteAll()
                    //Add sample words.
                    var word = Friend("ZhangTuNan", Rank.BestFriends.toString())
                    wordDao.insert(word)
                    word = Friend("MUYE", Rank.BestFriends.toString())
                    wordDao.insert(word)
                    word = Friend("ZhaoYang", Rank.Family.toString())
                    wordDao.insert(word)
                    word = Friend("bob", Rank.GoodFriends.toString())
                    wordDao.insert(word)
                    word = Friend("WangYang", Rank.BestFriends.toString())
                    wordDao.insert(word)
                    word = Friend("ll", Rank.NormalFriends.toString())
                    wordDao.insert(word)
                    word = Friend("sorenza", Rank.Acquaintances.toString())
                    wordDao.insert(word)
                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: WordRoomDatabase? = null
        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): WordRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WordRoomDatabase::class.java,
                    "word_database"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
