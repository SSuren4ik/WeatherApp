package com.customView

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class DecimalTextWatcher(private val editText: EditText) : TextWatcher {

    private var isEditing = false

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isEditing) {
            return
        }
        isEditing = true

        val text = s.toString()
        val newText = StringBuilder()
        var hasDecimal = false
        var hasMinus = false

        for (i in text.indices) {
            val char = text[i]
            if (char == '.') {
                if (hasDecimal) continue
                hasDecimal = true
            } else if (char == '-') {
                if (hasMinus || i != 0) continue
                hasMinus = true
            }
            newText.append(char)
        }

        if (newText.toString() != text) {
            editText.setText(newText.toString())
            editText.setSelection(newText.length)
        }

        isEditing = false
    }
}
