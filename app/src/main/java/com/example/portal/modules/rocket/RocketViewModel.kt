package com.example.portal.modules.rocket

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portal.modules.base.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RocketViewModel @Inject constructor(
    private val repository: RocketRepository
) : ViewModel() {

    //
    private val _yearLiveData =
        MutableLiveData<List<Int>>()
    val yearLiveData: LiveData<List<Int>>
        get() = _yearLiveData

    private val _rocketItemList =
        MutableLiveData<Result<List<RocketItem>?>>()
    val rocketItemList: LiveData<Result<List<RocketItem>?>>
        get() = _rocketItemList

    private val originalList = mutableListOf<RocketItem>()

    init {
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _rocketItemList.postValue(Result.loading(data = null))
            val rocketItemResult = repository.getRocketItemResult()
            rocketItemResult.data?.let { originalList.addAll(it) }
            _rocketItemList.postValue(rocketItemResult)
            createFilterList(rocketItemResult.data)

        }
    }

    fun filterList(launchYear: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _rocketItemList.postValue(Result.success(
                if (launchYear != -1) {
                    originalList.filter {
                        it.launchYear == launchYear
                    }
                } else {
                    originalList
                }
            ))
        }
    }

    private fun createFilterList(list: List<RocketItem>?) {
        val launchYearList = mutableListOf<Int>()
        list?.let {
            it.distinctBy { it.launchYear }.sortedBy { it.launchYear }.forEach { rocketItem ->
                launchYearList.add(rocketItem.launchYear)
            }
        }
        launchYearList.add(0, -1)
        _yearLiveData.postValue(launchYearList)
    }
}