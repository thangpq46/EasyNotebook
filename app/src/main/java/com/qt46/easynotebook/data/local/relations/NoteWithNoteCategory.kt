package com.qt46.easynotebook.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.qt46.easynotebook.data.Note
import com.qt46.easynotebook.data.NoteCategory

data class NoteWithNoteCategory(
    @Embedded
    val note: Note,
     @Relation(
        parentColumn = "noteCategory", entityColumn = "categoryid"
    )
    val category: NoteCategory
)