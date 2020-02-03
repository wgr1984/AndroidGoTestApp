package com.test.androidgotestapp.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    val api = gotestlib.Api()

    fun loadPhotos() {
        val photos = api.photos
        for (i in 0..photos.itemsCount) {
            println(photos.getItem(i)?.title)
        }
    }
}
