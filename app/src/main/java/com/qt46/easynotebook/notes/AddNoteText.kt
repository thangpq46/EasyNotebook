package com.qt46.easynotebook.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.qt46.easynotebook.R
import com.qt46.easynotebook.data.ItemNoteType
import com.qt46.easynotebook.data.Note


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNote(viewModel: NotesViewModel, navController: NavController, noteType: ItemNoteType) {

    var showDialogSelectCategory by remember {
        mutableStateOf(false)
    }

    var selectedCategory by remember {
        mutableIntStateOf(0)
    }
    var heading by remember {
        mutableStateOf("")
    }
    var text by remember {
        mutableStateOf("")
    }
    var listCheckNote by remember {
        mutableStateOf(listOf(""))
    }
    var listCheckBox by remember {
        mutableStateOf(listOf(false))
    }

    val categories by viewModel.categories.collectAsState()
    if (showDialogSelectCategory) {
        DialogSelectCategory(categories, onConfirmation = {
            selectedCategory = it
            showDialogSelectCategory = false
        }, onDismiss = {
            showDialogSelectCategory = false
        })
    }
    Column {

        TopAppBar(title = { }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        }, actions = {
            IconButton(onClick = { showDialogSelectCategory = true }) {
                Icon(
                    painterResource(id = R.drawable.ic_category),
                    null,
                    tint = Color(categories[selectedCategory].color)
                )
            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.FavoriteBorder, null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.MoreVert, null)
            }
        }

        )
        Spacer(modifier = Modifier.height(16.dp))


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 9.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = heading,
                onValueChange = { heading = it },
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.heading_placeholder),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                textStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = MaterialTheme.typography.titleLarge.fontSize
                ),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )

            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Edited:Now", modifier = Modifier.padding(start = 14.dp))
                Icon(Icons.Default.DateRange, contentDescription = null)
            }
            if (noteType == ItemNoteType.TEXT) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxSize(),
                    value = text,
                    onValueChange = { text = it },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    itemsIndexed(items = listCheckNote) { index, text ->
                        Row {

                            Checkbox(checked = listCheckBox[index], onCheckedChange = {
                                listCheckBox = listCheckBox.toMutableList().apply {
                                    this[index] = !this[index]
                                }
                            })
                            OutlinedTextField(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight(.6f),
                                value = text,
                                onValueChange = {
                                    listCheckNote.apply {
                                        val l = this.toMutableList()
                                        l[index] = it
                                        listCheckNote = l
                                    }

                                },
                                textStyle = MaterialTheme.typography.bodyMedium
                            )
                        }

                    }
                }
                TextButton(onClick = {
                    listCheckNote = listCheckNote.toMutableList().apply {
                        add("")
                    }
                    listCheckBox = listCheckBox.toMutableList().apply {
                        add(false)
                    }
                }) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Text(text = "Add")
                }
            }

        }

    }
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End,
        modifier = Modifier.padding(20.dp)
    ) {
        FloatingActionButton(onClick = {
            if (noteType == ItemNoteType.CHECKBOX) {
                viewModel.addNote(
                    Note(
                        heading = heading,
                        noteCategory = categories[selectedCategory].categoryid,
                        reminder = null,
                        isPinned = false,
                        isCheckBox = true
                    ), listCheckNote, listCheckBox
                )
                navController.popBackStack()
            } else {
                viewModel.addNote(
                    Note(
                        heading = heading,
                        noteCategory = categories[selectedCategory].categoryid,
                        reminder = null,
                        isPinned = false
                    ), text
                )
                navController.popBackStack()
            }

        }, shape = CircleShape) {
            Icon(Icons.Default.Check, contentDescription = "add note")
        }
    }
}
