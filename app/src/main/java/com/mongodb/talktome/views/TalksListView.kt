package com.mongodb.talktome.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mongodb.talktome.R
import com.mongodb.talktome.viewmodels.TalkEntryViewModel
import com.mongodb.talktome.viewmodels.TalksListViewModel

@ExperimentalMaterialApi
@Composable
fun TalksListView(viewModel: TalksListViewModel) {
    // <editor-fold desc="State">
    var showDialog: MutableState<Boolean> = remember { mutableStateOf(false) }
    // </editor-fold>
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                showDialog.value = true
                // viewModel.addButtonTapped()
            }) {
                Icon(Icons.Filled.Add, "Add talks")
            }
        }
    ) {
        TalksList(viewModel = viewModel)
        if (showDialog.value) {
            Dialog(
                onDismissRequest = {
                    showDialog.value = false
                },
                properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
            ) {
                TalkEntryView(viewModel = TalkEntryViewModel(viewModel.realm), dialogState = showDialog)
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun TalksList(viewModel: TalksListViewModel) {
    val talks by viewModel.talks.observeAsState(emptyList())
    LazyColumn {
        items(items = talks, key = { talk -> talk._id }) { talk ->
            val dismissState = rememberDismissState()
            if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                viewModel.removeTalk(talk)
            }
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    val direction = dismissState.dismissDirection
                    if (direction == DismissDirection.EndToStart) {
                        val color = Color.Red
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(8.dp)
                        ) {
                            Column(modifier = Modifier.align(Alignment.CenterEnd)) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                            }
                        }
                    }
                }
            ) {
                TalksListItem(title = talk.title, author = talk.speaker)
            }
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
