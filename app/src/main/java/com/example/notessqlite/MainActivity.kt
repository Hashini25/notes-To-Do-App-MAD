package com.example.notessqlite

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notessqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NotesDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Using view binding to inflate the layout
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing the database helper
        db = NotesDatabaseHelper(this)

        // Creating a new NotesAdapter with the list of notes from the database
        notesAdapter = NotesAdapter(db.getAllNotes(), this)

        // Setting up the RecyclerView
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter

        // Setting a click listener for the add button
        binding.addButton.setOnClickListener {
            // Creating an intent to start the AddNoteActivity
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refreshing the data in the adapter when the activity is resumed
        notesAdapter.refreshData(db.getAllNotes())
    }
}
