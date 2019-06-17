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

package com.kogicodes.basicform.dao


import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import com.kogicodes.basicform.model.Data


@Dao
interface dataDao {
    @Query("SELECT * FROM DATA   ")
    fun getAll(): LiveData<List<Data>>




    @Insert(onConflict = IGNORE)
    fun insert(model: Data)

    @Update
    fun update(model: Data)

    @Delete
    fun delete(model: Data)

    @Query("DELETE FROM DATA")
    fun delete()
}
