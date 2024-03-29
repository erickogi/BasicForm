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


import android.net.Uri
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {
    var gson = Gson()

    @TypeConverter
    fun uriFromString(value: String?): Uri? {

        return if (value == null) {
            null
        } else Uri.parse(value)
    }

    @TypeConverter
    fun uriToString(uri: Uri?): String? {
        if (uri == null) {
            return ""
        }
        return uri.toString()
    }

}
