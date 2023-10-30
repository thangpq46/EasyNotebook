package com.qt46.easynotebook.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.qt46.easynotebook.data.local.relations.NoteWithNoteItem

@Composable
fun NotePreview(noteWithNoteItem: NoteWithNoteItem, noteColor: Long, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .height(220.dp)
            .clickable { onClick() }, shape = RoundedCornerShape(5.dp)
    ) {
        Row {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(9.dp)
                    .background(Color(noteColor))
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(9.dp)
            ) {
                Text(
                    text = noteWithNoteItem.note.heading,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )
                Text(text = noteWithNoteItem.note.modifiedTime.substring(5, 10))
                Divider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground
                )

                LazyColumn {
                    items(items = noteWithNoteItem.items) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (noteWithNoteItem.note.isCheckBox) {
                                Checkbox(
                                    checked = it.isComplete,
                                    onCheckedChange = {},
                                    enabled = false
                                )
                            }
                            Text(
                                text = it.text,
                                textDecoration = if (it.isComplete) TextDecoration.LineThrough else null
                            )

                        }

                    }
                }
            }
        }
    }
}