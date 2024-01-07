package com.example.exampractice.model

class ProfileModel(private var name:String,private var email:String) {

        fun getName():String{
            return name
        }
        fun setName(newName:String){
            name=newName
        }
        fun getEmail():String{
            return email
        }
        fun setEmail(newEmail:String){
            email=newEmail
        }
}