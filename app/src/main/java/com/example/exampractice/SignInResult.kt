package com.example.exampractice

import android.service.autofill.UserData

data class SignInResult(
    val data :Userdata?,
    val errorMessage:String?

)
data class Userdata(
    val userId:String,
    val username:String?,
    val profilePictureUrl:String?













)