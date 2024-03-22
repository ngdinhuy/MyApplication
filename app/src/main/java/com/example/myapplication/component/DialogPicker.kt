package com.example.myapplication.component

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.myapplication.databinding.DialogNumberPickerBinding

class DialogPicker: DialogFragment() {
    var listCategory : List<String>? = null
    lateinit var databinding : DialogNumberPickerBinding
    var clickButtonEvent : ClickButtonEvent? = null

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databinding = DialogNumberPickerBinding.inflate(inflater, container, false)
        return databinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listCategory?.let {
            if (it.isEmpty())
                return@let
            databinding.numberPicker.apply {
                minValue = 0
                maxValue = it.size - 1
                displayedValues = it.toTypedArray()
                wrapSelectorWheel = false
            }

            databinding.actionOk.setOnClickListener {
                clickButtonEvent?.let {
                    it.clickOk(listCategory?.get(databinding.numberPicker.value) ?: "")
                    this.dismiss()
                }
            }
        }
    }
}
interface ClickButtonEvent{
    fun clickOk(text: String)
}