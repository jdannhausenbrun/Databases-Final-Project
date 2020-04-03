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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cs411databases.databasefinalproject.R
import com.cs411databases.databasefinalproject.objects.*
import kotlinx.android.synthetic.main.update_dialog.view.*
import org.json.JSONArray
import java.sql.Date

class ViewFragment : Fragment() {
    private lateinit var listOfItems: MutableList<DatabaseObject>
    private lateinit var listAdapter: ListAdapter
    private lateinit var queue: RequestQueue
    private lateinit var spinner: Spinner
    var currentTableSelection: String = ""

    private val BASE_URL = "https://cs411sp20team25.web.illinois.edu/team25"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_view, container, false)

        spinner = root.findViewById(R.id.view_spinner)
        val spinnerItems: List<String> = listOf("Brands", "Transactions", "Retailers", "Products", "ProductsForSale")
        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, spinnerItems)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        val recyclerView: RecyclerView = root.findViewById(R.id.view_recyclerview)
        listOfItems = mutableListOf()
        listAdapter = ListAdapter(listOfItems, context!!, this)

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
        var url = "$BASE_URL?action=select&table=$table"
        currentTableSelection = table

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                listOfItems.clear()
                for (index in 0 until response.length()) {
                    val elements: JSONArray = response[index] as JSONArray
                    when (table) {
                        "Brands" -> {
                            val brand = Brand((elements[0] as String).toInt(), elements[1] as String)
                            listOfItems.add(brand)
                        }
                        "Transactions" -> {
                            val transaction = Transaction((elements[0] as String).toInt(), (elements[1] as String).toInt(),
                                Date.valueOf(elements[2] as String), (elements[3] as String).toDouble(), (elements[4] as String).toInt() == 1)
                            listOfItems.add(transaction)
                        }
                        "Retailers" -> {
                            val retailer = Retailer((elements[0] as String).toInt(), elements[1] as String, elements[2] as String)
                            listOfItems.add(retailer)
                        }
                        "Products" -> {
                            val product = Product((elements[0] as String).toInt(), (elements[1] as String).toInt(), elements[2] as String,
                                    elements[3] as String)
                            listOfItems.add(product)
                        }
                        "ProductsForSale" -> {
                            val productForSale = ProductForSale((elements[0] as String).toInt(), (elements[1] as String).toInt(),
                                (elements[2] as String).toInt(), (elements[3] as String).toDouble(), (elements[4] as String).toDouble())
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

    fun deleteEntry(position: Int) {
        val url = "$BASE_URL?action=delete&table=$currentTableSelection&id=${listOfItems[position].id}"

        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("Delete Request", response)
                // Reload the list of items
                updateViewList(currentTableSelection)
            },
            Response.ErrorListener { error -> Log.d("Delete Request", error.toString()) })

        queue.add(stringRequest)
    }

    fun updateEntry(position: Int, text1: String, text2: String, text3: String, text4: String) {
        var updateValues = ""
        if (text1.isNotEmpty()) {
            updateValues += "&c=${listOfItems[position].getAttributeName(1)}&v=$text1"
        }
        if (text2.isNotEmpty()) {
            updateValues += "&c=${listOfItems[position].getAttributeName(2)}&v=$text2"
        }
        if (text3.isNotEmpty()) {
            updateValues += "&c=${listOfItems[position].getAttributeName(3)}&v=$text3"
        }
        if (text4.isNotEmpty()) {
            var text4Formatted = text4
            if (listOfItems[position].getAttributeName(4) == "IsReturn") {
                if (text4.toLowerCase() == "yes") {
                    text4Formatted = "1"
                } else if (text4.toLowerCase() == "no") {
                    text4Formatted = "0"
                }
            }
            updateValues += "&c=${listOfItems[position].getAttributeName(4)}&v=$text4Formatted"
        }
        val url = "$BASE_URL?action=update&table=$currentTableSelection$updateValues" +
                "&wc=${listOfItems[position].idColumnName}&wv=${listOfItems[position].id}"

        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("Update Request", response)
                // Reload the list of items
                updateViewList(currentTableSelection)
            },
            Response.ErrorListener { error -> Log.d("Update Request", error.toString()) })

        queue.add(stringRequest)
    }

    fun createAndHandleUpdateAlertDialog(position: Int) {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.update_dialog, null)
        val dialogBuilder = AlertDialog.Builder(context)
            .setCancelable(true)
            .setView(dialogView)
            .setPositiveButton("Update", DialogInterface.OnClickListener { dialog, id ->
                updateEntry(position, dialogView.editText1.text.toString(), dialogView.editText2.text.toString(),
                    dialogView.editText3.text.toString(), dialogView.editText4.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Entry")
        alert.show()
        when (currentTableSelection) {
            "Brands" -> {
                dialogView.editText1.hint = "BrandName"
                dialogView.editText2.hint = "N/A"
                dialogView.editText3.hint = "N/A"
                dialogView.editText4.hint = "N/A"
            }
            "Transactions" -> {
                dialogView.editText1.hint = "ProductOfferingID"
                dialogView.editText2.hint = "TransactionDate (YYYY-MM-DD)"
                dialogView.editText3.hint = "TransactionPrice"
                dialogView.editText4.hint = "IsReturn (Yes/No)"
            }
            "Retailers" -> {
                dialogView.editText1.hint = "RetailerName"
                dialogView.editText2.hint = "RetailerCategory"
                dialogView.editText3.hint = "N/A"
                dialogView.editText4.hint = "N/A"
            }
            "Products" -> {
                dialogView.editText1.hint = "BrandID"
                dialogView.editText2.hint = "ProductName"
                dialogView.editText3.hint = "ProductType"
                dialogView.editText4.hint = "N/A"
            }
            "ProductsForSale" -> {
                dialogView.editText1.hint = "RetailerID"
                dialogView.editText2.hint = "ProductID"
                dialogView.editText3.hint = "Price"
                dialogView.editText4.hint = "DiscountPrice"
            }
        }
    }

    class ListAdapter(private val list: List<DatabaseObject>, private val context: Context, private val viewFragment: ViewFragment):
            RecyclerView.Adapter<ListAdapter.ObjectViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ObjectViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ObjectViewHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: ObjectViewHolder, position: Int) {
            val obj: DatabaseObject = list[position]
            holder.bind(obj, context, viewFragment, position)
        }

        override fun getItemCount(): Int = list.size

        class ObjectViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
            RecyclerView.ViewHolder(inflater.inflate(R.layout.view_list_item, parent, false)) {

            private var textView: TextView = itemView.findViewById(R.id.list_item_text)

            fun bind(obj: DatabaseObject, context: Context, viewFragment: ViewFragment, position: Int) {
                textView.text = obj.toString()
                itemView.setOnClickListener {
                    val dialogBuilder = AlertDialog.Builder(context)
                        .setCancelable(true)
                        .setPositiveButton("Delete", DialogInterface.OnClickListener { dialog, id ->
                            viewFragment.deleteEntry(position)
                        })
                        .setNegativeButton("Update", DialogInterface.OnClickListener { dialog, id ->
                            viewFragment.createAndHandleUpdateAlertDialog(position)
                            dialog.cancel()
                        })
                    val alert = dialogBuilder.create()
                    alert.setTitle("Update or Delete?")
                    alert.show()
                }
            }
        }
    }
}