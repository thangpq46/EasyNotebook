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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.qt46.easynotebook.R
import com.qt46.easynotebook.data.SortType

@Composable
@Preview(showBackground = true)
fun DialogSelectSortBy(
    sortBy: List<SortType> = listOf(
        SortType(
            SortBy.MODIFIED,
            R.string.sort_by_modified,
            R.drawable.ic_category
        ),
        SortType(SortBy.REMINDER, R.string.sort_by_remider, R.drawable.ic_category),
        SortType(SortBy.CREATED, R.string.sort_by_created, R.drawable.ic_category)
    ),
    onConfirmation: (SortType) -> Unit = {},
    onDismiss: () -> Unit = {}
) {

    Dialog(
        onDismissRequest = { onDismiss() }, properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {
        Card(
            modifier = Modifier
                .customDialogModifier(CustomDialogPosition.BOTTOM)
                .fillMaxWidth()
                .wrapContentHeight(),

            shape = RoundedCornerShape(16.dp, 16.dp, 0.dp, 0.dp),

            ) {
            Column(Modifier.padding(horizontal = 18.dp)) {
                Spacer(modifier = Modifier.height(18.dp))
                Text(
                    text = stringResource(id = R.string.sort_by),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(9.dp))
                LazyColumn(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    itemsIndexed(items = sortBy) { index, sortType ->
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(3.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .clickable {
                                onConfirmation(sortType)
                            }) {
                            Spacer(modifier = Modifier.width(9.dp))
                            Icon(painterResource(id = R.drawable.ic_category), null)
                            Spacer(modifier = Modifier.width(9.dp))
                            Text(
                                text = stringResource(id = sortType.nameId),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(18.dp))
            }
        }
    }
}
