package com.example.gamecardexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonOne = findViewById<Button>(R.id.instBtn)
        val buttonPlay = findViewById<Button>(R.id.playBtn)

        buttonPlay.setOnClickListener {
            val intentClick = Intent(this,GameWindow::class.java)
            startActivity(intentClick)
            Log.i("!!!", "button clicked!!")
        }
        buttonOne.setOnClickListener {
            val intent = Intent(this, InstructionsWindow::class.java)
            startActivity(intent)
        }
    }
}