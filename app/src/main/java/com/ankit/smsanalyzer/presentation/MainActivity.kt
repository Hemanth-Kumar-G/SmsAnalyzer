package com.ankit.smsanalyzer.presentation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankit.smsanalyzer.R
import com.ankit.smsanalyzer.base.BaseActivity
import com.ankit.smsanalyzer.common.Constants
import com.ankit.smsanalyzer.databinding.ActivityMainBinding
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val TAG = MainActivity::class.java.simpleName

    @Inject
    lateinit var adapter: SmsItemAdapter

    @Inject
    lateinit var smsItemList: ArrayList<SmsItem>

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        initObserver()
        initView()
        loadSms()
    }

    private fun initObserver() {
        viewModel.smsList.observe(this, Observer {
            when (it) {
                is SmsListState.Success -> {
                    tv_error_text.visibility = GONE
                    smsItemList.clear()
                    smsItemList.addAll(it.items)
                    adapter.notifyDataSetChanged()
                }
                is SmsListState.Failed -> {
                    tv_error_text.visibility = VISIBLE
                    tv_error_text.text = it.trowable.message
                }
            }
        })

        viewModel.analizedData.observe(this, Observer {
            val chartEntries = ArrayList<PieEntry>()
            chartEntries.add(PieEntry(it.totalExpences.toFloat(),0))
            chartEntries.add(PieEntry(it.totalIncome.toFloat(),1))
            updateChart(chartEntries)
        })
    }

    private fun loadSms() {
        if (checkSmsPermission()) {
            viewModel.loadSmsList()
        }
    }

    /*
     * Check Read Sms permission if granted return success or return false.
     */
    private fun checkSmsPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_SMS),
                Constants.READ_SMS_REQ_CODE
            )
            return false
        }
        return true
    }

    /*
     * Recycler view setup with sms adapter.
     * and pie chart setup
     */
    private fun initView() {
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.itemAnimator = DefaultItemAnimator()
        binding.rvMain.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        binding.rvMain.adapter = adapter

        val pieEntries = arrayListOf<PieEntry>().apply {
            add(PieEntry(100f,0))
            add(PieEntry(200f,1))
        }
       updateChart(pieEntries)

    }

    private fun updateChart(pieEntries: java.util.ArrayList<PieEntry>) {
        val pieDateSet =  PieDataSet(pieEntries,"Income / Expences")
        pieDateSet.sliceSpace = 2f
        pieDateSet.selectionShift = 3f
        val colors = ArrayList<Int>()
        for (c in ColorTemplate.JOYFUL_COLORS) colors.add(c)
        pieDateSet.colors = colors
        val pieData = PieData(pieDateSet)
        binding.pieChartMain.data = pieData;
        // undo all highlights
        binding.pieChartMain.highlightValues(null)
        binding.pieChartMain.invalidate()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_SMS_REQ_CODE) {
            Log.d(TAG, grantResults.size.toString())
            if (grantResults.isNotEmpty()) {
                viewModel.loadSmsList()
            } else {
                finish()
            }
        }
    }
}