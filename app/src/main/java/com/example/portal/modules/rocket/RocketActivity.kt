package com.example.portal.modules.rocket


import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.portal.R
import com.example.portal.databinding.ActivityRocketBinding
import com.example.portal.modules.base.Status
import com.example.portal.modules.detail.RocketDetailActivity
import com.example.portal.uikit.YearSelectDialog
import com.example.portal.util.setGone
import com.example.portal.util.setVisible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RocketActivity : AppCompatActivity() {

    private val viewBinding by lazy {
        ActivityRocketBinding.inflate(layoutInflater)
    }

    private val viewModel: RocketViewModel by viewModels()

    private val adapter by lazy {
        RocketAdapter {
            startActivity(RocketDetailActivity.createIntent(this, it))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        viewBinding.apply {
            portalToolbar.show("Rocket")
            portalToolbar.setRightIcon(R.drawable.icon_filter) {
                viewModel.yearLiveData.observe(this@RocketActivity, {
                    YearSelectDialog(
                        this@RocketActivity,
                        "Year Selection",
                        it,
                        onSelect = {
                            viewModel.filterList(it)
                        }).show()
                })
            }
            recyclerView.layoutManager = LinearLayoutManager(this@RocketActivity)
            recyclerView.adapter = adapter
        }

        viewModel.rocketItemList.observe(this, {
            when (it.status) {
                Status.SUCCESS -> {
                    hideProgress()
                    it.data?.let {
                        adapter.items = it
                    }
                }

                Status.LOADING -> {
                    showProgress()
                }

                Status.ERROR -> {
                    hideProgress()
                    showError(it.message)
                }
            }
        })
    }

    private fun hideProgress() {
        viewBinding.progressBar.setGone()
    }

    private fun showProgress() {
        viewBinding.progressBar.setVisible()
    }

    private fun showError(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}