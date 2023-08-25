package com.example.exampractice

public class TestModel {
    private var testID:String
    private var topScore:Int
    private var  time:Int

    constructor(testID:String,topScore:Int,time:Int){
        this.testID=testID
        this.topScore=topScore
        this.time=time

    }
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