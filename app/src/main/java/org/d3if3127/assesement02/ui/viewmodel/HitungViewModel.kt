package org.d3if3127.assesement02.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3127.assesement02.db.DataDao
import org.d3if3127.assesement02.db.DataEntity
import org.d3if3127.assesement02.model.*

class HitungViewModel(private val db: DataDao) : ViewModel() {
    private val hasilBmi = MutableLiveData<HasilBmi?>()
    private val hasilBmr = MutableLiveData<HasilBmr?>()
    private val navigasi = MutableLiveData<KategoriBmi?>()
    val data = db.getLastData()
    fun hitungBmi(berat: Float, tinggi: Float, isMale: Boolean) {
        val dataBmi = DataEntity(
            berat = berat,
            tinggi = tinggi,
            isMale = isMale,
            umur = 0f,
            pilihan = "BMI"
        )
        hasilBmi.value = dataBmi.hitungData()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataBmi)
            }
        }
    }
     fun hitungKaloriProtein(berat: Float, tinggi: Float, umur: Float, isMale: Boolean){
            val dataBmr = DataEntity(
                berat = berat,
                tinggi = tinggi,
                isMale = isMale,
                umur = umur,
                pilihan = "BMR"
            )
         hasilBmr.value = dataBmr.hitungBmr()
         viewModelScope.launch {
             withContext(Dispatchers.IO) {

                 db.insert(dataBmr)
             }
         }
    }
    fun mulaiNavigasi() {
        navigasi.value = hasilBmi.value?.kategori
    }
    fun selesaiNavigasi() {
        navigasi.value = null
    }
    fun getNavigasi() : LiveData<KategoriBmi?> = navigasi

    fun getHasilBmi(): LiveData<HasilBmi?> = hasilBmi
    fun getHasilBmr(): LiveData<HasilBmr?> = hasilBmr
}