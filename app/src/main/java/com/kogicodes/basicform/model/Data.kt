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

package com.kogicodes.basicform.model

import android.net.Uri
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Entity
@JvmSuppressWildcards
class Data : Serializable{

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    var id: Int? = null

   @SerializedName("idNumber")
    @Expose
    var idNumber: String? = null

    @SerializedName("firstName")
    @Expose
    var firstName: String? = null

    @SerializedName("lastName")
    @Expose
    var lastName: String? = null


    @SerializedName("uri")
    @Expose
    var uri: Uri? = null

    @SerializedName("qr")
    @Expose
    var qr: String? = null


    @SerializedName("lat")
    @Expose
    var lat: String? = null


    @SerializedName("lon")
    @Expose
    var lon: String? = null



    constructor(idNumber: String?, firstName: String?, lastName: String?, uri: Uri?, qr: String?,lat: String?,lon: String?) {
        this.idNumber = idNumber
        this.firstName = firstName
        this.lastName = lastName
        this.uri = uri
        this.qr = qr
        this.lat=lat
        this.lon=lon
    }
}