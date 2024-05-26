package com.example.alivebeats.modelo

data class Category(
    val name:String,
    val url:String,
    val songs:List<String>

    ){
    constructor() : this("","", listOf())

}
