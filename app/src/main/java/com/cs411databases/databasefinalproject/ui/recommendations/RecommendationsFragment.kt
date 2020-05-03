package com.cs411databases.databasefinalproject.ui.recommendations

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley

import com.cs411databases.databasefinalproject.R
import com.cs411databases.databasefinalproject.objects.*
import com.cs411databases.databasefinalproject.utils.SpinnerUtils
import org.json.JSONArray

class RecommendationsFragment : Fragment() {
    private val BASE_URL = "https://cs411sp20team25.web.illinois.edu/team25"
    private lateinit var spinner1: Spinner
    private lateinit var spinner2: Spinner
    private lateinit var recommendationList: TextView
    private var selectedRetailer: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recommendations, container, false)
        recommendationList = view.findViewById(R.id.textView)

        spinner1 = view.findViewById(R.id.spinner1)
        spinner1.adapter = SpinnerUtils.retailerSpinnerUtil(context!!)

        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var retailerId = ""
                if (spinner1.selectedItemPosition != 0) {
                    var spinnerText = spinner1.selectedItem as String
                    retailerId = spinnerText.substring(spinnerText.indexOf("(") + 1, spinnerText.indexOf(")"))
                }
                selectedRetailer = retailerId
                Log.e("Jacob5", selectedRetailer)
                Log.e("Jacob6", retailerId)
                updateListOfRecommendations()
            }
        }

        spinner2 = view.findViewById(R.id.spinner1)

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateListOfRecommendations()
            }
        }

        return view
    }

    fun updateListOfRecommendations() {
        if (spinner1.selectedItemPosition != 0 && spinner2.selectedItemPosition != 0) {
            var queryAction = ""
            when (spinner2.selectedItemPosition) {
                1 -> { queryAction = "add" }
                2 -> { queryAction = "remove" }
                3 -> { queryAction = "sale" }
            }
            val url = "$BASE_URL?action=recommend&retailer=$selectedRetailer&m=$queryAction"
            Log.e("Jacob1", url)

            val request = JsonArrayRequest(
                Request.Method.GET, url, null,
                Response.Listener<JSONArray> { response ->
                    Log.e("Jacob1", response.toString())
                    var newTextViewContents = ""
                    for (index in 0 until response.length()) {
                        val elements: String = response[index] as String
                        Log.e("Jacob2", elements)
                        newTextViewContents += "${index + 1}. ${elements[1] as String}\n"
                    }
                    recommendationList.text = newTextViewContents
                },
                Response.ErrorListener {
                    Log.e("Network", it.message)
                })

            // Add the request to the RequestQueue.
            Volley.newRequestQueue(context).add(request)
        } else {
            recommendationList.text = ""
        }
    }
}
