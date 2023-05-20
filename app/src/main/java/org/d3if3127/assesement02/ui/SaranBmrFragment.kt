package org.d3if3127.assesement02.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.d3if3127.assesment02.R
import org.d3if3127.assesment02.databinding.FragmentSaranbmrBinding

class SaranBmrFragment : Fragment() {

    private lateinit var binding: FragmentSaranbmrBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentSaranbmrBinding.inflate(layoutInflater, container, false)
        binding.imageView.setImageResource(R.drawable.lengkapi_kebutuhan)
        binding.textView.text = getString(R.string.saran_bmr)

        return binding.root
    }
}