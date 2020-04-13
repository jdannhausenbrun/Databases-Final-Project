package com.cs411databases.databasefinalproject.utils

import android.R
import android.content.Context
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class SpinnerUtils {
    companion object {
        fun retailerSpinnerUtil(context: Context): SpinnerAdapter {
            val spinnerItems: MutableList<String> = mutableListOf("(Retailer)")
            val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(context, R.layout.simple_spinner_item, spinnerItems)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            val request1 = JsonArrayRequest(
                Request.Method.GET, "https://cs411sp20team25.web.illinois.edu/team25?action=select&table=Retailers", null,
                Response.Listener<JSONArray> { response ->
                    for (index in 0 until response.length()) {
                        val elements: JSONArray = response[index] as JSONArray
                        val brand = "${elements[1] as String} (${elements[0] as String})"
                        spinnerItems.add(brand)
                    }
                    spinnerAdapter.notifyDataSetChanged()
                },
                Response.ErrorListener {
                    Log.e("Network", it.message)
                })

            Volley.newRequestQueue(context).add(request1)

            return spinnerAdapter
        }

        fun brandSpinnerUtil(context: Context): SpinnerAdapter {
            var spinnerItems: MutableList<String> = mutableListOf("(Brand)")
            val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item, spinnerItems)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            val request = JsonArrayRequest(
                Request.Method.GET, "https://cs411sp20team25.web.illinois.edu/team25?action=select&table=Brands", null,
                Response.Listener<JSONArray> { response ->
                    for (index in 0 until response.length()) {
                        val elements: JSONArray = response[index] as JSONArray
                        val brand = "${elements[1] as String} (${elements[0] as String})"
                        spinnerItems.add(brand)
                    }
                    spinnerAdapter.notifyDataSetChanged()
                },
                Response.ErrorListener {
                    Log.e("Network", it.message)
                })

            Volley.newRequestQueue(context).add(request)

            return spinnerAdapter
        }

        fun productForSaleSpinnerUtil(context: Context): SpinnerAdapter {
            val spinnerItems: MutableList<String> = mutableListOf("(Product For Sale)")
            val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(context, android.R.layout.simple_spinner_item, spinnerItems)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            val request = JsonArrayRequest(
                Request.Method.GET, "https://cs411sp20team25.web.illinois.edu/team25?action=select&table=ProductsForSale", null,
                Response.Listener<JSONArray> { response ->
                    for (index in 0 until response.length()) {
                        val elements: JSONArray = response[index] as JSONArray
                        val brand = "Retailer ${elements[1] as String}, Product ${elements[2] as String} (${elements[0] as String})"
                        spinnerItems.add(brand)
                    }
                    spinnerAdapter.notifyDataSetChanged()
                },
                Response.ErrorListener {
                    Log.e("Network", it.message)
                })

            Volley.newRequestQueue(context).add(request)

            return spinnerAdapter
        }

        fun productSpinnerUtil(context: Context): SpinnerAdapter {
            val spinnerItems: MutableList<String> = mutableListOf("(Product)")
            val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, spinnerItems)
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            val request2 = JsonArrayRequest(
                Request.Method.GET, "https://cs411sp20team25.web.illinois.edu/team25?action=select&table=Products", null,
                Response.Listener<JSONArray> { response ->
                    for (index in 0 until response.length()) {
                        val elements: JSONArray = response[index] as JSONArray
                        val brand = "${elements[2] as String} (${elements[0] as String})"
                        spinnerItems.add(brand)
                    }
                    spinnerAdapter.notifyDataSetChanged()
                },
                Response.ErrorListener {
                    Log.e("Network", it.message)
                })

            Volley.newRequestQueue(context).add(request2)

            return spinnerAdapter
        }
    }
}