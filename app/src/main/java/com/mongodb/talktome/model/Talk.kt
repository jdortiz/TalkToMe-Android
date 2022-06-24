package com.mongodb.talktome.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.Date
import java.util.UUID

class Talk() : RealmObject {
    @PrimaryKey
    var _id: String = UUID.randomUUID().toString()
    var title: String = ""
    var speaker: String = ""
    private var proposedDate: Date = Date()
    var scheduledDate: Date? = null

    constructor(title: String, speaker: String) : this() {
        this.title = title
        this.speaker = speaker
        this.scheduledDate = null
    }
}
