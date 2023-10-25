package com.qt46.easynotebook.data.local

import android.content.Context
import com.qt46.easynotebook.data.DataSource
import com.qt46.easynotebook.data.Note
import com.qt46.easynotebook.data.NoteCategory
import com.qt46.easynotebook.data.NoteItem
import com.qt46.easynotebook.data.local.relations.NoteWithNoteItem
import kotlinx.coroutines.flow.Flow

class LocalDataSource(context: Context, private val dao: NoteDao) : DataSource {
    override suspend fun addNote(note: Note): Long {
        return dao.addNote(note)
    }

    override suspend fun addCateGory(category: NoteCategory) {
        dao.addCategory(category)
    }

    override suspend fun addNoteItem(noteItem: NoteItem): Long {
        return dao.addNoteItem(noteItem)
    }

    override suspend fun getAllNotes(): List<NoteWithNoteItem> {
        return dao.getAllNoteWithNoteItems()
    }

    override suspend fun getNotesByCategory(categoryId: Long): List<NoteWithNoteItem> {
        return dao.getNoteCategoryWithNotes(categoryId)
    }

    override fun getAllCategory(): Flow<List<NoteCategory>> {
        return dao.getAllCategory()
    }

    override fun getAllNoteFlow(): Flow<List<NoteWithNoteItem>> = dao.getAllNoteWithNoteItemsFlow()
}