package com.qt46.easynotebook.notes

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.qt46.easynotebook.MyApplication
import com.qt46.easynotebook.constants.IS_GRID
import com.qt46.easynotebook.constants.NoteCategorys
import com.qt46.easynotebook.constants.formatter
import com.qt46.easynotebook.data.Note
import com.qt46.easynotebook.data.NoteCategory
import com.qt46.easynotebook.data.NoteItem
import com.qt46.easynotebook.data.NoteRepository
import com.qt46.easynotebook.data.SortType
import com.qt46.easynotebook.data.ViewType
import com.qt46.easynotebook.data.local.relations.NoteWithNoteItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
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
    var sharedPre : android.content.SharedPreferences? = null
    private val _currentNote = MutableStateFlow(
        NoteWithNoteItem(
            Note(0, heading = "", 0, null, false), listOf()
        )
    )
    val currentNote = _currentNote.asStateFlow()

    private val _textSearch = MutableStateFlow("")
    val textSearch = _textSearch.asStateFlow()
    @OptIn(ExperimentalCoroutinesApi::class)
    val n = combine(_allNotes, _category, _sortBy) { notes, category, order ->
        Triple(notes, category, order)

    }.flatMapLatest { (notes, category, order) ->
        flow {
            emit(if (category.categoryid == -1L) {
                notes
            } else {
                notes.filter { it.note.noteCategory == category.categoryid }
            }.sortedWith { note1, note2 ->
                when (order) {
                    SortBy.MODIFIED -> {
                        if (LocalDateTime.parse(note1.note.modifiedTime, formatter)
                                .isAfter(
                                    LocalDateTime.parse(
                                        note2.note.modifiedTime,
                                        formatter
                                    )
                                )
                        ) {
                            -1
                        } else if (LocalDateTime.parse(note1.note.modifiedTime, formatter)
                                .isBefore(
                                    LocalDateTime.parse(
                                        note2.note.modifiedTime,
                                        formatter
                                    )
                                )
                        ) {
                            1
                        } else {
                            0
                        }
                    }

                    SortBy.REMINDER -> {
                        if (note1.note.reminder!=null && note2.note.reminder!=null){
                            if (LocalDateTime.parse(note1.note.reminder, formatter)
                                    .isAfter(
                                        LocalDateTime.parse(
                                            note2.note.modifiedTime,
                                            formatter
                                        )
                                    )
                            ) {
                                -1
                            } else if (LocalDateTime.parse(note1.note.reminder, formatter)
                                    .isBefore(LocalDateTime.parse(note2.note.reminder, formatter))
                            ) {
                                1
                            } else {
                                0
                            }
                        }else{
                            0
                        }

                    }

                    else -> {
                        if (LocalDateTime.parse(note1.note.createdDate, formatter)
                                .isAfter(LocalDateTime.parse(note2.note.createdDate, formatter))
                        ) {
                            -1
                        } else if (LocalDateTime.parse(note1.note.createdDate, formatter)
                                .isBefore(
                                    LocalDateTime.parse(
                                        note2.note.createdDate,
                                        formatter
                                    )
                                )
                        ) {
                            1
                        } else {
                            0
                        }
                    }
                }

            }
//            {
//                println("CaLLED")
//                when(order){
//                        SortBy.MODIFIED->{
////                            LocalDate.parse(it.note.modifiedTime, formatter)
//                            it.note.heading.length
//
//                        }
//                    SortBy.CREATED->{
//                        it.note.heading.length
////                        LocalDate.parse(it.note.createdDate, formatter)
////                        it.note.createdDate
//                    }
//                    else->{
//
////                        LocalDate.parse(it.note.modifiedTime, formatter)
//                        it.note.heading.length
//                    }
//                }
//            }

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

    fun addNote(note: Note, items: List<String>, checkboxs: List<Boolean>) {
        viewModelScope.launch {
            val noteID = repository.addNote(note)
            for (idx in items.indices) {
                if (items[idx].isNotEmpty()) {
                    repository.addNoteItem(
                        NoteItem(
                            text = items[idx], noteId = noteID, isComplete = checkboxs[idx]
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

    fun setCurrentNote(noteWithNoteItem: NoteWithNoteItem) {
        _currentNote.update {
            noteWithNoteItem
        }
    }

    fun updateCurrentNote() {
        viewModelScope.launch {
            repository.addNote(
                _currentNote.value.note.copy(
                    modifiedTime = formatter.format(
                        LocalDateTime.now()
                    )
                )
            )
            for (item in _currentNote.value.items) {
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
                this[0] = this[0].copy(text = text)
            })
        }
    }

    fun updateNoteCategory(categoryid: Long) {
        _currentNote.update {
            it.copy(note = it.note.copy(noteCategory = categoryid))
        }
    }

    fun updateNoteItem(index: Int, text: String) {
        _currentNote.update {
            it.copy(items = it.items.toMutableList().apply {
                this[index] = this[index].copy(text = text)
            })
        }
    }

    fun updateNoteItemCheckBox(index: Int) {
        _currentNote.update {
            it.copy(items = it.items.toMutableList().apply {
                this[index] = this[index].copy(isComplete = !this[index].isComplete)
            })
        }
    }

    fun addNoteItem() {
        _currentNote.update {
            it.copy(items = it.items.toMutableList().apply {
                add(NoteItem(text = "", noteId = it.note.noteId))
            })
        }
    }

    fun sortNotes(sortType: SortType) {
        _sortBy.update {
            sortType.type
        }
    }

    val filterNotes = _allNotes.combine(_textSearch){ notes, text->
        if (text.isNotEmpty()){
            notes.filter { it.note.heading.contains(text,true)  }
        }else{
            listOf()
        }

    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), listOf()
    )
    fun searchText(text: String) {
        _textSearch.update {
            text
        }
    }

    private val _viewType = MutableStateFlow(ViewType.Grid)
    val viewType = _viewType.asStateFlow()

    fun changeViewType(viewType: ViewType){
        viewModelScope.launch {
            _viewType.update {
                viewType
            }
            sharedPre?.edit()?.putBoolean(IS_GRID, viewType==ViewType.Grid)?.apply()
        }

    }

    fun initViewType(shared: SharedPreferences?) {
        viewModelScope.launch {
            sharedPre=shared
            sharedPre?.getBoolean(IS_GRID,true)?.let { isGrid->
                _viewType.update {
                    if (isGrid){
                        ViewType.Grid
                    }else{
                        ViewType.List
                    }
                }
            }
        }

    }
}