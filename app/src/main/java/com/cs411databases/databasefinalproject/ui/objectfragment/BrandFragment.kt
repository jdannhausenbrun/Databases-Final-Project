package com.cs411databases.databasefinalproject.ui.objectfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.cs411databases.databasefinalproject.R
import com.cs411databases.databasefinalproject.ui.insert.InsertFragment

class BrandFragment(private val insertFragment: InsertFragment) : Fragment() {
    private lateinit var editText1: EditText
    private lateinit var insertButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_brand, container, false)
        editText1 = view.findViewById(R.id.editText1)
        insertButton = view.findViewById(R.id.button)

        insertButton.setOnClickListener {
            insertEntry("Brands", listOf(editText1.text.toString()))
            editText1.text.clear()
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
