package com.shakib.pagingexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shakib.pagingexample.databinding.ActivityMainBinding
import com.shakib.pagingexample.paging.QuotePagingAdapter
import dagger.hilt.android.AndroidEntryPoint

// Step 1
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuotePagingAdapter
    private lateinit var viewModel: QuoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapter = QuotePagingAdapter()

        viewModel = ViewModelProvider(this).get(QuoteViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerView.adapter= adapter


        viewModel.quoteList.observe(this) {
            adapter.submitData(lifecycle, it)
        }


    }
}