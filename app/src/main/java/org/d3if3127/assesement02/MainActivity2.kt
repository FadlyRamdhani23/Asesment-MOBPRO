package org.d3if3127.assesement02

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import org.d3if3127.assesement02.model.HasilBmr
import org.d3if3127.assesement02.ui.MainViewModel
import org.d3if3127.assesment02.R
import org.d3if3127.assesment02.databinding.ActivityMain2Binding



class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener { hitungKaloriProtein() }
        binding.RESET.setOnClickListener{resetFunction()}
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel.getHasilBmr().observe(this, { showResult(it) })
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

        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(this, R.string.gender_invalid, Toast.LENGTH_LONG).show()
            return
        }

        viewModel.hitungKaloriProtein(
            berat.toFloat(),
            tinggi.toFloat(),
            umur.toFloat(),
            selectedId == R.id.priaRadioButton
        )


    }


    private fun showResult(result: HasilBmr?) {
        if (result == null) return
        binding.bmrTextView.text = getString(R.string.bmr_x, result.perbedaan)
        binding.proteinTextView.text = getString(R.string.protein_x, result.hasilProtein)

    }

}
