package com.qt46.easynotebook.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.qt46.easynotebook.data.Note
import com.qt46.easynotebook.data.NoteCategory
import com.qt46.easynotebook.data.NoteItem
import com.qt46.easynotebook.data.local.relations.NoteWithNoteItem
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNote(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCategory(category: NoteCategory): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteItem(item: NoteItem): Long

    @Transaction
    @Query("SELECT * FROM note WHERE noteId = :noteId")
    suspend fun getNoteWithNoteItems(noteId: Int): List<NoteWithNoteItem>

    @Transaction
    @Query("SELECT * FROM note ")
    fun getAllNoteWithNoteItems(): List<NoteWithNoteItem>

    @Transaction
    @Query("SELECT * FROM note WHERE noteCategory = :categoryID")
    suspend fun getNoteCategoryWithNotes(categoryID: Long): List<NoteWithNoteItem>

    @Query("SELECT * FROM category")
    fun getAllCategory(): Flow<List<NoteCategory>>

    @Transaction
    @Query("SELECT * FROM note ")
    fun getAllNoteWithNoteItemsFlow(): Flow<List<NoteWithNoteItem>>
}