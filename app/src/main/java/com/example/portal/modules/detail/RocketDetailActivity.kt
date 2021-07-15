package com.example.portal.modules.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.portal.databinding.ActivityRocketDetailBinding
import com.example.portal.modules.rocket.RocketItem

class RocketDetailActivity : AppCompatActivity() {

    private val viewBinding by lazy {
        ActivityRocketDetailBinding.inflate(layoutInflater)
    }

    private val rocketItem by lazy {
        intent?.getParcelableExtra<RocketItem>(EXTRAS_ROCKET)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)

        viewBinding.apply {
            portalToolbar.show(
                title = rocketItem?.missionName ?: "-",
                showBack = { onBackPressed() })
            Glide.with(root.context)
                .load(rocketItem?.missionPatch)
                .centerCrop()
                .into(imageView)
            textViewName.text = "Name: " + rocketItem?.missionName
            textViewLaunchYear.text = "Year: " + rocketItem?.launchYear
        }
    }

    companion object {
        private const val EXTRAS_ROCKET = "EXTRAS_ROCKET"

        fun createIntent(context: Context, rocketItem: RocketItem) =
            Intent(context, RocketDetailActivity::class.java).apply {
                putExtra(EXTRAS_ROCKET, rocketItem)
            }
    }
}