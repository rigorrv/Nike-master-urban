package com.github.nikeapp_master.ui

interface Communicator {

    fun getQuery(query : String, sort : Boolean)
    fun playSound(url : String)
    fun playError()
    fun showFragment()
}
