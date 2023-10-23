package com.qt46.easynotebook.notes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.qt46.easynotebook.R
import com.qt46.easynotebook.data.NoteCategory

@Composable
@Preview(showBackground = true)
fun DialogSelectCategory(
    categories: List<NoteCategory> = listOf(),
    onConfirmation: (Int) -> Unit={},
    onDismiss:()->Unit={}
) {

    Dialog(onDismissRequest = { onDismiss() },properties = DialogProperties(
        usePlatformDefaultWidth = false
    )
    ) {
        Card(
            modifier = Modifier
                .customDialogModifier(CustomDialogPosition.BOTTOM)
                .fillMaxWidth()
                .wrapContentHeight()
                ,

            shape = RoundedCornerShape(16.dp,16.dp,0.dp,0.dp),

        ) {
            Column(Modifier.padding(horizontal = 18.dp)) {
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = stringResource(id = R.string.change_color),
                    modifier = Modifier
                        .fillMaxWidth()
                    ,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(9.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(9.dp)){
                    itemsIndexed(items=categories){ index,category ->
                        Row(verticalAlignment = Alignment.CenterVertically,modifier = Modifier.height(50.dp).fillMaxWidth().clip(RoundedCornerShape(3.dp)).background(MaterialTheme.colorScheme.background).clickable {
                            onConfirmation(index)
                        }) {
                            Spacer(modifier = Modifier.width(9.dp))
                            Icon(painterResource(id = R.drawable.ic_category),null, tint = Color(category.color))
                            Spacer(modifier = Modifier.width(9.dp))
                            Text(text = category.text, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                    }
                }

            }
        }
    }
}

enum class CustomDialogPosition {
    BOTTOM, TOP
}

fun Modifier.customDialogModifier(pos: CustomDialogPosition) = layout { measurable, constraints ->

    val placeable = measurable.measure(constraints);
    layout(constraints.maxWidth, constraints.maxHeight){
        when(pos) {
            CustomDialogPosition.BOTTOM -> {
                placeable.place(0, constraints.maxHeight - placeable.height, 10f)
            }
            CustomDialogPosition.TOP -> {
                placeable.place(0,0,10f)
            }
        }
    }
}