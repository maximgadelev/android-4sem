package com.example.web_app.domain.entity

data class Weather (
   var id:Int,
   var name:String,
   var temp:Double,
   var tempMax:Double,
   var tempMin:Double,
   var humidity:Int,
   var pressure:Int,
   var  sunrise:Int,
   var  sunset:Int,
   var speed:Double,
   var deg:Int
        )