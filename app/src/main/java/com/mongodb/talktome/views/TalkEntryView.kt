package com.mongodb.talktome.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import com.mongodb.talktome.viewmodels.TalkEntryViewModel

@Composable
fun TalkEntryView(viewModel: TalkEntryViewModel, dialogState: MutableState<Boolean>) {
    val titleField = remember { mutableStateOf("") }
    val speakerField = remember { mutableStateOf("") }

    Card(
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Talk",
                    style = MaterialTheme.typography.h4
                )
                IconButton(
                    modifier = Modifier.then(Modifier.size(24.dp)),
                    onClick = {
                        dialogState.value = false
                    }
                ) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "",
                        tint = MaterialTheme.colors.primaryVariant,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            CustomTextField(
                text = titleField,
                placeholder = "Talk title",
                leadingImage = Icons.Filled.List
            )
            Spacer(modifier = Modifier.height(8.dp))
            CustomTextField(
                text = speakerField,
                placeholder = "Speaker Name",
                leadingImage = Icons.Default.Person
            )

            Spacer(modifier = Modifier.height(20.dp))

            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        viewModel.confirmData(name = speakerField.value, title = titleField.value)
                        dialogState.value = false
                    },
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = titleField.value.isNotEmpty() && speakerField.value.isNotEmpty()
                ) {
                    Text(text = "Done")
                }
            }
        }
    }
}

@Composable
fun CustomTextField(text: MutableState<String>, placeholder: String, leadingImage: ImageVector) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colors.primaryVariant
                ),
                shape = RoundedCornerShape(50)
            ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        ),
        leadingIcon = {
            Icon(
                imageVector = leadingImage,
                contentDescription = "",
                tint = MaterialTheme.colors.secondary,
            )
        },
        placeholder = { Text(text = placeholder) },
        value = text.value,
        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
        onValueChange = {
            text.value = it
        }
    )
}

// @SuppressLint("UnrememberedMutableState")
// @Preview
// @Composable
// fun TalkEntryDialogPreview() {
//     TalkToMeTheme {
//         TalkEntryView(viewModel = TalkEntryViewModel(), dialogState = mutableStateOf(true))
//     }
// }
