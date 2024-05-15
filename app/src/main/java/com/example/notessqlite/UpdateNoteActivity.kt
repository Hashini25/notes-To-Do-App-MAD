package com.example.notessqlite

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.notessqlite.databinding.ActivityUpdateNoteBinding

class UpdateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateNoteBinding
    private lateinit var db: NotesDatabaseHelper
    private var noteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Using view binding to inflate the layout
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing the database helper
        db = NotesDatabaseHelper(this)

        // Getting the note ID from the intent
        noteId = intent.getIntExtra("note_Id", -1)
        if (noteId == -1) {
            // If the note ID is invalid, finish the activity
            finish()
            return
        }

        // Getting the note from the database using the note ID
        val note = db.getNoteById(noteId)

        // Populating the EditText fields with the note's title and content
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)

        // Setting a click listener for the save button
        binding.updateSaveButton.setOnClickListener {
            // Getting the new title and content from the EditText fields
            val newTitle = binding.updateTitleEditText.text.toString()
            val newContent = binding.updateContentEditText.text.toString()

            // Creating a new Note object with the updated title and content
            val updatedNote = Note(noteId, newTitle, newContent)

            // Updating the note in the database
            db.updateNote(updatedNote)

            // Finishing the activity and displaying a toast message
            finish()
            Toast.makeText(this, "Changes Saved", Toast.LENGTH_SHORT).show()
        }
    }
}
