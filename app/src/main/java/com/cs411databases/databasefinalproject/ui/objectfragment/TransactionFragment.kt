package com.cs411databases.databasefinalproject.ui.objectfragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

import com.cs411databases.databasefinalproject.R
import com.cs411databases.databasefinalproject.ui.insert.InsertFragment
import com.cs411databases.databasefinalproject.utils.SpinnerUtils
import org.json.JSONArray

class TransactionFragment(private val insertFragment: InsertFragment) : Fragment() {
    private lateinit var spinner1: Spinner
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText
    private lateinit var spinner2: Spinner
    private lateinit var insertButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_transaction, container, false)

        spinner1 = view.findViewById(R.id.spinner1)
        editText1 = view.findViewById(R.id.editText1)
        editText2 = view.findViewById(R.id.editText2)
        spinner2 = view.findViewById(R.id.spinner2)
        insertButton = view.findViewById(R.id.button)

        spinner1.adapter = SpinnerUtils.productForSaleSpinnerUtil(context!!)

        insertButton.setOnClickListener {
            if (spinner1.selectedItemPosition != 0 && spinner2.selectedItemPosition != 0) {
                var isReturn = ""
                if (spinner2.selectedItem as String == "Yes") {
                    isReturn = "1"
                } else if (spinner2.selectedItem as String == "No") {
                    isReturn = "0"
                }

                var spinner1Text = spinner1.selectedItem as String
                spinner1Text = spinner1Text.substring(spinner1Text.indexOf("(") + 1, spinner1Text.indexOf(")"))
                insertEntry("Transactions", listOf(spinner1Text, editText1.text.toString(),
                        editText2.text.toString(), isReturn))
                editText1.text.clear()
                editText2.text.clear()
                spinner1.setSelection(0, true)
                spinner2.setSelection(0, true)
            }
        }

        return view
    }

    private fun insertEntry(table: String, values: List<String>) {
        var url = "${insertFragment.BASE_URL}?action=insert&table=$table"

        for (index in values.indices) {
            url += "&v=${values[index]}"
        }

        url = url.replace(" ", "%20")
        insertFragment.sendRequest(url)
    }
}
