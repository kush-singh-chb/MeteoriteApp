package com.example.meteoritelandings.activities

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.meteoritelandings.Data.loadData
import com.example.meteoritelandings.R
import com.example.meteoritelandings.adapters.MeteoriteAdapter
import com.example.meteoritelandings.databinding.ActivityMainBinding
import com.example.meteoritelandings.models.Meteorite
import com.vicpin.krealmextensions.querySorted
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.realm.Realm
import io.realm.Sort
import org.jetbrains.anko.longToast

class MainActivity : Activity(),SwipeRefreshLayout.OnRefreshListener {

    companion object {
        @JvmField
        val TAG: String = MainActivity::class.java.simpleName
    }

    private lateinit var binding: ActivityMainBinding
    private var disposable: Disposable? = null
    private lateinit var meteoriteAdapter: MeteoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this.layoutInflater)
//        setContentView(R.layout.activity_main)
        setContentView(binding.root)
        binding.swipeView.setOnRefreshListener(this)
        val localMeteorite = Meteorite().querySorted("mass", Sort.DESCENDING)
        binding.meteoritesAmountValueText.text = "${localMeteorite.size}"
        setupView(localMeteorite)
    }


    private fun setupView(localMeteorite: List<Meteorite>) {
        meteoriteAdapter = MeteoriteAdapter(localMeteorite)
        binding.meteoritesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.meteoritesRecyclerView.adapter = meteoriteAdapter
        setDecorator(binding.meteoritesRecyclerView)
    }

    private fun setDecorator(recyclerView: RecyclerView) {
        val itemDecorator = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.accent_divider)!!)
        recyclerView.addItemDecoration(itemDecorator)
    }

    private fun consumerResponse(): Consumer<List<Meteorite>> = Consumer {
        binding.meteoritesAmountValueText.text = "${it.size}"
        Log.d(TAG, "Data loaded successfully")
        meteoriteAdapter.loadItems(it)
        binding.swipeView.isRefreshing = false
    }

    private fun consumerError(): Consumer<Throwable> = Consumer {
        it.localizedMessage?.let { message -> Log.i(TAG, message) }
        longToast("Meteorites data could not be loaded")
    }

    override fun onStart() {
        super.onStart()
        if (Realm.getDefaultInstance().isEmpty) {
            disposable = loadData(consumerResponse(), consumerError())
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    override fun onRefresh() {
        disposable = loadData(consumerResponse(), consumerError())
    }

}