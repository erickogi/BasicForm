package com.kogicodes.basicform.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.kogicodes.basicform.CatRepository
import com.kogicodes.basicform.model.Data

class MainViewModel (application: Application) : AndroidViewModel(application) {
    private var catRepository: CatRepository = CatRepository(application)




    fun get() : LiveData<List<Data>> {

        return catRepository.getData()
    }

    fun add(data: Data) {

         catRepository.insert(data)
    }



}
