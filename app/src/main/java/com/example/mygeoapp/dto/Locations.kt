package com.example.mygeoapp.dto

data class Locations (var name: String = "", var latitude: String = "", var longitude: String = ""){
    override fun toString(): String {
        return "$name"
    }
}