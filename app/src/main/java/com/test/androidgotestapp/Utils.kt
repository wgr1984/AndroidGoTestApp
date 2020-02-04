package com.test.androidgotestapp

import gotestlib.Api
import gotestlib.PhotoWrapper
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

sealed class Either<A, B> {
    class Left<A, B>(val left: A) : Either<A, B>()
    class Right<A, B>(val right: B) : Either<A, B>()
}

typealias Result<V> = Either<Exception, V>
typealias Failure<V> = Either.Left<Exception, V>
typealias Success<V> = Either.Right<Exception, V>

suspend fun Api.loadPhotos(): Result<PhotoWrapper> = suspendCoroutine {
    this.getPhotos { photos, err ->
        if (err != null) {
            it.resume(Failure(err))
            return@getPhotos
        }
        it.resume(Success(photos))
    }
}
