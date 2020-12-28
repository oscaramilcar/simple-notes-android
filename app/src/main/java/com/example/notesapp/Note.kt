package com.example.notesapp

import com.google.firebase.firestore.Exclude

class Note(val title:String? = null, val note:String? = null, val createdAt:String? = null) {
    @Exclude
    @set:Exclude
    @get:Exclude
    var uid: String? = null

    constructor():this(null,null,null){}
}