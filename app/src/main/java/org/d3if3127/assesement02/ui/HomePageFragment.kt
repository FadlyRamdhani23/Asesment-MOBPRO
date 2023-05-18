package org.d3if3127.assesement02.ui


import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
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
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.button2.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_homePageFragment2_to_hitungFragment
            )
        }
        binding.button1.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_homePageFragment2_to_hitungBmrFragment
            )
        }
        setHasOptionsMenu(true)
        binding.imageView.setImageResource(R.drawable.image_homepage)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_about) {
            findNavController().navigate(
                R.id.action_homePageFragment2_to_aboutFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}