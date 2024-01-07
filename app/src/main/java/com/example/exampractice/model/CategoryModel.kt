package com.example.exampractice.model

class CategoryModel// Constructor
    (// Private variables
    private var docId: String, private var name: String, private var numTest: Int
) {

    // Getter and setter for docId
    fun getDocId(): String {
        return docId
    }

    fun setDocId(newDocId: String) {
        docId = newDocId
    }

    // Getter and setter for name
    fun getName(): String {
        return name
    }

    fun setName(newName: String) {
        name = newName
    }

    // Getter and setter for numTest
    fun getNumTest(): Int {
        return numTest
    }

    fun setNumTest(newNumTest: Int) {
        numTest = newNumTest
    }
}
