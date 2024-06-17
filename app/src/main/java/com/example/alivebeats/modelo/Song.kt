package com.example.alivebeats.modelo

data class Song(
    val id: String,
    val title:String,
    val artist:String,
    val url:String,
    val coverUrl:String,
){
    constructor():this("","","","","")

}
