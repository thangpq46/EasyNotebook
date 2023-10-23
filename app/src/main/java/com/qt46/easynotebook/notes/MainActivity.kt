package com.qt46.easynotebook.notes

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compose.AppTheme
import com.qt46.easynotebook.constants.FIRST_TIME
import com.qt46.easynotebook.data.ItemNoteType
import com.qt46.easynotebook.data.Screen

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    private val viewModel: NotesViewModel by viewModels { NotesViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)
        sharedPref.getBoolean(FIRST_TIME, false).let {
            if (!it) {
                viewModel.addCategory()
                sharedPref.edit().putBoolean(FIRST_TIME, true).apply()
            }
        }

        setContent {
            AppTheme {
                val navController = rememberNavController()
                var noteItemType by remember {
                    mutableStateOf(ItemNoteType.TEXT)
                }
                // A surface container using the 'background' color from the theme
                Scaffold { paddingValues ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        NavHost(
                            navController = navController, startDestination = Screen.Notes.route
                        ) {
                            composable(Screen.AddNote.route) {
                                AddNote(viewModel, navController, noteItemType)


                            }
                            composable(Screen.Notes.route) {
                                Notes(viewModel, navController, {
                                    noteItemType = ItemNoteType.TEXT
                                    navController.navigate(Screen.AddNote.route)
                                }) {
                                    noteItemType = ItemNoteType.CHECKBOX
                                    navController.navigate(Screen.AddNote.route)
                                }
                            }
                            composable(Screen.UpdateNote.route) {
                                UpdateNote(
                                    viewModel, navController
                                )
                            }
                        }
                    }
                }


            }
        }
    }


}

@Composable
@Preview(showBackground = true)
fun Preview() {
    ExtendedFloatingActionButton(
        onClick = { },
        icon = { Icon(Icons.Filled.Edit, "Extended floating action button.") },
        text = { Text(text = "Add") },
    )
}