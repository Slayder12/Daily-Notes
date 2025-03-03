package com.example.daily_notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import com.example.daily_notes.ui.pages.AddNoteScreen
import com.example.daily_notes.ui.pages.NotesScreen
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                    val context = LocalContext.current
                    val viewModel: NoteViewModel = viewModel(
                        factory = NoteViewModelFactory(
                            (context.applicationContext as MyApplication).database.noteDataAccessObject()
                        )
                    )
                NotesApp(viewModel)
            }
        }
    }
}

@Composable
fun NotesApp(viewModel: NoteViewModel) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "notes") {
        composable("notes") { NotesScreen(navController, viewModel) }
        composable("addNote") { AddNoteScreen(navController, viewModel) }
    }
}
