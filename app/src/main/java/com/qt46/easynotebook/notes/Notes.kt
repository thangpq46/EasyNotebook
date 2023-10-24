package com.qt46.easynotebook.notes

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.qt46.easynotebook.R
import com.qt46.easynotebook.data.NoteCategory
import com.qt46.easynotebook.data.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Notes(
    viewModel: NotesViewModel,
    navController: NavController,
    selectTypeText: () -> Unit,
    selectTypeCheckList: () -> Unit
){
    val activity = LocalContext.current as Activity
    val notes by viewModel.n.collectAsState()
    val categories by viewModel.categories.collectAsState()

    var showDialogSelectCategory by remember {
        mutableStateOf(false)
    }
    var selectedCategory by remember {
        mutableIntStateOf(-1)
    }
    if (showDialogSelectCategory) {
        DialogSelectCategory(categories, onConfirmation = {
            viewModel.setCategory(categories[it])
            showDialogSelectCategory = false
            selectedCategory=it
        }, onDismiss = {
            showDialogSelectCategory = false
        })
    }
    var showDialogSelectNoteType by remember {
        mutableStateOf(false)
    }
    if (showDialogSelectNoteType){
        DialogSelectNoteType(onClickFirstBtn = {
            selectTypeText()
            showDialogSelectNoteType=false
        },{
            selectTypeCheckList()
            showDialogSelectNoteType=false
        },{
            showDialogSelectNoteType=false
        })
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(horizontal = 16.dp)) {
        TopAppBar(title = { }, navigationIcon = {
            IconButton(onClick = { activity.finish() }) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }
        }, actions = {
            IconButton(onClick = { showDialogSelectCategory = true }) {
                if (selectedCategory!=-1){
                    Icon(
                        painterResource(id = R.drawable.ic_category),
                        null,
                        tint = Color(categories[selectedCategory].color)
                    )
                }else{
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
                                    ), blendMode = BlendMode.SrcAtop)
                            }
                        },
                    )
                }

            }

            IconButton(onClick = { /*TODO*/ }) {
                Icon(painterResource(id = R.drawable.ic_sort), null)
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.MoreVert, null)
            }
        }

        )
        LazyVerticalGrid(columns =GridCells.Adaptive(160.dp) , horizontalArrangement = Arrangement.spacedBy(9.dp), verticalArrangement = Arrangement.spacedBy(9.dp)){
            items(items = notes){
                NotePreview(it, noteColor = getColorByCategoryId(categories,it.note.noteCategory)){
                    navController.navigate(Screen.UpdateNote.route)
                    viewModel.setCurrentNote(it)
                }
            }
        }


    }
    Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Bottom , modifier = Modifier.padding(16.dp)) {
        FloatingActionButton(
            onClick = { showDialogSelectNoteType=true },
            shape = CircleShape
        ){
            Icon(Icons.Filled.Edit, "Extended floating action button.")
        }
    }
    
}

fun getColorByCategoryId(categories: List<NoteCategory>, noteCategory: Long): Long {
    return categories.find {
        it.categoryid==noteCategory
    }?.color ?: 0xFF3BC700

}
