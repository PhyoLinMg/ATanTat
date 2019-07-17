package com.elemental.atantat.utils


class Calculations {

    fun calculatePercentage(yes:Int,no:Int):Int{
        val total:Double=yes.toDouble()+no.toDouble()
        var percentage:Double=0.0
        if(total==0.0){
            percentage=0.0
        }
       else{
            percentage=(yes/total)*100
        }
        return percentage.toInt()

    }




}