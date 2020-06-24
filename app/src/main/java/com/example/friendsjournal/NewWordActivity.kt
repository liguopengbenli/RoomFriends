package com.example.friendsjournal

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity


enum class Rank(){
    Family,
    BestFriends,
    GoodFriends,
    NormalFriends,
    Acquaintances
}

private const val LOG_TAG = "NewWordActivity"


class NewWordActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{
    private var rank = Rank.Acquaintances.toString()
    private lateinit var editWordView: EditText
    private val items = arrayOf(Rank.Acquaintances.toString(), Rank.NormalFriends.toString(), Rank.GoodFriends.toString(), Rank.BestFriends.toString(), Rank.Family.toString())


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_word)
        editWordView = findViewById(R.id.edit_word)

        val button = findViewById<Button>(R.id.button_save)

        val dropdown: Spinner = findViewById(R.id.spinner_rank)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        dropdown.adapter = adapter
        dropdown.setOnItemSelectedListener(this)

        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editWordView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val friendName = editWordView.text.toString()

                replyIntent.putExtra(EXTRA_NAME, friendName)
                replyIntent.putExtra(EXTRA_RANK, rank)
                setResult(Activity.RESULT_OK, replyIntent)
                Log.d(LOG_TAG,"Add words with friendname = $friendName and  rank = $rank")

            }
            finish()
        }
    }

    companion object {
        const val EXTRA_NAME = "com.android.wordlistsql.friendname"
        const val EXTRA_RANK = "com.android.wordlistsql.RANK"
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        rank = items[position]
    }

}


