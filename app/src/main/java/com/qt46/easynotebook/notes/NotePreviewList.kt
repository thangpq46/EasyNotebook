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
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.qt46.easynotebook.R
import com.qt46.easynotebook.data.local.relations.NoteWithNoteItem

@Composable
fun NotePreviewList(noteWithNoteItem: NoteWithNoteItem, noteColor: Long, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() }, shape = RoundedCornerShape(5.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Spacer(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(9.dp)
                    .background(Color(noteColor))
            )
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(.86f)
                    .padding(9.dp)
            ) {
                Text(
                    text = if(noteWithNoteItem.note.heading.isNotEmpty()) noteWithNoteItem.note.heading else noteWithNoteItem.items.firstOrNull()?.text.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold
                )
                Text(text = noteWithNoteItem.note.modifiedTime.substring(0, 5))
            }
            Icon(painterResource(id = R.drawable.ic_remider_time), contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.requiredSize(18.dp))
            if (noteWithNoteItem.note.isCheckBox){
                Icon(painterResource(id = R.drawable.ic_checklist), contentDescription = null, tint = MaterialTheme.colorScheme.outline, modifier = Modifier.requiredSize(18.dp))
            }

        }
    }
}