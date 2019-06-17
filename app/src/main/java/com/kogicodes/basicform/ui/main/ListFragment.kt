package com.kogicodes.basicform.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.kogicodes.basicform.MainActivity
import com.kogicodes.basicform.R
import com.kogicodes.basicform.adapters.Adapter
import com.kogicodes.basicform.model.Data
import kotlinx.android.synthetic.main.list_fragment.*
import java.util.*

class ListFragment : Fragment() {

    companion object {
        fun newInstance() = ListFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()

    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initview()


    }

    fun initview(){
        initData()
        fab_add_listings.setOnClickListener {

            ( activity as MainActivity).add()
        }
        loadData()
    }

    fun loadData(){
        viewModel.get().observe(this, Observer {
            Log.d("mdf", " dsd"+Gson().toJson(it))

            if(it!= null){


                datas=it
                initData()


            }
        })

    }

    private var adaptet: Adapter? = null
    private var datas:  List<Data>?= LinkedList()
    private fun initData() {

        adaptet = context?.let {
            Adapter(it, datas)
        }!!
        val spanCount = 1
        val layoutManager = GridLayoutManager(context, spanCount)
        recyclerview.layoutManager = layoutManager
        recyclerview.itemAnimator = DefaultItemAnimator()
        recyclerview.adapter = adaptet
        adaptet!!.notifyDataSetChanged()

    }

}
