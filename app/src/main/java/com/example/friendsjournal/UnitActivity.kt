package com.example.friendsjournal

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.friendsjournal.R.*
import kotlinx.android.synthetic.main.activity_unit.*

class UnitActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{
    private lateinit var wordViewModel: WordViewModel
    private lateinit var name: String
    private lateinit var rank: String
    private val items = arrayOf("Select your level friendship", Rank.Acquaintances.toString(), Rank.NormalFriends.toString(), Rank.GoodFriends.toString(), Rank.BestFriends.toString(), Rank.Family.toString())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_unit)
        wordViewModel = ViewModelProvider(this).get(WordViewModel::class.java)

        displayInfo()
        rank = intent.getStringExtra(RANK)
        mapRankColor(rank)

        val button = findViewById<Button>(R.id.button_save_unit)
        val editWordTitle: EditText = findViewById(R.id.unitTitle)

        val dropdown: Spinner = findViewById(R.id.rankSpinner)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        dropdown.adapter = adapter
        dropdown.onItemSelectedListener = this


        button.setOnClickListener{
            if (!TextUtils.isEmpty(editWordTitle.text)) {
                val newFriendName = editWordTitle.text.toString()
                val newPhone = phoneTitle.text.toString()
                val newAddress = addressTitle.text.toString()
                val newWeChat = weChatTitle.text.toString()
                val newFB = fbTitle.text.toString()
                val newBirthday = birthdayTitle.text.toString()
                val newNote = noteTitle.text.toString()

                wordViewModel.updateName(name, newFriendName)
                wordViewModel.updateContact(name, newPhone, newAddress, newWeChat, newFB)
                wordViewModel.updateInfo(name, newBirthday, newNote)
            }
        }
    }

    private fun mapRankColor(rank: String){
        when (rank){
            Rank.Acquaintances.toString() -> unitTitle.setBackgroundColor(resources.getColor(R.color.Acquaintances, null))
            Rank.NormalFriends.toString() -> unitTitle.setBackgroundColor(resources.getColor(R.color.NormalFriends, null))
            Rank.GoodFriends.toString()   -> unitTitle.setBackgroundColor(resources.getColor(R.color.GoodFriends, null))
            Rank.BestFriends.toString()   -> unitTitle.setBackgroundColor(resources.getColor(R.color.BestFriends, null))
            Rank.Family.toString()        -> unitTitle.setBackgroundColor(resources.getColor(R.color.Family, null))
        }
    }

    private fun displayInfo(){
        name = intent.getStringExtra(CLICKNAME)
        unitTitle.setText(name)
        var un = wordViewModel.get(name)
        phoneTitle.setText(un.phoneNumber)
        addressTitle.setText(un.address)
        weChatTitle.setText(un.weChat)
        fbTitle.setText(un.fb)
        birthdayTitle.setText(un.birthday)
        noteTitle.setText(un.note)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(position > 0){
            rank = items[position]
            wordViewModel.updateRank(name, rank)
        }else{
            // show toast select gender
        }
    }


}