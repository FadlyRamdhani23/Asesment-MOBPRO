package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.myapplication.databinding.ActivityMain2Binding



class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener { hitungKaloriProtein() }
        binding.RESET.setOnClickListener{resetFunction()}
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun resetFunction(){
        binding.beratBadanInp.text?.clear()
        binding.tinggiBadanInp.text?.clear()
        binding.UmurInp.text?.clear()
        binding.radioGroup.clearCheck()
    }
    @SuppressLint("StringFormatMatches")
    private fun hitungKaloriProtein() {


        val berat = binding.beratBadanInp.text.toString()
        if (TextUtils.isEmpty(berat)) {
            Toast.makeText(this, R.string.berat_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggi = binding.tinggiBadanInp.text.toString()
        if (TextUtils.isEmpty(tinggi)) {
            Toast.makeText(this, R.string.tinggi_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val umur = binding.UmurInp.text.toString()
        if (TextUtils.isEmpty(umur)) {
            Toast.makeText(this, R.string.Umur_invalid, Toast.LENGTH_LONG).show()
            return
        }


//        val tinggiCm = tinggi.toFloat() / 100
        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(this, R.string.gender_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val isMale = selectedId == R.id.priaRadioButton
//        val bmi = berat.toFloat() / (tinggiCm * tinggiCm)
        val bmr = (10 * berat.toFloat()) + (6.25 * tinggi.toFloat()) - (5 * umur.toFloat())
        val protein = if(isMale){
            (berat.toFloat() * 2)
        }else{
            (berat.toFloat() * 1.5)
        }

        val perbedaan = if(isMale){
            bmr + 5

        }else{
            bmr -161
        }
//        val kategori = getKategori(bmi, isMale)
//        binding.bmiTextView.text = getString(R.string.bmi_x, bmi)
        binding.bmrTextView.text = getString(R.string.bmr_x, perbedaan)
        binding.proteinTextView.text = getString(R.string.protein_x, protein)
//        binding.kategoriTextView.text = getString(R.string.kategori_x, kategori)
    }



    //    fun calculateProtein(weight: Int): Int {
//        return (weight * 1.5).toInt()
//    }
//    private fun getKategori(bmi: Float, isMale: Boolean): String {
//        val stringRes = if (isMale) {
//            when {
//                bmi < 20.5 -> R.string.kurus
//                bmi >= 27.0 -> R.string.gemuk
//                else -> R.string.ideal
//            }
//        } else {
//            when {
//                bmi < 18.5 -> R.string.kurus
//                bmi >= 25.0 -> R.string.gemuk
//                else -> R.string.ideal
//            }
//        }
//        return getString(stringRes)
//    }
}
