package org.d3if3127.assesement02.ui.histori

import androidx.lifecycle.ViewModel
import org.d3if3127.assesement02.db.DataDao

class HistoriViewModel(db: DataDao) : ViewModel() {
    val data = db.getLastData()
}