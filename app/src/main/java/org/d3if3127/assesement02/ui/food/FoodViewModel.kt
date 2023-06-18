package org.d3if3127.assesement02.ui.food

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3127.assesement02.data.SettingDataStore
import org.d3if3127.assesement02.data.dataStore
import org.d3if3127.assesement02.model.Food
import org.d3if3127.assesement02.network.ApiStatus
import org.d3if3127.assesement02.network.MakananApi
import org.d3if3127.assesment02.R

class FoodViewModel : ViewModel() {


    private val data = MutableLiveData<List<Food>>()
    private val status = MutableLiveData<ApiStatus>()
    init {
        retrieveData()
    }
    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(MakananApi.service.getMakanan())
                status.postValue(ApiStatus.SUCCESS)
            } catch (e: Exception) {
                Log.d("FoodViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)
            }
        }
    }

    fun getData(): LiveData<List<Food>> = data
    fun getStatus(): LiveData<ApiStatus> = status
}