package com.qt46.easynotebook.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteItem(@PrimaryKey(autoGenerate = true) val id: Int=0, val text: String,val noteId:Long,val isComplete:Boolean=false)
