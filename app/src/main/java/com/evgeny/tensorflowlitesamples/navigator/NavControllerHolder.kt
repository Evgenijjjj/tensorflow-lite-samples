package com.evgeny.tensorflowlitesamples.navigator

import androidx.navigation.NavController

interface NavControllerHolder {

    fun getNavController(): NavController
}

object NavControllerHolderStub : NavControllerHolder {

    override fun getNavController(): NavController {
        throw IllegalStateException()
    }
}
