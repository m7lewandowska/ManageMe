package com.example.managemev2

class Movie(_title:String, _director:String, _genre:String, _productionYear:String ) {

    var title : String
    var director : String
    var genre : String
    var productionYear : String

    init{
        this.title  =_title
        this.director = _director
        this.genre = _genre
        this.productionYear = _productionYear

    }
}