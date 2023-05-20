package org.d3if3127.assesement02.ui.histori

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.d3if3127.assesement02.db.DataDb
import org.d3if3127.assesement02.ui.viewmodel.HistoriViewModel
import org.d3if3127.assesement02.ui.viewmodel.HistoriViewModelFactory
import org.d3if3127.assesment02.R
import org.d3if3127.assesment02.databinding.FragmentHistoriBinding


class HistoriFragment : Fragment() {

    private val viewModel: HistoriViewModel by lazy {
        val db = DataDb.getInstance(requireContext())
        val factory = HistoriViewModelFactory(db.dao)
        setHasOptionsMenu(true)
        ViewModelProvider(this, factory)[HistoriViewModel::class.java]
    }
    private lateinit var binding: FragmentHistoriBinding
    private lateinit var myAdapter: HistoriAdapter

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.histori_menu, menu)

    }
    private fun hapusData() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.konfirmasi_hapus)
            .setPositiveButton(getString(R.string.hapus)) { _, _ ->
                viewModel.hapusData()
            }
            .setNegativeButton(getString(R.string.batal)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    // fun delete data by id masih belum berjalan untuk kedepannya
//    private fun deleteDataById(id: Long) {
//        MaterialAlertDialogBuilder(requireContext())
//            .setMessage(R.string.konfirmasi_hapus)
//            .setPositiveButton(getString(R.string.hapus)) { _, _ ->
//                viewModel.deleteDataById(id)
//            }
//            .setNegativeButton(getString(R.string.batal)) { dialog, _ ->
//                dialog.cancel()
//            }
//            .show()
//    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_hapus -> {
                hapusData()
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHistoriBinding.inflate(layoutInflater,
            container, false)
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myAdapter = HistoriAdapter()
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }
        viewModel.data.observe(viewLifecycleOwner, {
            binding.emptyView.visibility = if (it.isEmpty())
                View.VISIBLE else View.GONE
            myAdapter.submitList(it)
        })

    }

}