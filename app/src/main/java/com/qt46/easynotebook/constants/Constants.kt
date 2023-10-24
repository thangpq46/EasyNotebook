package com.qt46.easynotebook.constants

import androidx.compose.ui.graphics.Color
import com.qt46.easynotebook.data.NoteCategory
import java.time.format.DateTimeFormatter




val NoteCategorys = listOf(
    NoteCategory(text = "Work"),
    NoteCategory(text = "Schools", color = 0xFF3BC700),
    NoteCategory(text = "", color = 0xFF0080A7),
    NoteCategory(text = "", color = 0xFF8100B1),
    NoteCategory(text = "", color = 0xFFE62222),
    NoteCategory(text = "", color = 0xFF666666),
    NoteCategory(text = "", color = 0xFF673AB7),
    NoteCategory(text = "", color = 0xFF009688)
)
const val FIRST_TIME="first_time"
val a = Color(0xFF673AB7)

var formatter = DateTimeFormatter.ofPattern("MM-dd")