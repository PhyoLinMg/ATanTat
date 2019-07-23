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
    fun convert(string:String):Array<String>{
        val strs = string.split(":").toTypedArray()
        val strgg=strs[1].split(" ").toTypedArray()
        val finalarray= arrayOf(strs[0],strgg[0],strgg[1])
        return finalarray
    }
    fun twentyfourhrformat(array:Array<String>):Int {
        var hour=array[0].toInt()
        val time=array[2]
        if(time!="AM")
            hour+=12
        return hour

    }



}