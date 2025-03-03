package com.example.daily_notes.ui.pages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.daily_notes.Note
import com.example.daily_notes.NoteViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen(navController: NavController, viewModel: NoteViewModel) {
    val notes by viewModel.notes.collectAsState()
    var selectedNote by remember { mutableStateOf<Note?>(null) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            LazyColumn {
                items(notes) { note ->
                    NavigationDrawerItem(
                        icon = {
                            IconButton(onClick = {
                                if (notes.size == 1) {
                                    scope.launch {
                                        snackBarHostState.showSnackbar(
                                            "Создайте хотя бы ещё одну заметку перед удалением"
                                        )
                                    }
                                } else {
                                    viewModel.deleteNote(note)
                                    if (selectedNote == note) {
                                        selectedNote = null
                                    }
                                }
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                            }
                        },
                        label = { Text(note.title) },
                        selected = note == selectedNote,
                        onClick = {
                            selectedNote = note
                            scope.launch { drawerState.close() }
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = Color.Blue,
                            unselectedContainerColor = Color.LightGray,
                            selectedTextColor = Color.White,
                            unselectedTextColor = Color.Black,
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.Black
                        )

                    )
                }
            }
        }
    ) {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Меню")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { navController.navigate("addNote") }) {
                    Icon(Icons.Default.Add, contentDescription = "Добавить")
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center
            ) {
                if (selectedNote != null) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = selectedNote!!.title, style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = selectedNote!!.text, style = MaterialTheme.typography.bodyLarge)
                    }
                } else {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (notes.isEmpty()){
                            Text(text = "Добро пожаловать", style = MaterialTheme.typography.headlineMedium)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = "Напишите свою первую заметку", style = MaterialTheme.typography.bodyLarge)
                        } else {
                            Text(text = "Напишите заметку", style = MaterialTheme.typography.headlineMedium)
                        }
                    }
                }
            }
        }

    }
}

