package vcmsa.ci.practicum

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

@Suppress("UNREACHABLE_CODE")
class MainActivity : AppCompatActivity() {

    //Declaring of variables
    private lateinit var btnPlaylist: Button
    private lateinit var btnViewPlaylist: Button
    private val song = mutableListOf<String>()
    private val artist = mutableListOf<String>()
    private val rating = mutableListOf<Int>()
    private val comments = mutableListOf<String>()
    private lateinit var btnExit: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnPlaylist = findViewById(R.id.btnPlaylist)
        btnViewPlaylist = findViewById(R.id.btnViewPlaylist)
        btnExit = findViewById(R.id.btnExit)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Exit button
        btnExit.setOnClickListener {
            finishAffinity()
        }

        btnPlaylist.setOnClickListener {
            showAddSongDialogue()
        }

        btnViewPlaylist.setOnClickListener {
            if (song.isNotEmpty()) {
                val intent = Intent(this, MainActivity3::class.java)
                intent.putStringArrayListExtra("item", ArrayList(song))
                intent.putStringArrayListExtra("category", ArrayList(artist))
                intent.putIntegerArrayListExtra("quantity", ArrayList(rating))
                intent.putStringArrayListExtra("comments", ArrayList(comments))
                startActivity(intent)
            } else {
                Snackbar.make(
                    btnViewPlaylist,
                    "Packing list is empty. Add items first.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }

        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showAddSongDialogue() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Item")

        val view = layoutInflater.inflate(R.layout.activity_main2, null)
        val SongNameEditText: EditText = view.findViewById(R.id.SongNameEditText)
        val ArtistEditText: EditText = view.findViewById(R.id.ArtistEditText)
        val RatingEditText: EditText = view.findViewById(R.id.RatingEditText)
        val CommentsEditText: EditText = view.findViewById(R.id.CommentsEditText)


        builder.setView(view)
        //Declaring the variables that send the songs to the playlist
        builder.setPositiveButton("Add") { dialog, _ ->
            val SongName = SongNameEditText.text.toString().trim()
            val category = ArtistEditText.text.toString().trim()
            val quantityStr = RatingEditText.text.toString().trim()
            val comments = CommentsEditText.text.toString().trim()
            val intent = Intent(this, MainActivity::class.java)

            if (SongName.isEmpty() || category.isEmpty() || quantityStr.isEmpty()) {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Item name, category, and quantity cannot be empty.",
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setPositiveButton

            }
                //Making sure the user cant enter 0
                val quantity = quantityStr.toIntOrNull()
                if (quantity == null || quantity <= 0) {
                    Snackbar.make(
                        findViewById(android.R.id.content),
                        "Invalid rating. Please enter a number greater than zero.",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@setPositiveButton
                }

                song.add(SongName)
                this.artist.add(category)
                this.rating.add(quantity)
                this.comments.add(comments)

                Snackbar.make(
                    findViewById(android.R.id.content),
                    "$SongName added to the Playlist.",
                    Snackbar.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
            }
            builder.show()
    }
}