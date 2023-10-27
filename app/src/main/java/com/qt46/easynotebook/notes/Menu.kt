package com.qt46.easynotebook.notes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qt46.easynotebook.R

@Composable
@Preview
fun Menu() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp, 10.dp)
    ) {
        MenuSection(heading = stringResource(id = R.string.login), subHeading = stringResource(id = R.string.sign_in_sub_heading))
        Spacer(modifier = Modifier.height(9.dp))
//        Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(5.dp)) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth(.5f)
                    .height(60.dp),
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(10.dp,0.dp,0.dp,10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_recycle),
                    contentDescription = null,
                    modifier = Modifier.requiredSize(32.dp)
                )
                Spacer(modifier = Modifier.width(9.dp))
                Text(text = stringResource(id = R.string.recycle_bin),style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ),
                shape = RoundedCornerShape(0.dp, 10.dp, 10.dp, 0.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_achive),
                    contentDescription = null,
                    modifier = Modifier.requiredSize(32.dp)
                )
                Spacer(modifier = Modifier.width(9.dp))
                Text(text = stringResource(id = R.string.achive),style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)
            }
        }

//        }
        Spacer(modifier = Modifier.height(9.dp))
        MenuSection(
            leadingIcon = painterResource(id = R.drawable.ic_feedback),
            trailingIcon = null, heading = stringResource(id = R.string.feedback_and_sugg), subHeading = null, sectionshape = RoundedCornerShape(10.dp,10.dp,0.dp,0.dp))
        MenuSection(leadingIcon = painterResource(id = R.drawable.ic_settings),trailingIcon = null, heading = stringResource(
            id = R.string.setting
        ), subHeading = stringResource(id = R.string.setting_sub_heading), sectionshape = RoundedCornerShape(0.dp,0.dp,0.dp,0.dp))
        MenuSection(leadingIcon = painterResource(id = R.drawable.ic_widget),trailingIcon = null, heading = stringResource(
            id = R.string.widget
        ), subHeading = stringResource(id = R.string.widget_subhead), sectionshape = RoundedCornerShape(0.dp,0.dp,10.dp,10.dp))
    }
}

@Composable
@Preview
fun MenuSection(
    leadingIcon: Painter = painterResource(id = R.drawable.ic_google),
    trailingIcon: Int? = R.drawable.ic_sync,
    heading: String = "Login and Restore", subHeading: String? = "Sign in to synchoronic your data",
    sectionshape: RoundedCornerShape = RoundedCornerShape(10.dp)
) {
    Button(modifier = Modifier
        .fillMaxWidth(),
        onClick = {},
         shape = sectionshape,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurface
        )

    ) {
        Row {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(9.dp, 12.dp)
            ) {
                Icon(
                    leadingIcon,
                    contentDescription = null,
                    modifier = Modifier.requiredSize(32.dp)
                )
                Spacer(modifier = Modifier.width(18.dp))
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth(.85f)
                ) {
                    Text(
                        text = heading,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (subHeading != null) {
                        Text(text = subHeading, style = MaterialTheme.typography.bodyMedium)
                    }
                }
                if (trailingIcon != null) {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = trailingIcon),
                            contentDescription = null,
                            modifier = Modifier.requiredSize(32.dp)
                        )
                    }
                }

            }

        }


    }
}