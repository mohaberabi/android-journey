package com.mohaberabi.fliker.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


fun <VM> viewmodelFactory(
    viewmodel: () -> VM
): ViewModelProvider.Factory {
    return object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewmodel() as? T ?: throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}