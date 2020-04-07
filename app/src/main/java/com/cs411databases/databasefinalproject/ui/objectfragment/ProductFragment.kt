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
import org.json.JSONArray

class ProductFragment(private val insertFragment: InsertFragment) : Fragment() {
    private lateinit var spinner1: Spinner
    private lateinit var editText1: EditText
    private lateinit var spinner2: Spinner
    private lateinit var insertButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        spinner1 = view.findViewById(R.id.spinner1)
        editText1 = view.findViewById(R.id.editText1)
        spinner2 = view.findViewById(R.id.spinner2)
        insertButton = view.findViewById(R.id.button)

        var spinnerItems1: MutableList<String> = mutableListOf("(Brand)")
        val spinnerAdapter1: ArrayAdapter<String> = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, spinnerItems1)
        spinnerAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = spinnerAdapter1

        val request = JsonArrayRequest(
        Request.Method.GET, "https://cs411sp20team25.web.illinois.edu/team25?action=select&table=Brands", null,
        Response.Listener<JSONArray> { response ->
            for (index in 0 until response.length()) {
                val elements: JSONArray = response[index] as JSONArray
                val brand = "${elements[1] as String} (${elements[0] as String})"
                spinnerItems1.add(brand)
            }
            spinnerAdapter1.notifyDataSetChanged()
        },
        Response.ErrorListener {
            Log.e("Network", it.message)
        })

        Volley.newRequestQueue(context).add(request)

        val spinnerItems2: List<String> = listOf("(Type)", "Shirts", "Pants", "Jackets",
            "Dresswear", "Shoes", "Swim", "Accessories", "Socks", "Underwear")
        val spinnerAdapter2: ArrayAdapter<String> = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, spinnerItems2)
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner2.adapter = spinnerAdapter2

        insertButton.setOnClickListener {
            if (spinner1.selectedItemPosition != 0 && spinner2.selectedItemPosition != 0) {
                var spinner1Text = spinner1.selectedItem as String
                spinner1Text = spinner1Text.substring(spinner1Text.indexOf("(") + 1, spinner1Text.indexOf(")"))
                insertEntry("Products", listOf(spinner1Text, editText1.text.toString(), spinner2.selectedItem as String))
                editText1.text.clear()
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
