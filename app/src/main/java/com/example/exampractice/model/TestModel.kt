package com.example.exampractice.model

public class TestModel(
    private var testID: String,
    private var topScore: Int,
    private var time: Int
) {

    fun getTestID():String{
        return testID
    }
    fun setTestID(newTestID:String){
        testID=newTestID
    }
    fun getTopScore():Int{
        return topScore
    }
    fun setTopScore(newTopScore:Int){
        topScore=newTopScore
    }
    fun getTime():Int{
        return time
    }
    fun setTime(newTime:Int){
        time=newTime
    }


}