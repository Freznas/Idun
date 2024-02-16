package com.example.idun

import android.os.Bundle
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

    private lateinit var notepadDataManager: NotepadDataManager
    private var editNotePosition = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotepadBinding.inflate(layoutInflater)

        setContentView(binding.root)
        notepadDataManager = NotepadDataManager(this)

        adapter = NotesAdapter(this, notepadDataManager.getNotes(), notepadDataManager)
        titleEditText = findViewById(R.id.et_TitleNote)
        contentEditText = findViewById(R.id.et_ContentNote)
        listView = findViewById(R.id.lv_notesList)
        notepadDataManager.getNotes()

        val savedTitle: String = notepadDataManager.savedTitle
        val savedContent: String = notepadDataManager.savedContent

        titleEditText.setText(savedTitle)
        contentEditText.setText(savedContent)


        editNotePosition = intent.getIntExtra("edit_note_position", -1)
        if (editNotePosition != -1) // Hämta positionen för anteckningen att redigera
        {
            val noteToEdit: String = notepadDataManager.getNoteByPosition(editNotePosition)
            val noteParts = noteToEdit.split("\\|".toRegex()).dropLastWhile { it.isEmpty() }
                .toTypedArray()
            if (noteParts.size == 2) {
                titleEditText.setText(noteParts[0])
                contentEditText.setText(noteParts[1])
            }
            adapter.notifyDataSetChanged()
        }

        binding.btnAddNote.setOnClickListener {
            val title: String = titleEditText.text.toString()
            val content: String = contentEditText.text.toString()
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
                    }
                } else {
                    val newPosition: Int = notepadDataManager.notes.size
                    notepadDataManager.saveNote(
                        newPosition,
                        title,
                        content
                    ) //Troligtvis här problemet ligger, tilldelar endast "Key" 0 om och om igen.
                    adapter.notifyDataSetChanged()

                }
            }
        }

       binding.btnDeleteNote.setOnClickListener {
            if (editNotePosition != -1) {
                adapter.deleteNoteAtPosition(editNotePosition)
                adapter.notifyDataSetChanged()

            }
        }
    }


}


