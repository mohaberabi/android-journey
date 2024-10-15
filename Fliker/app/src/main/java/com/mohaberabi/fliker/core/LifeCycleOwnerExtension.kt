package com.mohaberabi.fliker.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


fun <F> LifecycleOwner.collectLifeCycleFlow(
    flow: Flow<F>,
    block: suspend (F) -> Unit
) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect {
                block(it)
            }
        }
    }
}

fun <F> Fragment.collectLifeCycleFlow(
    flow: Flow<F>,
    block: suspend (F) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collect {
                block(it)
            }
        }
    }
}