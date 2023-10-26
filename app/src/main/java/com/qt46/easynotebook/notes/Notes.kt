package com.qt46.easynotebook.notes

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.qt46.easynotebook.R
import com.qt46.easynotebook.constants.FIRST_TIME
import com.qt46.easynotebook.data.NoteCategory
import com.qt46.easynotebook.data.Screen
import com.qt46.easynotebook.data.ViewType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Notes(
    viewModel: NotesViewModel,
    navController: NavController,
    selectTypeText: () -> Unit,
    selectTypeCheckList: () -> Unit
) {
    val activity = LocalContext.current as Activity
    val notes by viewModel.n.collectAsState()
    val categories by viewModel.categories.collectAsState()

    var showDialogSelectCategory by remember {
        mutableStateOf(false)
    }
    var selectedCategory by remember {
        mutableIntStateOf(-1)
    }
    val textSearch by viewModel.textSearch.collectAsState()
    if (showDialogSelectCategory) {
        DialogSelectCategory(categories, onConfirmation = {
            viewModel.setCategory(categories[it])
            showDialogSelectCategory = false
            selectedCategory = it
        }, onDismiss = {
            showDialogSelectCategory = false
        })
    }
    var showDialogSelectNoteType by remember {
        mutableStateOf(false)
    }
    if (showDialogSelectNoteType) {
        DialogSelectNoteType(onClickFirstBtn = {
            selectTypeText()
            showDialogSelectNoteType = false
        }, {
            selectTypeCheckList()
            showDialogSelectNoteType = false
        }, {
            showDialogSelectNoteType = false
        })
    }
    var showDialogSortNotes by remember {
        mutableStateOf(false)
    }
    if (showDialogSortNotes) {
        DialogSelectSortBy(onConfirmation = {
            viewModel.sortNotes(it)
            showDialogSortNotes = false
        }, onDismiss = {
            showDialogSortNotes = false
        })

    }
    var showDropdownMenu by remember {
        mutableStateOf(false)
    }
    var active by rememberSaveable { mutableStateOf(false) }
    var showDialogSelectViewType by remember {
        mutableStateOf(false)
    }
    if (showDialogSelectViewType){
        DialogSelectViewType(onListSelected = viewModel::changeViewType, onGridSelected = viewModel::changeViewType, onDismiss = {
            showDialogSelectViewType=false
        })
    }
    val viewType by viewModel.viewType.collectAsState()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        DropdownMenu(
            offset = DpOffset(0.dp, 60.dp),
            expanded = showDropdownMenu,
            onDismissRequest = { showDropdownMenu = false }) {
            DropdownMenuItem(leadingIcon = {
                Icon(
                    painterResource(id = R.drawable.ic_select),
                    contentDescription = null
                )
            }, text = {
                Text(
                    text = stringResource(
                        id = R.string.select
                    )
                )
            }, onClick = { /*TODO*/ })
            DropdownMenuItem(
                leadingIcon = { Icon(Icons.Default.Menu, contentDescription = null) },
                text = {Text(
                    text = stringResource(
                        id = R.string.view
                    )
                ) },
                onClick = { showDialogSelectViewType=true
                    showDropdownMenu=false
                })
            DropdownMenuItem(
                leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                text = { Text(
                    text = stringResource(
                        id = R.string.feedback
                    )
                ) },
                onClick = { /*TODO*/ })
        }

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        TopAppBar(title = { }, navigationIcon = {
            IconButton(onClick = { activity.finish() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        }, actions = {
            IconButton(onClick = { showDialogSelectCategory = true }) {
                if (selectedCategory != -1) {
                    Icon(
                        painterResource(id = R.drawable.ic_category),
                        null,
                        tint = Color(categories[selectedCategory].color)
                    )
                } else {
                    Icon(
                        painterResource(id = R.drawable.ic_category),
                        null,
                        modifier = Modifier.drawWithCache {
                            onDrawWithContent {
                                drawContent()
                                drawCircle(
                                    Brush.sweepGradient(
                                        listOf(
                                            Color(0xFF3BC700),
                                            Color(0xFF0080A7),
                                            Color(0xFF8100B1),
                                            Color(0xFFE62222),
                                            Color(0xFF666666),
                                            Color(0xFF673AB7),
                                            Color(0xFF009688)
                                        )
                                    ), blendMode = BlendMode.SrcAtop
                                )
                            }
                        },
                    )
                }

            }

            IconButton(onClick = { showDialogSortNotes = true }) {
                Icon(painterResource(id = R.drawable.ic_sort), null)
            }
            IconButton(onClick = { showDropdownMenu = true }) {
                Icon(Icons.Default.MoreVert, null)
            }
        }

        )
val filterdNotes by viewModel.filterNotes.collectAsState()
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .semantics { traversalIndex = -1f },
                query = textSearch,
                onQueryChange = { viewModel.searchText(it) },
                onSearch = { active = false },
                active = active,
                onActiveChange = {
                    active = it
                },
                placeholder = { Text("Hinted search text") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                ) {
                    for( item in filterdNotes){
                        ListItem(
                            headlineContent = { Text(item.note.heading) },
                            supportingContent = { Text(item.note.modifiedTime.substring(0,5)) },
                            leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                            modifier = Modifier
                                .clickable {
                                    navController.navigate(Screen.UpdateNote.route)
                                    viewModel.setCurrentNote(item)
                                    active = false
                                }
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
            }

        Spacer(modifier = Modifier.height(18.dp))
        if (viewType==ViewType.Grid){
                    LazyVerticalGrid(
            columns = GridCells.Adaptive(160.dp),
            horizontalArrangement = Arrangement.spacedBy(9.dp),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            items(items = notes) {
                NotePreview(
                    it, noteColor = getColorByCategoryId(categories, it.note.noteCategory)
                ) {
                    navController.navigate(Screen.UpdateNote.route)
                    viewModel.setCurrentNote(it)
                }
            }
        }
        }else{

            LazyColumn(verticalArrangement = Arrangement.spacedBy(9.dp)){
                items(items = notes) {
                    NotePreviewList(
                        it, noteColor = getColorByCategoryId(categories, it.note.noteCategory)
                    ) {
                        navController.navigate(Screen.UpdateNote.route)
                        viewModel.setCurrentNote(it)
                    }
                }
            }
        }



    }
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.padding(16.dp)
    ) {
        FloatingActionButton(
            onClick = { showDialogSelectNoteType = true }, shape = CircleShape
        ) {
            Icon(Icons.Filled.Edit, "Extended floating action button.")
        }
    }

}

fun getColorByCategoryId(categories: List<NoteCategory>, noteCategory: Long): Long {
    return categories.find {
        it.categoryid == noteCategory
    }?.color ?: 0xFF3BC700

}
