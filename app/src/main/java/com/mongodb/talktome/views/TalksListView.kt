package com.mongodb.talktome.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mongodb.talktome.R
import com.mongodb.talktome.viewmodels.TalksListViewModel

@Composable
fun TalksListView(viewModel: TalksListViewModel) {
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.addButtonTapped()
            }) {
                Icon(Icons.Filled.Add, "Add talks")
            }
        }
    ) {
        TalksList(viewModel = viewModel)
    }
}

@Composable
fun TalksList(viewModel: TalksListViewModel) {
    val talks by viewModel.talks.observeAsState(emptyList())
    LazyColumn {
        items(talks) { talk ->
            TalksListItem(title = talk.title, author = talk.speaker)
            Divider()
        }
    }
}

@Composable
fun TalksListItem(title: String, author: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h5
        )
        Text(
            text = author,
            style = MaterialTheme.typography.h6
        )
    }
}
