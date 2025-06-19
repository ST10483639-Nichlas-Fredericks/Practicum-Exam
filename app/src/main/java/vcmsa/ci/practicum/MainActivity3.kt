package vcmsa.ci.practicum

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.system.exitProcess

class MainActivity3 : AppCompatActivity() {

    //Declaring variables
    private lateinit var song: ArrayList<String>
    private lateinit var artist: ArrayList<String>
    private lateinit var rating: ArrayList<Int>
    private lateinit var comments: ArrayList<String>
    private lateinit var displayPlaylistView:TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main3)

        song = intent.getStringArrayListExtra("item") ?: arrayListOf()
        artist = intent.getStringArrayListExtra("category") ?: arrayListOf()
        rating = intent.getIntegerArrayListExtra("quantity") ?: arrayListOf()
        comments = intent.getStringArrayListExtra("comments") ?: arrayListOf()
        displayPlaylistView = findViewById(R.id.displayPlaylistView)

        val displayAllButton: Button = findViewById(R.id.displayAllButton)
        val displayQuantityButton: Button = findViewById(R.id.displayQuantityButton)
        val btnMain: Button = findViewById(R.id.btnMain)
        val btnExit: Button = findViewById(R.id.btnExit)

        btnExit.setOnClickListener {
            finishAffinity()
            }


        displayAllButton.setOnClickListener {
            displayPackingList()
        }

        displayQuantityButton.setOnClickListener {
            displayItemsWithQuantity()
        }

        btnMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun displayPackingList() {
        val stringBuilder = StringBuilder()
        if (song.isNotEmpty()) {
            for (i in song.indices) {
                stringBuilder.append("Item: ${song[i]}\n")
                stringBuilder.append("Category: ${artist[i]}\n")
                stringBuilder.append("Quantity: ${rating[i]}\n")
                stringBuilder.append("Comments: ${comments[i]}\n\n")
            }
            displayPlaylistView.text = stringBuilder.toString()
        } else {
            displayPlaylistView.text = "Packing list is empty."
        }
    }

    //This portion of code alone was helped given by chatGPT
    private fun displayItemsWithQuantity() {
        if (rating.isNotEmpty()) {
            val total = rating.sum()
            val average = total.toDouble() / rating.size
            displayPlaylistView.text = "Average Rating: %.2f".format(average)
        } else {
            displayPlaylistView.text = "No ratings to calculate average."
        }
    }

    }