
package com.kogicodes.basicform.adapters


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kogicodes.basicform.R
import com.kogicodes.basicform.model.Data
import com.kogicodes.basicform.ui.main.Utils

class Adapter(private var context: Context,
              private var modelList: List<Data>?
) : RecyclerView.Adapter<ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView: View? = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)



        return ItemViewHolder(itemView!!)
    }


    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val model = modelList!![position]
        holder.name.text = "Names   "+model.firstName+" "+model.lastName
        holder.id.text="ID      "+model.idNumber
        holder.qr.text="QR      "+model.qr
        Utils().loadImage(context,model.uri,holder.avatar)





    }


    override fun getItemCount(): Int {
        return if (null != modelList) modelList!!.size else 0
    }

    fun updateList( modelLists : List<Data>?){

        this.modelList=modelLists

    }


}