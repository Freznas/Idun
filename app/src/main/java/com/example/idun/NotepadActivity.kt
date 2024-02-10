package com.example.idun

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.idun.databinding.ActivityNotepadBinding

class NotepadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotepadBinding
    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var adapter: NotesAdapter
    private lateinit var listView: ListView
    lateinit var saveButton: Button
    lateinit var deleteButton: Button
    private lateinit var notepadDataManager: NotepadDataManager
    private var editNotePosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotepadBinding.inflate(layoutInflater)

        setContentView(binding.root)
        notepadDataManager = NotepadDataManager(this)

        adapter = NotesAdapter(this, notepadDataManager.getNotes(), notepadDataManager)
        titleEditText = findViewById(R.id.et_titlenote)
        contentEditText = findViewById(R.id.et_contentnote)
        saveButton = findViewById(R.id.btn_addNote)
        listView = findViewById(R.id.lv_notesList)
        deleteButton = findViewById(R.id.btn_deleteNote)
        val notes: List<String> = notepadDataManager.getNotes()

        val savedTitle: String = notepadDataManager.getSavedTitle()
        val savedContent: String = notepadDataManager.getSavedContent()

        titleEditText.setText(savedTitle)
        contentEditText.setText(savedContent)


        editNotePosition = intent.getIntExtra("edit_note_position", -1)
        if (editNotePosition != -1) // Hämta positionen för anteckningen att redigera
        {
            val noteToEdit: String = notepadDataManager.getNoteByPosition(editNotePosition)
            if (noteToEdit != null) {
                val noteParts = noteToEdit.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray()
                if (noteParts.size == 2) {
                    titleEditText.setText(noteParts[0])
                    contentEditText.setText(noteParts[1])
                }
                adapter.notifyDataSetChanged()
            }
        }

        saveButton.setOnClickListener(View.OnClickListener {
            val title: String = titleEditText.getText().toString()
            val content: String = contentEditText.getText().toString()
            if (title.isEmpty()) {
                Toast.makeText(
                    this@NotepadActivity,
                    R.string.save_error_message,
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                if (editNotePosition != -1) {
                    val isUpdated: Boolean =
                        notepadDataManager.updateNoteByPosition(editNotePosition, title, content)
                    if (isUpdated) {
                        adapter.notifyDataSetChanged()
                        val intent = Intent(
                            this@NotepadActivity,
                            MainActivity::class.java
                        )
                        startActivity(intent)
                    }
                } else {
                    val newPosition: Int = notepadDataManager.getNotes().size
                    notepadDataManager.saveNote(
                        newPosition,
                        title,
                        content
                    ) //Troligtvis här problemet ligger, tilldelar endast "Key" 0 om och om igen.
                    adapter.notifyDataSetChanged()
                    val intent = Intent(
                        this@NotepadActivity,
                        MainActivity::class.java
                    )
                    startActivity(intent)
                }
            }
        })

        val deleteButton = findViewById<Button>(R.id.btn_deleteNote)
        deleteButton.setOnClickListener {
            if (editNotePosition != -1) {
                adapter.deleteNoteAtPosition(editNotePosition)
                adapter.notifyDataSetChanged()
                val intent = Intent(
                    this@NotepadActivity,
                    MainActivity::class.java
                )
                startActivity(intent)
            }
        }
    }


}


