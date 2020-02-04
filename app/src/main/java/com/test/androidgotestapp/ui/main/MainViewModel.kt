package com.test.androidgotestapp.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import gotestlib.PhotosCallback

class MainViewModel : ViewModel() {
    val api = gotestlib.Api()

    fun loadPhotos() {
        api.getPhotos { photos, err ->
            if (err != null) {
                Log.e(MainViewModel::class.simpleName, "load error", err)
                return@getPhotos
            }
            for (i in 0..photos.itemsCount) {
                println(photos.getItem(i)?.title)
            }
        }
    }
}
