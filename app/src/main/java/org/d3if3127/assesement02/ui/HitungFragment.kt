package org.d3if3127.assesement02.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if3127.assesement02.model.HasilBmi
import org.d3if3127.assesement02.model.KategoriBmi
import org.d3if3127.assesment02.R
import org.d3if3127.assesment02.databinding.FragmentHitungBinding


class HitungFragment : Fragment() {

    private lateinit var binding: FragmentHitungBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHitungBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener { hitungBmi() }
        binding.RESET.setOnClickListener{resetFunction()}
        viewModel.getHasilBmi().observe(requireActivity(), { showResult(it) })
        binding.saranButton.setOnClickListener { viewModel.mulaiNavigasi() }
        binding.shareButton.setOnClickListener { shareData() }
        viewModel.getNavigasi().observe(viewLifecycleOwner, {
            if (it == null) return@observe
            findNavController().navigate(HitungFragmentDirections
                .actionHitungFragmentToSaranFragment(it))
            viewModel.selesaiNavigasi()
        })
    }

    private fun shareData() {
        val selectedId = binding.radioGroup.checkedRadioButtonId
        val gender = if (selectedId == R.id.priaRadioButton)
            getString(R.string.pria)
        else
            getString(R.string.wanita)
        val message = getString(R.string.bagikan_template,
            binding.beratBadanInp.text,
            binding.tinggiBadanInp.text,
            gender,
            binding.bmiTextView.text,
            binding.kategoriTextView.text
        )
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager) != null) {
            startActivity(shareIntent)
        }
    }

        private fun resetFunction(){
            binding.beratBadanInp.text?.clear()
            binding.tinggiBadanInp.text?.clear()
            binding.radioGroup.clearCheck()
            binding.bmiTextView.visibility = View.GONE
            binding.kategoriTextView.visibility = View.GONE
            binding.saranButton.visibility = View.GONE
            binding.shareButton.visibility = View.GONE
    }
    private fun hitungBmi() {

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

        val selectedId = binding.radioGroup.checkedRadioButtonId
        if (selectedId == -1) {
            Toast.makeText(context, R.string.gender_invalid, Toast.LENGTH_LONG).show()
            return
        }
        viewModel.hitungBmi(
            berat.toFloat(),
            tinggi.toFloat(),
            selectedId == R.id.priaRadioButton
        )


    }
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
    private fun hitungBmi(berat: Float, tinggi: Float, isMale: Boolean): HasilBmi {
        val tinggiCm = tinggi / 100
        val bmi = berat / (tinggiCm * tinggiCm)
        val kategori = getKategori(bmi, isMale)
        return HasilBmi(bmi, kategori)
    }
    private fun getKategoriLabel(kategori: KategoriBmi): String {
        val stringRes = when (kategori) {
            KategoriBmi.KURUS -> R.string.kurus
            KategoriBmi.IDEAL -> R.string.ideal
            KategoriBmi.GEMUK -> R.string.gemuk
        }
        return getString(stringRes)
    }
    private fun showResult(result: HasilBmi?) {
        if (result == null) return
        binding.bmiTextView.text = getString(R.string.bmi_x, result.bmi)
        binding.kategoriTextView.text = getString(R.string.kategori_x,
            getKategoriLabel(result.kategori))
        binding.bmiTextView.visibility = View.VISIBLE
        binding.kategoriTextView.visibility = View.VISIBLE
        binding.buttonGroup.visibility = View.VISIBLE
    }
}