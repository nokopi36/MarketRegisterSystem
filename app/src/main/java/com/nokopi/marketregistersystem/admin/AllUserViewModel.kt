package com.nokopi.marketregistersystem.admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.nokopi.marketregistersystem.data.User
import com.nokopi.marketregistersystem.data.UserDatabaseDao

class AllUserViewModel(database: UserDatabaseDao) : ViewModel() {

    var userList: LiveData<List<User>> = database.getUserName()



}