package org.d3if3127.assesement02.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import org.d3if3127.assesment02.R
import org.d3if3127.assesment02.databinding.FragmentHomepageBinding


class HomePageFragment : Fragment() {
    private lateinit var binding: FragmentHomepageBinding

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentHomepageBinding.inflate(layoutInflater, container, false)
        binding.button1.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_homePageFragment2_to_hitungFragment
            )
        }
        binding.button2.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_homePageFragment2_to_hitungBmrFragment
            )
        }
        return binding.root
    }
}