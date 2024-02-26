package com.example.pressurenote.di

import com.example.pressurenote.ui.healthlist.adapters.indication.IndicationAdapter
import javax.inject.Inject
import javax.inject.Provider

class IndicationAdapterProvider @Inject constructor() : Provider<IndicationAdapter> {
    override fun get(): IndicationAdapter {
        return IndicationAdapter()
    }
}