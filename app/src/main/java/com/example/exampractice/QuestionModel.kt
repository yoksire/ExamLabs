package com.example.exampractice

class QuestionModel(question: String, optionA: String, optionB: String, optionC: String, optionD: String, correctAnswer: Int) {
    private lateinit var _question: String
    private lateinit var _optionA: String
    private lateinit var _optionB: String
    private lateinit var _optionC: String
    private lateinit var _optionD: String
    private var _correctAnswer: Int = 0

    init {
        _question = question
        _optionA = optionA
        _optionB = optionB
        _optionC = optionC
        _optionD = optionD
        _correctAnswer = correctAnswer
    }

    // Getter and Setter for 'question'
    var question
        get() = _question
        set(value) {
            _question = value
        }

    // Getter and Setter for 'optionA'
    var optionA
        get() = _optionA
        set(value) {
            _optionA = value
        }

    // Getter and Setter for 'optionB'
    var optionB
        get() = _optionB
        set(value) {
            _optionB = value
        }

    // Getter and Setter for 'optionC'
    var optionC
        get() = _optionC
        set(value) {
            _optionC = value
        }

    // Getter and Setter for 'optionD'
    var optionD
        get() = _optionD
        set(value) {
            _optionD = value
        }

    // Getter and Setter for 'correctAnswer'
    var correctAnswer
        get() = _correctAnswer
        set(value) {
            _correctAnswer = value
        }
}
