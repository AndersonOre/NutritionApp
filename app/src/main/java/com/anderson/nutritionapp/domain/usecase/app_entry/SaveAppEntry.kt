package com.anderson.nutritionapp.domain.usecase.app_entry

import com.anderson.nutritionapp.domain.manager.LocalUserManager


class SaveAppEntry (private val localUserManager: LocalUserManager){

    suspend operator fun invoke() {
        localUserManager.saveAppEntry()
    }

}