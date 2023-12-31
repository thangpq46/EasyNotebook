package com.qt46.easynotebook.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.qt46.easynotebook.data.Note
import com.qt46.easynotebook.data.NoteCategory
import com.qt46.easynotebook.data.NoteItem

data class NoteWithNoteItem(
    @Embedded val note: Note, @Relation(
        parentColumn = "noteId", entityColumn = "noteId"
    ) val items: List<NoteItem>,
    @Relation(
        parentColumn = "noteCategory", entityColumn = "categoryid"
    )
    val noteCategory: NoteCategory

)
