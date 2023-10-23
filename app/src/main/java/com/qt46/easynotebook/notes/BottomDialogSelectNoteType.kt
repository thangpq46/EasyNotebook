package com.qt46.easynotebook.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.qt46.easynotebook.R

@Composable
@Preview(showBackground = true)
fun DialogSelectNoteType(
    onClickFirstBtn: () -> Unit = {},
    onClickSecondBtn: () -> Unit = {},
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
                    text = stringResource(id = R.string.add_note),
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(9.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = onClickFirstBtn,
                        modifier = Modifier.width(120.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(painterResource(id = R.drawable.ic_txt), contentDescription = null)
                            Text(text = stringResource(id = R.string.txt))
                        }
                    }
                    Button(
                        onClick = onClickSecondBtn,
                        modifier = Modifier.width(120.dp),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painterResource(id = R.drawable.ic_checklist),
                                contentDescription = null
                            )
                            Text(text = stringResource(id = R.string.check_list))
                        }
                    }

                }
                Spacer(modifier = Modifier.height(18.dp))

            }
        }
    }
}