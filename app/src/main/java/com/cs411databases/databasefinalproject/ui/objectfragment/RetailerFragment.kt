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

import com.cs411databases.databasefinalproject.R
import com.cs411databases.databasefinalproject.ui.insert.InsertFragment

class RetailerFragment(private val insertFragment: InsertFragment) : Fragment() {
    private lateinit var editText1: EditText
    private lateinit var spinner1: Spinner
    private lateinit var insertButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_retailer, container, false)

        editText1 = view.findViewById(R.id.editText1)
        spinner1 = view.findViewById(R.id.spinner1)
        insertButton = view.findViewById(R.id.button)

        insertButton.setOnClickListener {
            if (spinner1.selectedItemPosition != 0) {
                insertEntry("Retailers", listOf(editText1.text.toString(), spinner1.selectedItem as String))
                editText1.text.clear()
                spinner1.setSelection(0, true)
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
