package com.cs411databases.databasefinalproject.ui.insert

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.cs411databases.databasefinalproject.R
import java.sql.Date

class InsertFragment : Fragment() {
    private var currentSpinnerSelection: Int = -1
    private val BASE_URL = "https://cs411sp20team25.web.illinois.edu/team25?q="

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

        val editText1: EditText = root.findViewById(R.id.editText1)
        val editText2: EditText = root.findViewById(R.id.editText2)
        val editText3: EditText = root.findViewById(R.id.editText3)
        val editText4: EditText = root.findViewById(R.id.editText4)
        val insertButton: Button = root.findViewById(R.id.insert_button)

        insertButton.setOnClickListener {
            when (currentSpinnerSelection) {
                0 -> {
                    editText1.text.toString()
                    val url = getInsertURL(
                        "Brands",
                        listOf(
                            editText1.text.toString()))
                    Log.e("URL", url)
                    Toast.makeText(context, url, Toast.LENGTH_SHORT).show()

                }
                1 -> {

                    val url = getInsertURL(
                        "Transactions",
                        listOf(
                            editText1.text.toString(),
                            Date.valueOf(editText2.text.toString()).toString(),
                            editText3.text.toString().toDouble().toString(),
                            editText4.text.toString().toBoolean().toString()))
                    Log.e("URL", url)
                    Toast.makeText(context, url, Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    val url = getInsertURL(
                        "Retailers",
                        listOf(
                            editText1.text.toString(),
                            editText2.text.toString()))
                    Log.e("URL", url)
                    Toast.makeText(context, url, Toast.LENGTH_SHORT).show()
                }
                3 -> {
                    val url = getInsertURL(
                        "Retailers",
                        listOf(
                            editText1.text.toString(),
                            editText2.text.toString(),
                            editText3.text.toString()))
                    Toast.makeText(context, url, Toast.LENGTH_SHORT).show()
                }
                4 -> {
                    val url = getInsertURL(
                        "Retailers",
                        listOf(
                            editText1.text.toString(),
                            editText2.text.toString(),
                            editText3.text.toString().toDouble().toString(),
                            editText4.text.toString().toDouble().toString()))
                    Toast.makeText(context, url, Toast.LENGTH_SHORT).show()
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
                        editText4.hint = "IsReturn (Y/N)"
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

    fun getInsertURL(table: String, values: List<String>) : String {
        print(table + values)
        Log.e("URL", table + values)
        var valuesStr = "("
        for (i in values.indices) {
            valuesStr += if (i == values.size - 1) {
                "\"" + values[i] + "\")"
            } else {
                "\"" + values[i] + "\",%20"
            }
        }
        return BASE_URL + "INSERT%20INTO%20" + table + "%20VALUES%20" + valuesStr
    }
}
