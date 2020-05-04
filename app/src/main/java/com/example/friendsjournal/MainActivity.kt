package com.example.friendsjournal

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.friendsjournal.menu.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Integer.parseInt
import java.lang.Long.parseLong

private const val LOG_TAG = "MainActivity"
const val PREFS_FILENAME = "com.example.friendsjournal.interval.prefs"
const val PREFS_FILEVAL = "interval.value"
lateinit var  intervalNotifPref: SharedPreferences

class MainActivity : AppCompatActivity() {

    private val newWordActivityRequestCode = 1

    private lateinit var wordViewModel: WordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)
        setupButtonMenu()
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewWordActivity::class.java)
            startActivityForResult(intent, newWordActivityRequestCode)
        }

        selectInterval(60*60*1000)

        val button = findViewById<Button>(R.id.button_save_notification)
        button.setOnClickListener {
            if (TextUtils.isEmpty(interval_notification.text)) {
                Toast.makeText(this, "Enter a number of hours for notifying your friends ", Toast.LENGTH_LONG).show();
            }
            else {
                selectInterval(getInterval())
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newWordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewWordActivity.EXTRA_NAME)?.let {
                val friendName = data.getStringExtra(NewWordActivity.EXTRA_NAME)
                val rank = data?.getStringExtra(NewWordActivity.EXTRA_RANK)
                val word = Friend(friendName,rank)
                Log.d(LOG_TAG,"Add words with friendname = $friendName and  rank = $rank")
                wordViewModel.insert(word)
            }
        }

        else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }

    private fun getInterval() : Long {
        val co = 60*60*1000L // 1 hour
        var interval = 1
        try {
            interval = parseInt(interval_notification.text.toString())
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter a number between 0-24 by defaut value set 1 hours", Toast.LENGTH_LONG).show();
            return 1*co
        }
        if (interval<1 || interval>24){
            Toast.makeText(this, "Please enter a number between 0-24 by defaut value set 1 hours", Toast.LENGTH_LONG).show();
            interval = 1
        }else{
            Toast.makeText(this, "Set interval = $interval hours", Toast.LENGTH_LONG).show();
        }
        return interval*co
    }

    private fun selectInterval(value: Long) {
        intervalNotifPref = this.getSharedPreferences(PREFS_FILENAME, 0);
        val editor = intervalNotifPref.edit()
        editor.putLong(PREFS_FILEVAL, value)
        editor.commit()
    }

    private fun setupButtonMenu(){
        val fab1 = findViewById<Button>(R.id.button_Family)
        fab1.setOnClickListener {
            val intent = Intent(this@MainActivity, Family::class.java)
            startActivity(intent)
        }

        val fab2 = findViewById<Button>(R.id.button_BestFriends)
        fab2.setOnClickListener {
            val intent = Intent(this@MainActivity, BestFriends::class.java)
            startActivity(intent)
        }

        val fab3 = findViewById<Button>(R.id.button_GoodFriends)
        fab3.setOnClickListener {
            val intent = Intent(this@MainActivity, GoodFriends::class.java)
            startActivity(intent)
        }

        val fab4 = findViewById<Button>(R.id.button_NormalFriends)
        fab4.setOnClickListener {
            val intent = Intent(this@MainActivity, NormalFriends::class.java)
            startActivity(intent)
        }

        val fab5 = findViewById<Button>(R.id.button_Acquaintances)
        fab5.setOnClickListener {
            val intent = Intent(this@MainActivity, Acquaintances::class.java)
            startActivity(intent)
        }
    }
}
