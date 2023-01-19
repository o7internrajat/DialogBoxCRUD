package com.o7solutions.dialogboxcrud.interfaces

import com.o7solutions.dialogboxcrud.models.UserData

interface ClickInterface {
    fun editClick(userData: UserData, position: Int)
    fun deleteClick(userData: UserData, position: Int)

}