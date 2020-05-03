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
import com.cs411databases.databasefinalproject.utils.SpinnerUtils
import org.json.JSONArray
import kotlin.math.round

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
                if (position != 0) {
                    val spinnerText = spinner1.selectedItem as String
                    val retailerId = spinnerText.substring(spinnerText.indexOf("(") + 1, spinnerText.indexOf(")"))
                    selectedRetailer = retailerId
                    if (spinner2.selectedItemPosition != 0) {
                        updateListOfRecommendations(retailerId, spinner2.selectedItemPosition)
                    }
                } else {
                    selectedRetailer = ""
                    recommendationList.text = ""
                }
            }
        }

        spinner2 = view.findViewById(R.id.spinner2)

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position != 0 && spinner1.selectedItemPosition != 0) {
                    updateListOfRecommendations(selectedRetailer, position)
                } else {
                    recommendationList.text = ""
                }
            }
        }

        return view
    }

    fun updateListOfRecommendations(retailer: String, action: Int) {
        var queryAction = ""
        when (action) {
            1 -> { queryAction = "add" }
            2 -> { queryAction = "remove" }
            3 -> { queryAction = "sale" }
        }
        val url = "$BASE_URL?action=recommend&retailer=$retailer&m=$queryAction"
        Log.e("Jacob", url)

        val request = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                var newTextViewContents = ""
                if (queryAction != "sale") {
                    for (index in 0 until response.length()) {
                        val elements: JSONArray = response[index] as JSONArray
                        if (index != (response.length() - 1)) {
                            newTextViewContents += "${index + 1}. ${elements[2] as String}\n\n"
                        } else {
                            newTextViewContents += "${index + 1}. ${elements[2] as String}"
                        }
                    }
                } else {
                    for (index in 0 until response.length()) {
                        val elements: JSONArray = response[index] as JSONArray
                        if (index != (response.length() - 1)) {
                            newTextViewContents += "${index + 1}. ${elements[0] as String} - (Consider pricing at ${round((elements[1] as String).toDouble() * 100) / 100})\n\n"
                        } else {
                            newTextViewContents += "${index + 1}. ${elements[0] as String} - (Consider pricing at ${round((elements[1] as String).toDouble() * 100) / 100})"
                        }
                    }
                }
                recommendationList.text = newTextViewContents
            },
            Response.ErrorListener {
                Log.e("Network", it.message)
            })

        // Add the request to the RequestQueue.
        Volley.newRequestQueue(context).add(request)
    }
}
