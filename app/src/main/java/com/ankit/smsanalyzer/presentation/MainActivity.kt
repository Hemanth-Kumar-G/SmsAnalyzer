package com.ankit.smsanalyzer.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankit.smsanalyzer.R
import com.ankit.smsanalyzer.base.BaseActivity
import com.ankit.smsanalyzer.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject lateinit var adapter: SmsItemAdapter

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this)[MainViewModel::class.java]
    }
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.viewModel = viewModel
        initView()
    }

    private fun initView() {
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.itemAnimator = DefaultItemAnimator()
        binding.rvMain.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
        binding.rvMain.adapter = adapter
    }
}