package com.example.gamecardexam

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import java.util.Collections.addAll


private const val TAG = "MainActivity"
class GameWindow : AppCompatActivity() {

    private lateinit var buttons: List<ImageButton>
    private lateinit var cards: List<MemoryCard>
    private var indexOfSingleSelectedCard: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonLoseWin = findViewById<Button>(R.id.lostWinBtn)
        buttonLoseWin.setOnClickListener {
            val intentThree = Intent(this, MainActivity::class.java)
            startActivity(intentThree)
        }

        val images = mutableListOf(R.drawable.ic_reddit, R.drawable.ic_bug, R.drawable.ic_point,
            R.drawable.ic_smiley, R.drawable.ic_gps, R.drawable.ic_moon)
        // Add each image twice so we can create pairs
        images.addAll(images)
        // Randomize the order of images
        images.shuffle()

        val btnOne = findViewById<ImageButton>(R.id.imageButton1)
        val btnTwo = findViewById<ImageButton>(R.id.imageButton2)
        val btnThree = findViewById<ImageButton>(R.id.imageButton3)
        val btnFour = findViewById<ImageButton>(R.id.imageButton4)
        val btnFive = findViewById<ImageButton>(R.id.imageButton5)
        val btnSix = findViewById<ImageButton>(R.id.imageButton6)
        val btnSeven = findViewById<ImageButton>(R.id.imageButton7)
        val btnEight = findViewById<ImageButton>(R.id.imageButton8)
        val btnNine = findViewById<ImageButton>(R.id.imageButton9)
        val btnTen = findViewById<ImageButton>(R.id.imageButton10)
        val btnEleven = findViewById<ImageButton>(R.id.imageButton11)
        val btnTwelve = findViewById<ImageButton>(R.id.imageButton12)

        buttons = listOf(btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight,
            btnNine, btnTen, btnEleven, btnTwelve)

        cards = buttons.indices.map { index ->
            MemoryCard(images[index])
        }

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {
                Log.i(TAG, "button clicked!!")
                // Update models
                updateModels(index)
                // Update the UI for the game
                updateViews()
            }
        }
    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            if (card.isMatched) {
                button.alpha = 0.1f
            }
            button.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.ic_android_black)
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]
        // Error checking:
        if (card.isFaceUp) {
            Toast.makeText(this, "Invalid move!", Toast.LENGTH_SHORT).show()
            return
        }
        // Three cases
        // 0 cards previously flipped over => restore cards + flip over the selected card
        // 1 card previously flipped over => flip over the selected card + check if the images match
        // 2 cards previously flipped over => restore cards + flip over the selected card
        if (indexOfSingleSelectedCard == null) {
            // 0 or 2 selected cards previously
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            // exactly 1 card was selected previously
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        if (cards[position1].identifier == cards[position2].identifier) {
            Toast.makeText(this, "Match found!!", Toast.LENGTH_SHORT).show()
            cards[position1].isMatched = true
            cards[position2].isMatched = true
        }
    }
}