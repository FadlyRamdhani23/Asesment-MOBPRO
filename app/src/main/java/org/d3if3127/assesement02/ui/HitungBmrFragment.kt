package org.d3if3127.assesement02.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
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
        binding.saranbmrButton.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_hitungBmrFragment_to_saranBmrFragment
            )
        }
        binding.sharebmrButton.setOnClickListener { shareData() }
    }

    private fun shareData() {
        val selectedId = binding.radioGroup.checkedRadioButtonId
        val gender = if (selectedId == R.id.priaRadioButton)
            getString(R.string.pria)
        else
            getString(R.string.wanita)
        val message = getString(R.string.bagikan_templatebmr,
            binding.beratBadanInp.text,
            binding.UmurInp.text,
            binding.tinggiBadanInp.text,
            gender,
            binding.bmrTextView.text,
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
        binding.UmurInp.text?.clear()
        binding.radioGroup.clearCheck()
        binding.bmrTextView.visibility = View.GONE
        binding.proteinTextView.visibility = View.GONE
        binding.saranbmrButton.visibility = View.GONE
        binding.sharebmrButton.visibility = View.GONE


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
        binding.proteinTextView.visibility = View.VISIBLE
        binding.bmrTextView.visibility = View.VISIBLE
        binding.saranbmrButton.visibility = View.VISIBLE
        binding.sharebmrButton.visibility = View.VISIBLE
    //        binding.buttonGroup.visibility = View.VISIBLE
    }

}
