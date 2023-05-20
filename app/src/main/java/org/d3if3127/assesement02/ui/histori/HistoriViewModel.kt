package org.d3if3127.assesement02.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3127.assesement02.db.DataDao

class HistoriViewModel(private val db: DataDao) : ViewModel() {
    val data = db.getLastData()
    fun hapusData() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.clearData()
        }
    }
}