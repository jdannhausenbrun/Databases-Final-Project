package com.cs411databases.databasefinalproject.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cs411databases.databasefinalproject.R
import com.cs411databases.databasefinalproject.objects.Brand

class ViewFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_view, container, false)

        val spinner: Spinner = root.findViewById(R.id.view_spinner)
        val spinnerItems: List<String> = listOf("Brands", "Transactions", "Retailers", "Products", "ProductsForSale")
        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, spinnerItems)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        val recyclerView: RecyclerView = root.findViewById(R.id.view_recyclerview)

        val brand1 = Brand()
        brand1.brandID = 1L
        brand1.brandName = "Brand 1"
        val brand2 = Brand()
        brand2.brandID = 2L
        brand2.brandName = "Brand 2"
        val brand3 = Brand()
        brand3.brandID = 3L
        brand3.brandName = "Brand 3"
        val brand4 = Brand()
        brand4.brandID = 4L
        brand4.brandName = "Brand 4"
        val brand5 = Brand()
        brand5.brandID = 5L
        brand5.brandName = "Brand 5"
        val brand6 = Brand()
        brand6.brandID = 6L
        brand6.brandName = "Brand 6"
        val brand7 = Brand()
        brand7.brandID = 7L
        brand7.brandName = "Brand 7"
        val brand8 = Brand()
        brand8.brandID = 8L
        brand8.brandName = "Brand 8"
        var listOfItems: MutableList<Any> = mutableListOf(brand1, brand2, brand3, brand4, brand5, brand6, brand7, brand8)
        val listAdapter = ListAdapter(listOfItems)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> print("x == 0")
                    1 -> print("x == 1")
                    2 -> print("x == 2")
                    3 -> print("x == 3")
                    4 -> print("x == 4")
                    else -> { // Note the block

                    }
                }
            }
        }

        listAdapter.notifyDataSetChanged()

        return root
    }

    class ListAdapter(private val list: List<Any>): RecyclerView.Adapter<ListAdapter.ObjectViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ObjectViewHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: ObjectViewHolder, position: Int) {
            val obj: Any = list[position]
            holder.bind(obj)
        }

        override fun getItemCount(): Int = list.size

        class ObjectViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.view_list_item, parent, false)) {

            private var textView: TextView = itemView.findViewById(R.id.list_item_text)

            fun bind(obj: Any) {
                textView.text = obj.toString()
            }
        }
    }
}
