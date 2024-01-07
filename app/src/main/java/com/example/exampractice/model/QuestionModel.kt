package com.example.exampractice.model

class QuestionModel(question: String, optionA: String, optionB: String, optionC: String, optionD: String, correctAnswer: Int,selectedAnswer: Int,status:Int) {
    private var _question: String
    private var _optionA: String
    private var _optionB: String
    private var _optionC: String
    private var _optionD: String
    private var _correctAnswer: Int = 0
    private var _selectedAnswer: Int = 0
    private var _status:Int=0

    init {
        _question = question
        _optionA = optionA
        _optionB = optionB
        _optionC = optionC
        _optionD = optionD
        _correctAnswer = correctAnswer
        _selectedAnswer = selectedAnswer
        _status=status
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
    // Getter and Setter for 'selectedAnswer'
    var selectedAnswer
        get() = _selectedAnswer
        set(value) {
            _selectedAnswer = value
        }
    // Getter and Setter for 'status'
    var status
        get() = _status
        set(value) {
            _status = value
        }
}
