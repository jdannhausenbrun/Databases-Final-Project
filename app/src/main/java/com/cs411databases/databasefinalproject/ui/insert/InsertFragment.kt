package com.cs411databases.databasefinalproject.ui.insert

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cs411databases.databasefinalproject.R
import java.sql.Date

class InsertFragment : Fragment() {
    private var currentSpinnerSelection: Int = -1
    private val BASE_URL = "https://cs411sp20team25.web.illinois.edu/team25"
    private lateinit var queue: RequestQueue
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var editText3: EditText
    private lateinit var editText4: EditText

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_insert, container, false)

        val spinner: Spinner = root.findViewById(R.id.insert_spinner)
        val spinnerItems: List<String> = listOf("Brands", "Transactions", "Retailers", "Products", "ProductsForSale")
        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, spinnerItems)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        editText1 = root.findViewById(R.id.editText1)
        editText2 = root.findViewById(R.id.editText2)
        editText3 = root.findViewById(R.id.editText3)
        editText4 = root.findViewById(R.id.editText4)
        val insertButton: Button = root.findViewById(R.id.insert_button)

        queue = Volley.newRequestQueue(context)

        insertButton.setOnClickListener {
            when (currentSpinnerSelection) {
                0 -> {
                    editText1.text.toString()
                    val url = insertEntry(
                        "Brands",
                        listOf(
                            editText1.text.toString()))

                }
                1 -> {

                    val url = insertEntry(
                        "Transactions",
                        listOf(
                            editText1.text.toString().toInt().toString(),
                            Date.valueOf(editText2.text.toString()).toString(),
                            editText3.text.toString().toDouble().toString(),
                            editText4.text.toString()))
                }
                2 -> {
                    val url = insertEntry(
                        "Retailers",
                        listOf(
                            editText1.text.toString(),
                            editText2.text.toString()))
                }
                3 -> {
                    val url = insertEntry(
                        "Products",
                        listOf(
                            editText1.text.toString().toInt().toString(),
                            editText2.text.toString(),
                            editText3.text.toString()))
                }
                4 -> {
                    val url = insertEntry(
                        "ProductsForSale",
                        listOf(
                            editText1.text.toString().toInt().toString(),
                            editText2.text.toString().toInt().toString(),
                            editText3.text.toString().toDouble().toString(),
                            editText4.text.toString().toDouble().toString()))
                }
            }
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                editText1.text.clear()
                editText2.text.clear()
                editText3.text.clear()
                editText4.text.clear()
                when (position) {
                    0 -> {
                        editText1.hint = "BrandName"
                        editText2.hint = "N/A"
                        editText3.hint = "N/A"
                        editText4.hint = "N/A"
                        currentSpinnerSelection = 0
                    }
                    1 -> {
                        editText1.hint = "ProductOfferingID"
                        editText2.hint = "TransactionDate (YYYY-MM-DD)"
                        editText3.hint = "TransactionPrice"
                        editText4.hint = "IsReturn (Yes/No)"
                        currentSpinnerSelection = 1
                    }
                    2 -> {
                        editText1.hint = "RetailerName"
                        editText2.hint = "RetailerCategory"
                        editText3.hint = "N/A"
                        editText4.hint = "N/A"
                        currentSpinnerSelection = 2
                    }
                    3 -> {
                        editText1.hint = "BrandID"
                        editText2.hint = "ProductName"
                        editText3.hint = "ProductType"
                        editText4.hint = "N/A"
                        currentSpinnerSelection = 3
                    }
                    4 -> {
                        editText1.hint = "RetailerID"
                        editText2.hint = "ProductID"
                        editText3.hint = "Price"
                        editText4.hint = "DiscountPrice"
                        currentSpinnerSelection = 4
                    }
                }
            }
        }

        return root
    }

    private fun insertEntry(table: String, values: List<String>) {
        var url = "$BASE_URL?action=insert&table=$table"

        for (index in values.indices) {
            if (table == "Transactions" && index == 3) {
                if (values[index].toLowerCase() == "yes") {
                    url += "&v=1"
                } else if (values[index].toLowerCase() == "no") {
                    url += "&v=0"
                }
            } else {
                url += "&v=${values[index]}"
            }
        }

        url = url.replace(" ", "%20")

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("Insert Request", response)
            },
            Response.ErrorListener { error ->
                Log.d("Insert Request", error.toString())
            })

        queue.add(stringRequest)

        editText1.text.clear()
        editText2.text.clear()
        editText3.text.clear()
        editText4.text.clear()
    }
}
