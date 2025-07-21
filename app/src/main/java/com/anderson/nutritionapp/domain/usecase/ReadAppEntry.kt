package com.anderson.nutritionapp.domain.usecase

import com.anderson.nutritionapp.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(private val localUserManager: LocalUserManager) {

    operator fun invoke() : Flow <Boolean>{
        return localUserManager.readAppEntry()
    }

}