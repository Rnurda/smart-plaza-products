package com.example.productssearch.ui.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.productssearch.R
import kotlinx.android.synthetic.main.dialog_filter.*

class FilterDialog(
    private val callback: DialogForResult
): DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.dialog_filter, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        button_save.setOnClickListener {
            val sort  = radioGroup.checkedRadioButtonId
            val sorName = if(sort == R.id.costDescend){
                "costDescend"
            }else{
                "costAccent"
            }
            val ed_ot_price = if(text_view_low_price.text.isBlank())"0" else text_view_low_price.text.toString()
            val ed_do_price = if(text_view_high_price.text.isBlank())"9999999" else text_view_high_price.text.toString()

            onClickSaveBtn(sorName, ed_do_price, ed_ot_price)


        }
    }

    private fun onClickSaveBtn(sortName: String, highCost: String, lowCost: String) {
        callback.onResultSuccess(sortName, highCost,lowCost)
        dismiss()
    }


    interface DialogForResult{
        fun onResultSuccess(sortName: String, highCost: String, lowCost: String)
        fun onResultFailed(ex: Exception)
        fun onResultNeutral(item: Any?)
    }

    companion object {
        const val TAG = "FilterDialog"
    }

}