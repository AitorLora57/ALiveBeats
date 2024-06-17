package com.example.alivebeats.modelo

data class Album(
    val name:String,
    val url:String,
    val artist:String,
    var songs:List<String>

    ){
    constructor() : this("","", "", listOf())

}
