package com.example.portal.uikit


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import com.example.portal.databinding.LayoutToolbarBinding
import com.example.portal.util.setGone
import com.example.portal.util.setVisible

class PortalToolbar @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    defStyleArr: Int = 0
) : FrameLayout(context, attributes, defStyleArr) {
    private val binding by lazy {
        LayoutToolbarBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )
    }

    fun show(
        title: CharSequence,
        showBack: (() -> Unit)? = null,
        showMenu: (() -> Unit)? = null,
    ) {
        binding.apply {
            textViewTitle.text = title
            if (showBack != null) {
                imageViewBack.visibility = View.VISIBLE
                imageViewBack.setOnClickListener { showBack() }
            }
            if (showMenu != null) {
                imageViewMenu.visibility = View.VISIBLE
                imageViewMenu.setOnClickListener { showMenu() }
            }
        }
    }

    fun setRightIcon(
        @DrawableRes iconResId: Int,
        onClick: () -> Unit
    ) {
        binding.imageButtonRight.setImageResource(iconResId)
        binding.imageButtonRight.setOnClickListener { onClick() }
        binding.imageButtonRight.setVisible()
    }

    fun hideRightIcon() {
        binding.imageButtonRight.setGone()
    }


}