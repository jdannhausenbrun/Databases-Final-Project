package com.cs411databases.databasefinalproject.ui.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
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
import com.cs411databases.databasefinalproject.utils.SpinnerUtils
import org.json.JSONArray
import java.sql.Date

class ViewFragment : Fragment() {
    private lateinit var listOfItems: MutableList<DatabaseObject>
    private lateinit var listAdapter: ListAdapter
    private lateinit var queue: RequestQueue
    private lateinit var spinner: Spinner
    private lateinit var searchView: SearchView
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
                searchView.setQuery("", false)
                when (position) {
                    0 -> updateViewList("Brands", null)
                    1 -> updateViewList("Transactions", null)
                    2 -> updateViewList("Retailers", null)
                    3 -> updateViewList("Products", null)
                    4 -> updateViewList("ProductsForSale", null)
                }
            }
        }

        setHasOptionsMenu(true)

        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    updateViewList(currentTableSelection, null)
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    updateViewList(currentTableSelection, query)
                }
                return false
            }
        })
    }

    fun updateViewList(table: String, query: String?) {
        var url = "$BASE_URL?action=select&table=$table"
        if (query != null) {
            url = "$BASE_URL?action=search&table=$table&q=$query"
        }
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
                searchView.setQuery("", false)
                updateViewList(currentTableSelection, null)
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
            updateValues += "&c=${listOfItems[position].getAttributeName(4)}&v=$text4"
        }
        val url = "$BASE_URL?action=update&table=$currentTableSelection$updateValues" +
                "&wc=${listOfItems[position].idColumnName}&wv=${listOfItems[position].id}"

        val stringRequest = StringRequest(Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("Update Request", response)
                Log.e("Jacob", url)
                // Reload the list of items
                searchView.setQuery("", false)
                updateViewList(currentTableSelection, null)
            },
            Response.ErrorListener { error ->
                Log.d("Update Request", error.toString())
                Log.e("Jacob", url)
            })

        queue.add(stringRequest)
    }

    fun createAndHandleUpdateAlertDialog(position: Int) {
        var dialogView: View? = null
        when (currentTableSelection) {
            "Brands" -> {
                dialogView = LayoutInflater.from(context).inflate(R.layout.fragment_brand, null)
                val button = dialogView.findViewById<Button>(R.id.button)
                button.text = "Update"
                button.setOnClickListener {
                    updateEntry(position,
                        dialogView?.findViewById<EditText>(R.id.editText1)?.text.toString(),
                        "",
                        "",
                        "")
                }
            }
            "Transactions" -> {
                dialogView = LayoutInflater.from(context).inflate(R.layout.fragment_transaction, null)
                val spinner1 = dialogView.findViewById<Spinner>(R.id.spinner1)
                val spinner2 = dialogView.findViewById<Spinner>(R.id.spinner2)
                spinner1.adapter = SpinnerUtils.productForSaleSpinnerUtil(context!!)
                val button = dialogView.findViewById<Button>(R.id.button)
                button.text = "Update"

                button.setOnClickListener {
                    var spinner1Text = ""
                    if (spinner1.selectedItemPosition != 0) {
                        spinner1Text = spinner1.selectedItem as String
                        spinner1Text = spinner1Text.substring(spinner1Text.indexOf("(") + 1, spinner1Text.indexOf(")"))
                    }

                    var spinner2Text = ""
                    if (spinner2.selectedItemPosition != 0) {
                        if (spinner2.selectedItem as String == "Yes") {
                            spinner2Text = "1"
                        } else if (spinner2.selectedItem as String == "No") {
                            spinner2Text = "0"
                        }
                    }

                    updateEntry(position,
                        spinner1Text,
                        dialogView?.findViewById<EditText>(R.id.editText1)?.text.toString(),
                        dialogView?.findViewById<EditText>(R.id.editText2)?.text.toString(),
                        spinner2Text)
                }
            }
            "Retailers" -> {
                dialogView = LayoutInflater.from(context).inflate(R.layout.fragment_retailer, null)
                val button = dialogView.findViewById<Button>(R.id.button)
                val spinner1 = dialogView.findViewById<Spinner>(R.id.spinner1)
                button.text = "Update"
                button.setOnClickListener {
                    updateEntry(position,
                        dialogView?.findViewById<EditText>(R.id.editText1)?.text.toString(),
                        if (spinner1.selectedItemPosition != 0) spinner1.selectedItem as String else "",
                        "",
                        "")
                }
            }
            "Products" -> {
                dialogView = LayoutInflater.from(context).inflate(R.layout.fragment_product, null)
                val spinner1 = dialogView.findViewById<Spinner>(R.id.spinner1)
                spinner1.adapter = SpinnerUtils.brandSpinnerUtil(context!!)
                val spinner2 = dialogView.findViewById<Spinner>(R.id.spinner2)
                val button = dialogView.findViewById<Button>(R.id.button)
                button.text = "Update"

                button.setOnClickListener {
                    var spinner1Text = ""
                    if (spinner1.selectedItemPosition != 0) {
                        spinner1Text = spinner1.selectedItem as String
                        spinner1Text = spinner1Text.substring(spinner1Text.indexOf("(") + 1, spinner1Text.indexOf(")"))
                    }

                    updateEntry(position,
                        spinner1Text,
                        dialogView?.findViewById<EditText>(R.id.editText1)?.text.toString(),
                        if (spinner2.selectedItemPosition != 0) spinner2.selectedItem as String else "",
                        "")
                }
            }
            "ProductsForSale" -> {
                dialogView = LayoutInflater.from(context).inflate(R.layout.fragment_product_for_sale, null)
                val spinner1 = dialogView.findViewById<Spinner>(R.id.spinner1)
                spinner1.adapter = SpinnerUtils.retailerSpinnerUtil(context!!)
                val spinner2 = dialogView.findViewById<Spinner>(R.id.spinner2)
                spinner2.adapter = SpinnerUtils.productSpinnerUtil(context!!)
                val button = dialogView.findViewById<Button>(R.id.button)
                button.text = "Update"

                button.setOnClickListener {
                    var spinner1Text = ""
                    if (spinner1.selectedItemPosition != 0) {
                        spinner1Text = spinner1.selectedItem as String
                        spinner1Text = spinner1Text.substring(spinner1Text.indexOf("(") + 1, spinner1Text.indexOf(")"))
                    }

                    var spinner2Text = ""
                    if (spinner2.selectedItemPosition != 0) {
                        spinner2Text = spinner2.selectedItem as String
                        spinner2Text = spinner2Text.substring(spinner2Text.indexOf("(") + 1, spinner2Text.indexOf(")"))
                    }

                    updateEntry(position,
                        spinner1Text,
                        spinner2Text,
                        dialogView?.findViewById<EditText>(R.id.editText1)?.text.toString(),
                        dialogView?.findViewById<EditText>(R.id.editText2)?.text.toString())
                }
            }
        }

        val dialogBuilder = AlertDialog.Builder(context)
            .setCancelable(true)
            .setView(dialogView)
            .setNegativeButton("Close", DialogInterface.OnClickListener { dialog, id ->
                dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Entry")
        alert.show()
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