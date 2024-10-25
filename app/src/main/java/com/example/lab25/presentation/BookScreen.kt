package com.example.lab25.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lab25.R
import com.example.lab25.data.BookDBHelper
import com.example.lab25.repository.BookRepository
import com.example.lab25.model.Book

@Composable
fun BookApp(repository: BookRepository) {
    var books by remember { mutableStateOf(repository.getBooks()) }
    var showDialog by remember { mutableStateOf(false) }
    var bookToEdit by remember { mutableStateOf<Book?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(16.dp)) {
            LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                item { Text("Новые книги", style = MaterialTheme.typography.titleMedium) }
                items(books.filter { it.status == BookDBHelper.STATUS_NEW }) { book ->
                    BookItem(book, repository, onEdit = { bookToEdit = it; showDialog = true }) { books = repository.getBooks() }
                }
                item { Text("Прочитанные книги", style = MaterialTheme.typography.titleMedium) }
                items(books.filter { it.status == BookDBHelper.STATUS_READ }) { book ->
                    BookItem(book, repository, onEdit = { bookToEdit = it; showDialog = true }) { books = repository.getBooks() }
                }
            }
        }

        FloatingActionButton(
            onClick = { showDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.plus),
                contentDescription = "Add Book",
                modifier = Modifier.size(18.dp)
            )
        }

        if (showDialog) {
            if (bookToEdit != null) {
                EditBookDialog(
                    book = bookToEdit!!,
                    onDismiss = { showDialog = false; bookToEdit = null },
                    onSave = { updatedBook ->
                        repository.updateBook(updatedBook)
                        books = repository.getBooks()
                        showDialog = false
                        bookToEdit = null
                    }
                )
            } else {
                AddBookDialog(
                    onDismiss = { showDialog = false },
                    onAdd = { title, author ->
                        val newBook = Book(title = title, author = author, status = BookDBHelper.STATUS_NEW)
                        repository.addBook(newBook)
                        books = repository.getBooks()
                        showDialog = false
                    }
                )
            }
        }
    }
}

@Composable
fun AddBookDialog(onDismiss: () -> Unit, onAdd: (String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Добавить книгу") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Author") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onAdd(title, author) }) {
                Text("Добавить")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отменить")
            }
        }
    )
}

@Composable
fun EditBookDialog(book: Book, onDismiss: () -> Unit, onSave: (Book) -> Unit) {
    var title by remember { mutableStateOf(book.title) }
    var author by remember { mutableStateOf(book.author) }
    var status by remember { mutableStateOf(book.status == BookDBHelper.STATUS_READ) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Изменить книгу") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Название") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = author,
                    onValueChange = { author = it },
                    label = { Text("Автор") },
                    modifier = Modifier.fillMaxWidth()
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = status,
                        onCheckedChange = { status = it }
                    )
                    Text("Прочитано")
                }
            }
        },
        confirmButton = {
            Button(onClick = { onSave(book.copy(title = title, author = author, status = if (status) BookDBHelper.STATUS_READ else BookDBHelper.STATUS_NEW)) }) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Отменить")
            }
        }
    )
}
@Composable
fun BookItem(book: Book, repository: BookRepository, onEdit: (Book) -> Unit, onUpdate: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .clickable { onEdit(book) }) {
            Icon(
                painter = painterResource(
                    id = if (book.status == BookDBHelper.STATUS_READ) R.drawable.green_book else R.drawable.black_book
                ), contentDescription = "Status", modifier = Modifier.size(52.dp)

            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f).align(Alignment.CenterVertically)) {
                Text(book.title, style = MaterialTheme.typography.titleMedium)
                Text(book.author, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(onClick = {
                repository.deleteBook(book.id)
                onUpdate()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.trash),
                    contentDescription = "Delete",
                    modifier = Modifier.size(48.dp)
                )
            }
    }
}
