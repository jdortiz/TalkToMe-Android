package com.mongodb.talktome

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mongodb.talktome.model.Talk
import com.mongodb.talktome.ui.theme.TalkToMeTheme
import com.mongodb.talktome.viewmodels.TalksListViewModel
import com.mongodb.talktome.views.TalksListView
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = RealmConfiguration.Builder(schema = setOf(Talk::class))
            .build()
        val realm: Realm = Realm.open(config)
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
