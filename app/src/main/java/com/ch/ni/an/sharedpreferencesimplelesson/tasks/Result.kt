package com.ch.ni.an.sharedpreferencesimplelesson.tasks

sealed class Result<T>

class SuccessResult<T>(
    data: T
): Result<T>()

class ErrorResult<T>(
    val error: Throwable
): Result<T>()

class PendingResult<T>: Result<T>()

class EmptyResult<T>: Result<T>()
