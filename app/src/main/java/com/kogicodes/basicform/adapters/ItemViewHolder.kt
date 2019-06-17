

package com.kogicodes.basicform.adapters


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kogicodes.basicform.R


class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    var itemVew: View = itemView

    var name: TextView = itemView.findViewById(R.id.names)
    var avatar: ImageView = itemView.findViewById(R.id.avatar)
    var id: TextView = itemView.findViewById(R.id.id)
    var qr: TextView = itemView.findViewById(R.id.qr)



}
