package org.d3if3127.assesement02.ui.hitung

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3127.assesement02.db.DataDao
import org.d3if3127.assesement02.db.DataEntity
import org.d3if3127.assesement02.model.HasilBmi
import org.d3if3127.assesement02.model.HasilBmr
import org.d3if3127.assesement02.model.KategoriBmi
import org.d3if3127.assesement02.model.hitungData

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
        )
        hasilBmi.value = dataBmi.hitungData()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                db.insert(dataBmi)
            }
        }
    }
     fun hitungKaloriProtein(berat: Float, tinggi: Float, umur: Float, isMale: Boolean){
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
        hasilBmr.value = HasilBmr(bmr, perbedaan, hasilProtein)
         viewModelScope.launch {
             withContext(Dispatchers.IO) {
                 val dataBmi = DataEntity(
                     berat = berat,
                     tinggi = tinggi,
                     isMale = isMale,
                     umur = umur,
                 )
                 db.insert(dataBmi)
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
    private fun getKategori(bmi: Float, isMale: Boolean): KategoriBmi {
        val kategori = if (isMale) {
            when {
                bmi < 20.5 -> KategoriBmi.KURUS
                bmi >= 27.0 -> KategoriBmi.GEMUK
                else -> KategoriBmi.IDEAL
            }
        } else {
            when {
                bmi < 18.5 -> KategoriBmi.KURUS
                bmi >= 25.0 -> KategoriBmi.GEMUK
                else -> KategoriBmi.IDEAL
            }
        }
        return kategori
    }
    fun getHasilBmi(): LiveData<HasilBmi?> = hasilBmi
    fun getHasilBmr(): LiveData<HasilBmr?> = hasilBmr
}