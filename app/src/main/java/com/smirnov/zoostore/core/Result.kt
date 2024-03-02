package com.smirnov.zoostore.core

sealed class Result<T> {
    class Success<T>(val value: T): Result<T>()
    class Error<T>(val error: Exception): Result<T>()
}
