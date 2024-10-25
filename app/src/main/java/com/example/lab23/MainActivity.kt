package com.example.lab23

import Repository
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    val viewModel = MainViewModel()
    var query by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = query,
                onValueChange = { query = it },
                label = { Text("Search") },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { viewModel.searchRepositories(query) }) {
                Text("Search")
            }
        }
        RepositoryList(repositories = viewModel.repositories)
    }
}


@Composable
fun RepositoryList(repositories: LiveData<List<Repository>>) {
    val repoList by repositories.observeAsState(initial = emptyList())
    val context = LocalContext.current

    LazyColumn {
        items(repoList) { repo ->
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = "Название репозитория: " + repo.name,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.htmlUrl))
                        context.startActivity(intent)
                    },
                    textDecoration = TextDecoration.Underline,
                    color = Color.Blue,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Имя разработчика: " + repo.owner.login,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.owner.htmlUrl))
                        context.startActivity(intent)
                    },
                    textDecoration = TextDecoration.Underline,
                    color = Color.Green,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "Язык: " + repo.language,
                )
                Text(text = "Описание: " + (repo.description ?: "Нет описания проекта"), color = Color.Gray)
                HorizontalDivider()
            }
        }
    }
}

