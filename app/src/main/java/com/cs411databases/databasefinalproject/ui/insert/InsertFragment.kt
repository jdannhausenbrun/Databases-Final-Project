package com.cs411databases.databasefinalproject.ui.insert

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.cs411databases.databasefinalproject.R

class InsertFragment : Fragment() {
    private var currentSpinnerSelection: Int = -1

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
        val editText5: EditText = root.findViewById(R.id.editText5)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (position) {
                    0 -> {
                        editText1.hint = "BrandID"
                        editText2.hint = "BrandName"
                        editText3.hint = "N/A"
                        editText4.hint = "N/A"
                        editText5.hint = "N/A"
                        currentSpinnerSelection = 0
                    }
                    1 -> {
                        editText1.hint = "TransactionID"
                        editText2.hint = "ProductOfferingID"
                        editText3.hint = "TransactionDate"
                        editText4.hint = "TransactionPrice"
                        editText5.hint = "IsReturn"
                        currentSpinnerSelection = 1
                    }
                    2 -> {
                        editText1.hint = "RetailerID"
                        editText2.hint = "RetailerName"
                        editText3.hint = "RetailerCategory"
                        editText4.hint = "N/A"
                        editText5.hint = "N/A"
                        currentSpinnerSelection = 2
                    }
                    3 -> {
                        editText1.hint = "ProductID"
                        editText2.hint = "BrandID"
                        editText3.hint = "ProductName"
                        editText4.hint = "ProductType"
                        editText5.hint = "N/A"
                        currentSpinnerSelection = 3
                    }
                    4 -> {
                        editText1.hint = "ProductOfferingID"
                        editText2.hint = "RetailerID"
                        editText3.hint = "ProductID"
                        editText4.hint = "Price"
                        editText5.hint = "DiscountPrice"
                        currentSpinnerSelection = 4
                    }
                    else -> { // Note the block

                    }
                }
            }
        }

        return root
    }
}
