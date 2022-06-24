package com.mongodb.talktome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mongodb.talktome.ui.theme.TalkToMeTheme
import com.mongodb.talktome.viewmodels.TalksListViewModel
import com.mongodb.talktome.views.TalksListView
import io.realm.kotlin.Realm

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val realm: Realm = (application as? TalkToMeApp)?.realm !!
        val viewModel = TalksListViewModel(realm = realm)
        setContent {
            TalkToMeTheme {
                TalksListView(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    TalkToMeTheme {
        Greeting("Android")
    }
}
