package com.qt46.easynotebook.data

import com.qt46.easynotebook.data.local.LocalDataSource
import com.qt46.easynotebook.data.local.relations.NoteWithNoteItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NoteRepository(private val localDataSource: LocalDataSource):DataSource {
    override suspend fun addNote(note: Note):Long {
        return localDataSource.addNote(note)
//        remoteDataSource.addNote(note)
    }

    override suspend fun addCateGory(category: NoteCategory) {
        withContext(Dispatchers.IO){
             localDataSource.addCateGory(category)
        }
    }

    override suspend fun addNoteItem(noteItem: NoteItem): Long {
        return localDataSource.addNoteItem(noteItem)
    }

    override suspend fun getAllNotes(): List<NoteWithNoteItem> {
        return localDataSource.getAllNotes()
    }

    override suspend fun getNotesByCategory(categoryId: Long): List<NoteWithNoteItem> {
        return localDataSource.getNotesByCategory(categoryId)
    }

    override fun getAllCategory(): Flow<List<NoteCategory>> =localDataSource.getAllCategory()
    override fun getAllNoteFlow(): Flow<List<NoteWithNoteItem>> {
        return localDataSource.getAllNoteFlow()
    }


}