package com.mongodb.talktome

import android.app.Application
import android.util.Log
import com.mongodb.talktome.model.Talk
import io.realm.kotlin.Realm
import io.realm.kotlin.internal.platform.runBlocking
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.Credentials
import io.realm.kotlin.mongodb.sync.SyncConfiguration

class TalkToMeApp : Application() {
    lateinit var realm: Realm

    companion object {
        const val APP_ID = "application-id"

    }

    override fun onCreate() {
        super.onCreate()
        val app = App.create(APP_ID)
        println("Hello")
        val NAME_QUERY = "TALKS_QUERY"
        runBlocking {
            val user = app.login(Credentials.anonymous())
            val config = SyncConfiguration.Builder(user, setOf(Talk::class))
                .log(LogLevel.DEBUG)
                .maxNumberOfActiveVersions(100)
                .initialSubscriptions { realm ->
                    add(
                        realm.query<Talk>(
                            Talk::class,
                            "ownerId == $0",
                            user.id
                        ),
                        NAME_QUERY
                    )
                }
                .waitForInitialRemoteData()
                .build()
            realm = Realm.open(config)
            Log.v("Realm", "Successfully opened realm: ${realm.configuration}")
        }
    }
}
