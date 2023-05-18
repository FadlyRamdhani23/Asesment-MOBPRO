package org.d3if3127.assesement02.model

import org.d3if3127.assesement02.db.DataEntity

fun DataEntity.hitungBmr(): HasilBmr {
    val bmr = (10 * berat) + (6.25 * tinggi) - (5 * umur)
    val protein = if(isMale){
        (berat * 2)
    }else{
        (berat * 1.5)
    }
    val hasilProtein = protein.toFloat()

    val perbedaan = if(isMale){
        bmr + 5
    }else{
        bmr -161
    }
    return HasilBmr(bmr, perbedaan, hasilProtein)
}