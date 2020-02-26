package com.example.kotlinfirebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var editTextName: EditText
    lateinit var ratingBar: RatingBar
    lateinit var buttonSave: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editText2)
        ratingBar = findViewById(R.id.ratingBar)
        buttonSave = findViewById(R.id.button)

        //adding an event listener to our button
        buttonSave.setOnClickListener{
            saveHero()
        }
    }
    //create a function saveHero
    private fun saveHero(){

        val name = editTextName.text.toString().trim()

        // test if the text box is empty and throw an error
        if (name.isEmpty()){
            editTextName.error = "Please Enter Name"
            return
        }
        //create a database reference object since we are storing a hero we will pass heroes to create a new node inside the firebase database

        val ref = FirebaseDatabase.getInstance().getReference("heroes")

        //hero id create using a push method to generate a unique key inside the node "heroes"
        //so that every time we call push will create a new unique key

        val heroId = ref.push().key

        //now create hero object
        val hero = Hero(heroId, name, ratingBar.numStars)

        //create a new child from hero so we can store the new hero child inside the

        ref.child(heroId.toString()).setValue(hero).addOnCompleteListener {
            Toast.makeText(applicationContext, "Hero Saved Successfully", Toast.LENGTH_LONG).show()
        }
    }
}
