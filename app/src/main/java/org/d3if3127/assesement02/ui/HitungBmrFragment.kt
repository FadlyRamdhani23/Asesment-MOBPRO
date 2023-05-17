package org.d3if3127.assesement02.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.d3if3127.assesement02.model.HasilBmr
import org.d3if3127.assesment02.R
import org.d3if3127.assesment02.databinding.FragmentHitungbmrBinding


class HitungBmrFragment : Fragment() {
    private lateinit var binding: FragmentHitungbmrBinding
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHitungbmrBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener { hitungKaloriProtein() }
        binding.RESET.setOnClickListener{resetFunction()}
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
            Toast.makeText(context, R.string.berat_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val tinggi = binding.tinggiBadanInp.text.toString()
        if (TextUtils.isEmpty(tinggi)) {
            Toast.makeText(context, R.string.tinggi_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val umur = binding.UmurInp.text.toString()
        if (TextUtils.isEmpty(umur)) {
            Toast.makeText(context, R.string.Umur_invalid, Toast.LENGTH_LONG).show()
            return
        }

        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(context, R.string.gender_invalid, Toast.LENGTH_LONG).show()
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
