package com.test.androidgotestapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.androidgotestapp.loadPhotos
import gotestlib.Photo
import kotlinx.coroutines.launch
import com.test.androidgotestapp.Failure
import com.test.androidgotestapp.Success
import gotestlib.PhotoWrapper

class MainViewModel : ViewModel() {

    private val api = gotestlib.Api()

    private val mutPhotos = MutableLiveData<List<Photo>>()
    val photos: LiveData<List<Photo>> = mutPhotos

    fun loadPhotos() {
        viewModelScope.launch {
            val loadedPhotos = api.loadPhotos()
            if (loadedPhotos is Success<PhotoWrapper>) {
                val photoList = loadedPhotos.right.run { 0..itemsCount }.map(loadedPhotos.right::getItem)
                mutPhotos.postValue(photoList)
            } else if (loadedPhotos is Failure<PhotoWrapper>) {
                println("Erro: ${loadedPhotos.left}")
                mutPhotos.postValue(listOf(Photo().apply { url = "not found !" }))
            }
        }
    }
}
