/*
 * Copyright 2019 Eric Kogi. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kogicodes.basicform


import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import com.kogicodes.basicform.dao.dataDao
import com.kogicodes.basicform.model.Data
import com.kogicodes.pawametinderforcats.data.AppDatabase

class CatRepository(application: Application) {
    private val dataData: dataDao

    private val db: AppDatabase = AppDatabase.getDatabase(application)!!
   
    private val context: Context

    init {
        dataData = db.dataDao()
        context = application.applicationContext
    }


     fun insert(data: Data) {
        dataData.insert(data)

        
    }

    

    fun getData(): LiveData<List<Data>> {

        return dataData.getAll()
    }

  




}
