package com.cs411databases.databasefinalproject.ui.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
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
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.cs411databases.databasefinalproject.R
import com.cs411databases.databasefinalproject.objects.*
import org.json.JSONArray
import java.sql.Date

class ViewFragment : Fragment() {
    private lateinit var listOfItems: MutableList<Any>
    private lateinit var listAdapter: ListAdapter
    private lateinit var queue: RequestQueue
    private var currentTableSelection: String = ""

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
        listOfItems = mutableListOf()
        listAdapter = ListAdapter(listOfItems, context!!)

        queue = Volley.newRequestQueue(context)

        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = listAdapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> updateViewList("Brands")
                    1 -> updateViewList("Transactions")
                    2 -> updateViewList("Retailers")
                    3 -> updateViewList("Products")
                    4 -> updateViewList("ProductsForSale")
                }
            }
        }

        return root
    }

    fun updateViewList(table: String) {
        val url = "https://cs411sp20team25.web.illinois.edu/team25?q=SELECT%20*%20FROM%20$table"
        currentTableSelection = table

        // Request a string response from the provided URL.
        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                Log.e("Jacob", response.toString())
                listOfItems.clear()
                for (index in 0 until response.length()) {
                    val elements: JSONArray = response[index] as JSONArray
                    when (table) {
                        "Brands" -> {
                            val brand = Brand(elements[0] as String, elements[1] as String)
                            listOfItems.add(brand)
                        }
                        "Transactions" -> {
                            val transaction = Transaction(elements[0] as String, elements[1] as String,
                                Date.valueOf(elements[2] as String), (elements[3] as String).toDouble(), (elements[4] as String).toBoolean())
                            listOfItems.add(transaction)
                        }
                        "Retailers" -> {
                            val retailer = Retailer(elements[0] as String, elements[1] as String, elements[2] as String)
                            listOfItems.add(retailer)
                        }
                        "Products" -> {
                            val product = Product(elements[0] as String, elements[1] as String, elements[2] as String,
                                    elements[3] as String)
                            listOfItems.add(product)
                        }
                        "ProductsForSale" -> {
                            val productForSale = ProductForSale(elements[0] as String, elements[1] as String,
                                    elements[2] as String, (elements[3] as String).toDouble(), (elements[4] as String).toDouble())
                            listOfItems.add(productForSale)
                        }
                    }
                }
                listAdapter.notifyDataSetChanged()
            },
            Response.ErrorListener {
                Log.e("Network", it.message)
            })

        // Add the request to the RequestQueue.
        queue.add(request)
    }

    class ListAdapter(private val list: List<Any>, private val context: Context): RecyclerView.Adapter<ListAdapter.ObjectViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ObjectViewHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: ObjectViewHolder, position: Int) {
            val obj: Any = list[position]
            holder.bind(obj, context)
        }

        override fun getItemCount(): Int = list.size

        class ObjectViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.view_list_item, parent, false)) {

            private var textView: TextView = itemView.findViewById(R.id.list_item_text)

            fun bind(obj: Any, context: Context) {
                textView.text = obj.toString()
                itemView.setOnClickListener {
                    val dialogBuilder = AlertDialog.Builder(context)
                    dialogBuilder.setMessage("Are you sure?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", DialogInterface.OnClickListener {
                                dialog, id -> Log.e("Jacob", "Delete: ${textView.text}")
                        })
                        .setNegativeButton("No", DialogInterface.OnClickListener {
                                dialog, id -> dialog.cancel()
                        })
                    val alert = dialogBuilder.create()
                    alert.setTitle("Deleting Entry")
                    alert.show()
                }
            }
        }
    }
}
