package com.example.portal.uikit

import android.content.Context
import android.os.Bundle
import com.example.portal.databinding.DialogYearSelectBinding

class YearSelectDialog(
    context: Context,
    private val toolbarTitle: String,
    private val items: List<Int>,
    private val onSelect: (Int) -> Unit
) : BaseFullScreenDialog(context) {

    private val viewBinding by lazy { DialogYearSelectBinding.inflate(layoutInflater) }

    private val adapter by lazy {
        YearSelectAdapter {
            onSelect(it)
            dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.apply {
            portalToolbar.show(toolbarTitle, showBack = { dismiss() })
            recyclerView.layoutManager =
                androidx.recyclerview.widget.LinearLayoutManager(context)
            recyclerView.adapter = adapter
            adapter.updateData(items)
        }

    }
}

