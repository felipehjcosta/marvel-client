package com.github.felipehjcosta.marvelclient

import kotlinx.coroutines.*

internal actual val ApplicationDispatcher: CoroutineDispatcher = Dispatchers.Unconfined