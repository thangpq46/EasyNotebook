package com.qt46.easynotebook.data.local.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.qt46.easynotebook.data.Note
import com.qt46.easynotebook.data.NoteCategory

data class NoteCategoryWithNote(
    @Embedded val noteCategory: NoteCategory, @Relation(
        parentColumn = "categoryid", entityColumn = "noteCategory"
    ) val notes: List<Note>
)
