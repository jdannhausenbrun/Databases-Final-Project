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
import com.cs411databases.databasefinalproject.ui.objectfragment.*

class InsertFragment : Fragment() {
    val BASE_URL = "https://cs411sp20team25.web.illinois.edu/team25"
    private lateinit var queue: RequestQueue
    private lateinit var spinner: Spinner

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val brandFragment = BrandFragment(this)
        val transactionFragment = TransactionFragment(this)
        val retailerFragment = RetailerFragment(this)
        val productFragment = ProductFragment(this)
        val productForSaleFragment = ProductForSaleFragment(this)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var newFragment: Fragment? = null
                when (position) {
                    0 -> newFragment = brandFragment
                    1 -> newFragment = transactionFragment
                    2 -> newFragment = retailerFragment
                    3 -> newFragment = productFragment
                    4 -> newFragment = productForSaleFragment
                }
                val transaction = fragmentManager!!.beginTransaction()
                transaction.replace(R.id.host_fragment, newFragment!!)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_insert, container, false)

        spinner = root.findViewById(R.id.insert_spinner)
        val spinnerItems: List<String> = listOf("Brands", "Transactions", "Retailers", "Products", "ProductsForSale")
        val spinnerAdapter: ArrayAdapter<String> = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, spinnerItems)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        queue = Volley.newRequestQueue(context)

        return root
    }

    fun sendRequest(url: String) {
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Log.d("Insert Request", response)
                Log.e("Jacob Success", url)
            },
            Response.ErrorListener { error ->
                Log.d("Insert Request", error.toString())
                Log.e("Jacob Error", url)
            })

        queue.add(stringRequest)
    }
}
