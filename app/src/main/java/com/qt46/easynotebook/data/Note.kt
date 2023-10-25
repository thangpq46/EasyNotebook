package com.qt46.easynotebook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.qt46.easynotebook.constants.formatter
import java.time.LocalDateTime

@Entity(tableName = "note")
data class Note(
    @PrimaryKey(autoGenerate = true) val noteId: Long = 0,
    @ColumnInfo val heading: String,
    @ColumnInfo val noteCategory: Long,
    @ColumnInfo val reminder: String?,
    @ColumnInfo val isPinned: Boolean = false,
    @ColumnInfo val isCheckBox: Boolean = false,
    val createdDate: String = formatter.format(LocalDateTime.now()),
    val modifiedTime: String = formatter.format(LocalDateTime.now())
)
