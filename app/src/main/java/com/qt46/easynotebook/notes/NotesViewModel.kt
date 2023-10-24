package com.qt46.easynotebook.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.qt46.easynotebook.MyApplication
import com.qt46.easynotebook.constants.NoteCategorys
import com.qt46.easynotebook.constants.formatter
import com.qt46.easynotebook.data.Note
import com.qt46.easynotebook.data.NoteCategory
import com.qt46.easynotebook.data.NoteRepository
import com.qt46.easynotebook.data.local.relations.NoteWithNoteItem
import com.qt46.easynotebook.data.NoteItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime


enum class SortBy {
    MODIFIED, CREATED, REMINDER
}

class NotesViewModel(private val repository: NoteRepository) : ViewModel() {

    private val scope = CoroutineScope(Dispatchers.IO)

    private val _category = MutableStateFlow(NoteCategory(categoryid = -1))
    private val _sortBy = MutableStateFlow(SortBy.MODIFIED)

    private val _allNotes = repository.getAllNoteFlow().stateIn(
        scope, SharingStarted.WhileSubscribed(5000), listOf()
    )

    private val _currentNote = MutableStateFlow(
        NoteWithNoteItem(
            Note(0, heading = "", 0, null, false), listOf()
        )
    )
    val currentNote = _currentNote.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val n = combine(_allNotes, _category, _sortBy) { notes, category, order ->
        Triple(notes, category, order)

    }.flatMapLatest { (notes, category, order) ->
        flow {
            emit(
                if (category.categoryid==-1L){
                    notes
                }else{
                    notes.filter { it.note.noteCategory == category.categoryid }
                }

            )
        }
    }.stateIn(
        scope, SharingStarted.WhileSubscribed(5000), listOf()
    )

    val categories = repository.getAllCategory().stateIn(
        scope, SharingStarted.WhileSubscribed(5000), listOf()
    )

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myRepository = (this[APPLICATION_KEY] as MyApplication).repository
                NotesViewModel(
                    repository = myRepository,
                )
            }
        }
    }

    fun addCategory() {
        viewModelScope.launch(Dispatchers.IO) {
            for (item in NoteCategorys) {
                repository.addCateGory(item)
            }
        }
    }

    fun addNote(note: Note, text: String) {
        viewModelScope.launch {
            val noteID = repository.addNote(note)
            repository.addNoteItem(NoteItem(text = text, noteId = noteID))
        }
    }

    fun addNote(note: Note, items: List<String>,checkboxs:List<Boolean>) {
        viewModelScope.launch {
            val noteID = repository.addNote(note)
            for (idx in items.indices) {
                if (items[idx].isNotEmpty()) {
                    repository.addNoteItem(
                        NoteItem(
                            text = items[idx],
                            noteId = noteID,
                            isComplete =  checkboxs[idx]
                        )
                    )
                }
            }

        } 
    }

    fun setCategory(category: NoteCategory) {
        _category.update {
            category
        }
    }

    fun setCurrentNote(noteWithNoteItem: NoteWithNoteItem){
        _currentNote.update {
            noteWithNoteItem
        }
    }

    fun updateCurrentNote() {
        viewModelScope.launch {
            repository.addNote(_currentNote.value.note.copy(modifiedTime = formatter.format(
                LocalDateTime.now())))
            for (item in _currentNote.value.items){
                repository.addNoteItem(item)
            }
        }
    }

    fun updateNoteHeading(heading: String) {
        _currentNote.update {
            it.copy(note = it.note.copy(heading = heading))
        }
    }

    fun updateTextNote(text: String) {
        _currentNote.update {
            it.copy(items = it.items.toMutableList().apply {
                this[0]=this[0].copy(text=text)
            })
        }
    }

    fun updateNoteCategory(categoryid: Long) {
        _currentNote.update {
            it.copy(note = it.note.copy(noteCategory =categoryid))
        }
    }

    fun updateNoteItem(index: Int, text: String) {
        _currentNote.update {
            it.copy(items = it.items.toMutableList().apply {
                this[index]=this[index].copy(text=text)
            })
        }
    }

    fun updateNoteItemCheckBox(index: Int) {
        _currentNote.update {
            it.copy(items = it.items.toMutableList().apply {
                this[index]=this[index].copy(isComplete = !this[index].isComplete)
            })
        }
    }
    fun addNoteItem(){
        _currentNote.update {
            it.copy(items = it.items.toMutableList().apply {
                add(NoteItem(text = "", noteId = it.note.noteId))
            })
        }
    }
}