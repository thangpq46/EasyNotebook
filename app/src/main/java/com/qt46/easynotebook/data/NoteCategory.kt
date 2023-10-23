package com.qt46.easynotebook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class NoteCategory(
    @PrimaryKey(autoGenerate = true) val categoryid: Long=0,
    @ColumnInfo var text: String="",
    @ColumnInfo val color: Long = 0xFFFAC029
    )
