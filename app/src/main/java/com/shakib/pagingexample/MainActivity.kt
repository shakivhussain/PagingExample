package com.shakib.pagingexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shakib.pagingexample.databinding.ActivityMainBinding
import com.shakib.pagingexample.paging.LoaderAdapter
import com.shakib.pagingexample.paging.QuotePagingAdapter
import dagger.hilt.android.AndroidEntryPoint


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


        /*
        * Step 1 : MainActivity
        * Step 2 : QuoteAppApplication
        * Step 3 : NetworkModule
        * Step 4 : Create Models
        * Step 5 : QuotePagingSource
        * Step 6 : QuoteRepository
        * Step 7 : QuoteViewModel
        * Step 7 : Attach to activity with adapter.
        *  Adapter Loader
        * Step 8 : Create Loader Adapter.
        *  Remote Mediator
        * Step 9 : Model and Dao
        * Step 10 : QuoteDatabase
        * Step 11 : DatabaseModule
        *
        * */



        adapter = QuotePagingAdapter()

        viewModel = ViewModelProvider(this).get(QuoteViewModel::class.java)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)

        binding.recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter(),
        )


        viewModel.quoteList.observe(this) {
            adapter.submitData(lifecycle, it)
        }


    }
}