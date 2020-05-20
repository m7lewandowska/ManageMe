package com.example.managemev2

data class Movie(val title: String = "", val director:String = "",  val genre:String= "",
                 val productionYear:String= "", val rating:String="", val watched:String="0" )
//class Movie(_title:String, _director:String, _genre:String, _productionYear:String )
//{
//    var title : String
//    var director : String
//    var genre : String
//    var productionYear : String
//
//    init{
//        this.title  =_title
//        this.director = _director
//        this.genre = _genre
//        this.productionYear = _productionYear
//    }
//}