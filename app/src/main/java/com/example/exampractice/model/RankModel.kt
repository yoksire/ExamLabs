package com.example.exampractice.model

class RankModel(private var score: Int, private var rank: Int) {

    fun getScore(): Int {
        return score
    }

    fun setScore(newScore: Int) {
        score = newScore
    }

    fun getRank(): Int {
        return rank
    }

    fun setRank(newRank: Int) {
        rank = newRank
    }
}