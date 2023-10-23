package com.qt46.easynotebook.data

import com.qt46.easynotebook.data.local.relations.NoteWithNoteItem
import kotlinx.coroutines.flow.Flow

interface DataSource {

    suspend fun addNote(note: Note):Long

    suspend fun addCateGory(category: NoteCategory)

    suspend fun addNoteItem(noteItem: NoteItem):Long

    suspend fun getAllNotes(): List<NoteWithNoteItem>

    suspend fun getNotesByCategory(categoryId:Long):List<NoteWithNoteItem>

    fun getAllCategory():Flow<List<NoteCategory>>

    fun getAllNoteFlow():Flow<List<NoteWithNoteItem>>
}