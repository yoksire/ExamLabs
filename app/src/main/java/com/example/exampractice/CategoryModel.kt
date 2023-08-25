package com.example.exampractice

class CategoryModel {
    // Private variables
    private var docId: String
    private var name: String
    private var numTest: Int

    // Constructor
    constructor(docId: String, name: String, numTest: Int) {
        this.docId = docId
        this.name = name
        this.numTest = numTest
    }

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
