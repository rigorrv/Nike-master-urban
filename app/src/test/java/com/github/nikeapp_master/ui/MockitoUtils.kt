package com.github.nikeapp_master.ui

import org.mockito.Mockito

inline fun <reified T> mock(): T = Mockito.mock(T::class.java)